package okay.maruko.illagerblabber.voice

import net.minecraft.entity.mob.EvokerEntity
import net.minecraft.entity.mob.IllagerEntity
import net.minecraft.entity.mob.PillagerEntity
import net.minecraft.entity.mob.VindicatorEntity
import org.slf4j.LoggerFactory
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

/**
 * Central registry for illager voice managers and state tracking
 */

object IllagerVoiceRegistry {
    private val LOGGER = LoggerFactory.getLogger("illagerblabber")

    // Group Spotted Spam Prevention
    private val lastGroupSpottedSoundTime = ConcurrentHashMap<IllagerType, Long>()

    // Maps to store state data
    private val voiceManagers = ConcurrentHashMap<UUID, IllagerVoiceManager>()
    private val hadTargetLastTick = ConcurrentHashMap<UUID, Boolean>()
    private val victoryTimers = ConcurrentHashMap<UUID, Int>()
    private val combatDebounceTimers = ConcurrentHashMap<UUID, Int>()
    private val lastProcessedTick = ConcurrentHashMap<UUID, Long>()
    private var currentGameTick: Long = 0

    private val safeRandom = java.util.Random()

    // Special target tracking for Illagers (Victory state)
    private val lastPillagerTargets = ConcurrentHashMap<UUID, UUID?>()
    private val lastVindicatorTargets = ConcurrentHashMap<UUID, UUID?>()
    private val lastEvokerTargets = ConcurrentHashMap<UUID, UUID?>()

    fun getLastGroupSpottedTime(type: IllagerType): Long {
        return lastGroupSpottedSoundTime.getOrDefault(type, 0L)
    }

    fun setLastGroupSpottedTime(type: IllagerType, time: Long) {
        lastGroupSpottedSoundTime[type] = time
    }
    /**
     * Gets or creates a voice manager for an illager
     */
    @JvmStatic
    fun getVoiceManager(illager: IllagerEntity, type: IllagerType): IllagerVoiceManager {
        return voiceManagers.computeIfAbsent(illager.uuid) {
            LOGGER.info("CREATING NEW VOICE MANAGER FOR ${type.name}!")
            IllagerVoiceManager(illager, type)
        }
    }

    /**
     * Gets the illager type for an entity
     */
    @JvmStatic
    private fun getIllagerType(illager: IllagerEntity): IllagerType {
        return when (illager) {
            is EvokerEntity -> IllagerType.EVOKER
            is VindicatorEntity -> IllagerType.VINDICATOR
            is PillagerEntity -> IllagerType.PILLAGER
            else -> IllagerType.EVOKER // Default fallback
        }
    }

    /**
     * Sets an illager entity to the hurt state
     */
    @JvmStatic
    fun setHurtState(entity: IllagerEntity) {
        val id = entity.uuid
        val voiceManager = voiceManagers[id] ?: return
        voiceManager.setState(IllagerState.Hurt)
    }


    /**
     * Sets an illager entity to the victory state
     */
    @JvmStatic
    fun setVictoryState(entity: IllagerEntity) {
        val id = entity.uuid
        val voiceManager = voiceManagers[id] ?: return
        voiceManager.setState(IllagerState.Victory)
        victoryTimers[id] = 100 // 5 seconds
    }

    /**
     * Updates an illager's voice manager and state
     */
    @JvmStatic
    fun updateIllager(illager: IllagerEntity, illagerType: IllagerType) {
        val id = illager.uuid
        // Increment game tick counter
        currentGameTick++

        // Skip if already processed this tick
        if (lastProcessedTick.getOrDefault(id, 0L) == currentGameTick) {
            LOGGER.info("Entity ${id} already processed this tick, skipping")
            return
        }

        // Mark as processed for this tick
        lastProcessedTick[id] = currentGameTick

        // Process normally
        synchronized(id.toString().intern()) {
            val voiceManager = getVoiceManager(illager, illagerType)
            voiceManager.update()
            updateIllagerState(illager)
        }
    }

    /**
     * Updates the state for any illager entity
     */
    @JvmStatic
    private fun updateIllagerState(illager: IllagerEntity) {
        val id = illager.uuid
        val illagerType = getIllagerType(illager)
        val voiceManager = voiceManagers[id] ?: return

        // Get current state values
        val hadTarget = hadTargetLastTick.getOrDefault(id, false)
        var victoryTimer = victoryTimers.getOrDefault(id, 0)
        var combatDebounceTimer = combatDebounceTimers.getOrDefault(id, 0)

        // If we're celebrating a victory, keep in victory state for a while
        if (victoryTimer > 0) {
            victoryTimer--
            victoryTimers[id] = victoryTimer
            return // Stay in victory state
        }

        // Handle combat state stickiness
        if (combatDebounceTimer > 0) {
            combatDebounceTimer--
            combatDebounceTimers[id] = combatDebounceTimer
        }

        val hasTarget = illager.target != null && illager.target!!.isAlive

        // Ensuring Illager do Victory State
        if (illagerType == IllagerType.VINDICATOR) {
            // Debug logging
            // LOGGER.info("Vindicator state update - hasTarget: $hasTarget, hadTarget: $hadTarget, debounce: $combatDebounceTimer")

            val vindicatorId = illager.uuid

            if (hasTarget) {
                // Store current target ID
                lastVindicatorTargets[vindicatorId] = illager.target!!.uuid
            } else if (hadTarget && lastVindicatorTargets.containsKey(vindicatorId)) {
                // Target is gone - force a short victory state
                LOGGER.info("FORCING VINDICATOR VICTORY!")
                voiceManager.setState(IllagerState.Victory)
                victoryTimers[id] = 60 //3 seconds victory period
                hadTargetLastTick[id] = false
                lastVindicatorTargets.remove(vindicatorId)
                return // Skip the rest of the processing
            }

            // Additional logging
            if (!hasTarget && hadTarget) {
                //LOGGER.info("VINDICATOR LOST TARGET - Combat debounce: $combatDebounceTimer")
            }

            if (!hasTarget && hadTarget && combatDebounceTimer <= 0) {
                //LOGGER.info("VINDICATOR VICTORY CONDITION MET!")
            }
        }
        if (illagerType == IllagerType.PILLAGER) {
            val pillagerId = illager.uuid

            if (hasTarget) {
                // Store current target ID
                lastPillagerTargets.getOrPut(pillagerId) { illager.target!!.uuid }
            } else if (hadTarget && lastPillagerTargets.containsKey(pillagerId)) {
                // Target is gone - force a short victory state
                //LOGGER.info("FORCING PILLAGER VICTORY!")
                voiceManager.setState(IllagerState.Victory)
                victoryTimers[id] = 60 // 3 second victory period
                hadTargetLastTick[id] = false
                lastPillagerTargets.remove(pillagerId)
                return // Skip the rest of the processing
            }

            // Additional logging
            if (!hasTarget && hadTarget) {
                //LOGGER.info("PILLAGER LOST TARGET - Combat debounce: $combatDebounceTimer")
            }
        }
        if (illagerType == IllagerType.EVOKER) {
            val evokerId = illager.uuid

            if (hasTarget) {
                // Store current target ID
                lastEvokerTargets.getOrPut(evokerId) { illager.target!!.uuid }
            } else if (hadTarget && lastEvokerTargets.containsKey(evokerId)) {
                // Target is gone - force a short victory state
                //LOGGER.info("FORCING EVOKER VICTORY!")
                voiceManager.setState(IllagerState.Victory)
                victoryTimers[id] = 60 // 3 second victory period
                hadTargetLastTick[id] = false
                lastEvokerTargets.remove(evokerId)
                return // Skip the rest of the processing
            }

            // Additional logging
            if (!hasTarget && hadTarget) {
                //LOGGER.info("EVOKER LOST TARGET - Combat debounce: $combatDebounceTimer")
            }
        }


        if (hasTarget) {
            // We have a valid target
            if (!hadTarget) {
                voiceManager.setState(IllagerState.Spotted)
                hadTargetLastTick[id] = true
            } else {
                voiceManager.setState(IllagerState.Combat)
            }

            // Reset combat debounce timer whenever we have a valid target
            combatDebounceTimer = 60 + safeRandom.nextInt(41) //3-5 seconds
            combatDebounceTimers[id] = combatDebounceTimer
        } else {
            // We don't have a target right now
            if (hadTarget) {
                // Had a target last tick but don't now
                if (combatDebounceTimer <= 0) {
                    // Debounce expired, trigger victory
                    voiceManager.setState(IllagerState.Victory)
                    victoryTimer = 100 // Stay in victory state for 5 seconds
                    victoryTimers[id] = victoryTimer
                    hadTargetLastTick[id] = false
                } else {
                    // Still in debounce period, stay in combat
                    voiceManager.setState(IllagerState.Combat)
                }
            } else {
                // Never had a target, just passive
                voiceManager.setState(IllagerState.Passive)
            }
        }

        // Update had target state
        hadTargetLastTick[id] = hasTarget
    }
}
