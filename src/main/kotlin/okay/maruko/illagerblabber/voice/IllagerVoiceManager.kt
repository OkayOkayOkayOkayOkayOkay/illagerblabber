package okay.maruko.illagerblabber.voice

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.mob.EvokerEntity
import net.minecraft.entity.mob.IllagerEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent
import net.minecraft.util.math.random.Random
import okay.maruko.illagerblabber.IllagerSounds
import org.slf4j.LoggerFactory

class IllagerVoiceManager(private val illager: IllagerEntity, private val illagerType: IllagerType) {

    private val threadSafeRandom = java.util.Random()

    companion object {
        private val LOGGER = LoggerFactory.getLogger("illagerblabber")

        private val PILLAGER_AMBIENT_NOISE_SOUNDS = arrayOf(
            IllagerSounds.PILLAGER_AMBIENT_NOISE_01,
            IllagerSounds.PILLAGER_AMBIENT_NOISE_02,
            IllagerSounds.PILLAGER_AMBIENT_NOISE_03,
            IllagerSounds.PILLAGER_AMBIENT_NOISE_04,
            IllagerSounds.PILLAGER_AMBIENT_NOISE_05,
            IllagerSounds.PILLAGER_AMBIENT_NOISE_06,
            IllagerSounds.PILLAGER_AMBIENT_NOISE_07,
            IllagerSounds.PILLAGER_AMBIENT_NOISE_08,
            IllagerSounds.PILLAGER_AMBIENT_NOISE_09,
            IllagerSounds.PILLAGER_AMBIENT_NOISE_10,
            IllagerSounds.PILLAGER_AMBIENT_NOISE_11,
            IllagerSounds.PILLAGER_AMBIENT_NOISE_12,
            IllagerSounds.PILLAGER_AMBIENT_NOISE_13,
            IllagerSounds.PILLAGER_AMBIENT_NOISE_14
        )

        private val PILLAGER_AMBIENT_TALK_SOUNDS = arrayOf(
            IllagerSounds.PILLAGER_AMBIENT_TALK_01,
            IllagerSounds.PILLAGER_AMBIENT_TALK_02,
            IllagerSounds.PILLAGER_AMBIENT_TALK_03,
            IllagerSounds.PILLAGER_AMBIENT_TALK_04,
            IllagerSounds.PILLAGER_AMBIENT_TALK_05,
            IllagerSounds.PILLAGER_AMBIENT_TALK_06,
            IllagerSounds.PILLAGER_AMBIENT_TALK_07,
            IllagerSounds.PILLAGER_AMBIENT_TALK_08,
            IllagerSounds.PILLAGER_AMBIENT_TALK_09,
            IllagerSounds.PILLAGER_AMBIENT_TALK_10,
            IllagerSounds.PILLAGER_AMBIENT_TALK_11,
            IllagerSounds.PILLAGER_AMBIENT_TALK_12,
            IllagerSounds.PILLAGER_AMBIENT_TALK_13,
            IllagerSounds.PILLAGER_AMBIENT_TALK_14,
            IllagerSounds.PILLAGER_AMBIENT_TALK_15,
            IllagerSounds.PILLAGER_AMBIENT_TALK_16,
            IllagerSounds.PILLAGER_AMBIENT_TALK_17,
            IllagerSounds.PILLAGER_AMBIENT_TALK_18,
            IllagerSounds.PILLAGER_AMBIENT_TALK_19,
            IllagerSounds.PILLAGER_AMBIENT_TALK_20,
            IllagerSounds.PILLAGER_AMBIENT_TALK_21,
            IllagerSounds.PILLAGER_AMBIENT_TALK_22,
            IllagerSounds.PILLAGER_AMBIENT_TALK_23
        )

        private val PILLAGER_SPOTTED_SOUNDS = arrayOf(
            IllagerSounds.PILLAGER_SPOTTED_01,
            IllagerSounds.PILLAGER_SPOTTED_02,
            IllagerSounds.PILLAGER_SPOTTED_03,
            IllagerSounds.PILLAGER_SPOTTED_04,
            IllagerSounds.PILLAGER_SPOTTED_05,
            IllagerSounds.PILLAGER_SPOTTED_06
        )

        private val PILLAGER_BATTLE_SOUNDS = arrayOf(
            IllagerSounds.PILLAGER_BATTLE_01,
            IllagerSounds.PILLAGER_BATTLE_02,
            IllagerSounds.PILLAGER_BATTLE_03,
            IllagerSounds.PILLAGER_BATTLE_04,
            IllagerSounds.PILLAGER_BATTLE_05,
            IllagerSounds.PILLAGER_BATTLE_06,
            IllagerSounds.PILLAGER_BATTLE_07
        )

        private val PILLAGER_HURT_SOUNDS = arrayOf(
            IllagerSounds.PILLAGER_HURT_01,
            IllagerSounds.PILLAGER_HURT_02,
            IllagerSounds.PILLAGER_HURT_03,
            IllagerSounds.PILLAGER_HURT_04,
            IllagerSounds.PILLAGER_HURT_05,
            IllagerSounds.PILLAGER_HURT_06,
            IllagerSounds.PILLAGER_HURT_07,
            IllagerSounds.PILLAGER_HURT_08,
            IllagerSounds.PILLAGER_HURT_09,
            IllagerSounds.PILLAGER_HURT_10,
            IllagerSounds.PILLAGER_HURT_11,
            IllagerSounds.PILLAGER_HURT_12,
            IllagerSounds.PILLAGER_HURT_13,
            IllagerSounds.PILLAGER_HURT_14,
            IllagerSounds.PILLAGER_HURT_15,
            IllagerSounds.PILLAGER_HURT_16,
            IllagerSounds.PILLAGER_HURT_17,
            IllagerSounds.PILLAGER_HURT_18,
            IllagerSounds.PILLAGER_HURT_19,
            IllagerSounds.PILLAGER_HURT_20,
            IllagerSounds.PILLAGER_HURT_21,
            IllagerSounds.PILLAGER_HURT_22,
            IllagerSounds.PILLAGER_HURT_23
        )

        private val PILLAGER_VICTORY_SOUNDS = arrayOf(
            IllagerSounds.PILLAGER_VICTORY_01,
            IllagerSounds.PILLAGER_VICTORY_02,
            IllagerSounds.PILLAGER_VICTORY_03,
            IllagerSounds.PILLAGER_VICTORY_04,
            IllagerSounds.PILLAGER_VICTORY_05,
            IllagerSounds.PILLAGER_VICTORY_06,
            IllagerSounds.PILLAGER_VICTORY_07,
            IllagerSounds.PILLAGER_VICTORY_08,
            IllagerSounds.PILLAGER_VICTORY_09,
            IllagerSounds.PILLAGER_VICTORY_10,
            IllagerSounds.PILLAGER_VICTORY_11,
            IllagerSounds.PILLAGER_VICTORY_12,
            IllagerSounds.PILLAGER_VICTORY_13,
            IllagerSounds.PILLAGER_VICTORY_14,
            IllagerSounds.PILLAGER_VICTORY_15,
            IllagerSounds.PILLAGER_VICTORY_16,
            IllagerSounds.PILLAGER_VICTORY_17,
            IllagerSounds.PILLAGER_VICTORY_18,
            IllagerSounds.PILLAGER_VICTORY_19
        )


        private val VINDICATOR_AMBIENT_NOISE_SOUNDS = arrayOf(
            IllagerSounds.VINDICATOR_AMBIENT_NOISE_01,
            IllagerSounds.VINDICATOR_AMBIENT_NOISE_02,
            IllagerSounds.VINDICATOR_AMBIENT_NOISE_03,
            IllagerSounds.VINDICATOR_AMBIENT_NOISE_04,
            IllagerSounds.VINDICATOR_AMBIENT_NOISE_05,
            IllagerSounds.VINDICATOR_AMBIENT_NOISE_06,
            IllagerSounds.VINDICATOR_AMBIENT_NOISE_07,
            IllagerSounds.VINDICATOR_AMBIENT_NOISE_08,
            IllagerSounds.VINDICATOR_AMBIENT_NOISE_09,
            IllagerSounds.VINDICATOR_AMBIENT_NOISE_10,
            IllagerSounds.VINDICATOR_AMBIENT_NOISE_11,
            IllagerSounds.VINDICATOR_AMBIENT_NOISE_12,
            IllagerSounds.VINDICATOR_AMBIENT_NOISE_13,
            IllagerSounds.VINDICATOR_AMBIENT_NOISE_14,
            IllagerSounds.VINDICATOR_AMBIENT_NOISE_15,
            IllagerSounds.VINDICATOR_AMBIENT_NOISE_16,
            IllagerSounds.VINDICATOR_AMBIENT_NOISE_17,
            IllagerSounds.VINDICATOR_AMBIENT_NOISE_18,
            IllagerSounds.VINDICATOR_AMBIENT_NOISE_19,
            IllagerSounds.VINDICATOR_AMBIENT_NOISE_20,
            IllagerSounds.VINDICATOR_AMBIENT_NOISE_21,
            IllagerSounds.VINDICATOR_AMBIENT_NOISE_22,
            IllagerSounds.VINDICATOR_AMBIENT_NOISE_23,
            IllagerSounds.VINDICATOR_AMBIENT_NOISE_24,
            IllagerSounds.VINDICATOR_AMBIENT_NOISE_25,
            IllagerSounds.VINDICATOR_AMBIENT_NOISE_26,
            IllagerSounds.VINDICATOR_AMBIENT_NOISE_27,
            IllagerSounds.VINDICATOR_AMBIENT_NOISE_28,
            IllagerSounds.VINDICATOR_AMBIENT_NOISE_29,
            IllagerSounds.VINDICATOR_AMBIENT_NOISE_30,
            IllagerSounds.VINDICATOR_AMBIENT_NOISE_31
        )
        private val VINDICATOR_AMBIENT_TALK_SOUNDS = arrayOf(
            IllagerSounds.VINDICATOR_AMBIENT_TALK_01,
            IllagerSounds.VINDICATOR_AMBIENT_TALK_02,
            IllagerSounds.VINDICATOR_AMBIENT_TALK_03,
            IllagerSounds.VINDICATOR_AMBIENT_TALK_04,
            IllagerSounds.VINDICATOR_AMBIENT_TALK_05,
            IllagerSounds.VINDICATOR_AMBIENT_TALK_06,
            IllagerSounds.VINDICATOR_AMBIENT_TALK_07,
            IllagerSounds.VINDICATOR_AMBIENT_TALK_08,
            IllagerSounds.VINDICATOR_AMBIENT_TALK_09,
            IllagerSounds.VINDICATOR_AMBIENT_TALK_10,
            IllagerSounds.VINDICATOR_AMBIENT_TALK_11,
            IllagerSounds.VINDICATOR_AMBIENT_TALK_12,
            IllagerSounds.VINDICATOR_AMBIENT_TALK_13,
            IllagerSounds.VINDICATOR_AMBIENT_TALK_14,
            IllagerSounds.VINDICATOR_AMBIENT_TALK_15,
            IllagerSounds.VINDICATOR_AMBIENT_TALK_16,
            IllagerSounds.VINDICATOR_AMBIENT_TALK_17,
            IllagerSounds.VINDICATOR_AMBIENT_TALK_18,
            IllagerSounds.VINDICATOR_AMBIENT_TALK_19,
            IllagerSounds.VINDICATOR_AMBIENT_TALK_20,
            IllagerSounds.VINDICATOR_AMBIENT_TALK_21,
            IllagerSounds.VINDICATOR_AMBIENT_TALK_22,
            IllagerSounds.VINDICATOR_AMBIENT_TALK_23,
            IllagerSounds.VINDICATOR_AMBIENT_TALK_24
        )

        private val VINDICATOR_BATTLE_SOUNDS = arrayOf(
            IllagerSounds.VINDICATOR_BATTLE_01,
            IllagerSounds.VINDICATOR_BATTLE_02,
            IllagerSounds.VINDICATOR_BATTLE_03,
            IllagerSounds.VINDICATOR_BATTLE_04,
            IllagerSounds.VINDICATOR_BATTLE_05,
            IllagerSounds.VINDICATOR_BATTLE_06,
            IllagerSounds.VINDICATOR_BATTLE_07,
            IllagerSounds.VINDICATOR_BATTLE_08,
            IllagerSounds.VINDICATOR_BATTLE_09,
            IllagerSounds.VINDICATOR_BATTLE_10,
            IllagerSounds.VINDICATOR_BATTLE_11,
            IllagerSounds.VINDICATOR_BATTLE_12,
            IllagerSounds.VINDICATOR_BATTLE_13
        )

        private val VINDICATOR_SPOTTED_SOUNDS = arrayOf(
            IllagerSounds.VINDICATOR_SPOTTED_01,
            IllagerSounds.VINDICATOR_SPOTTED_02,
            IllagerSounds.VINDICATOR_SPOTTED_03,
            IllagerSounds.VINDICATOR_SPOTTED_04,
            IllagerSounds.VINDICATOR_SPOTTED_05,
            IllagerSounds.VINDICATOR_SPOTTED_06,
            IllagerSounds.VINDICATOR_SPOTTED_07,
            IllagerSounds.VINDICATOR_SPOTTED_08,
            IllagerSounds.VINDICATOR_SPOTTED_09,
            IllagerSounds.VINDICATOR_SPOTTED_10,
            IllagerSounds.VINDICATOR_SPOTTED_11
        )

        private val VINDICATOR_HURT_SOUNDS = arrayOf(
            IllagerSounds.VINDICATOR_HURT_01,
            IllagerSounds.VINDICATOR_HURT_02,
            IllagerSounds.VINDICATOR_HURT_03,
            IllagerSounds.VINDICATOR_HURT_04,
            IllagerSounds.VINDICATOR_HURT_05,
            IllagerSounds.VINDICATOR_HURT_06,
            IllagerSounds.VINDICATOR_HURT_07,
            IllagerSounds.VINDICATOR_HURT_08,
            IllagerSounds.VINDICATOR_HURT_09,
            IllagerSounds.VINDICATOR_HURT_10,
            IllagerSounds.VINDICATOR_HURT_11,
            IllagerSounds.VINDICATOR_HURT_12,
            IllagerSounds.VINDICATOR_HURT_13,
            IllagerSounds.VINDICATOR_HURT_14,
            IllagerSounds.VINDICATOR_HURT_15,
            IllagerSounds.VINDICATOR_HURT_16,
            IllagerSounds.VINDICATOR_HURT_17,
            IllagerSounds.VINDICATOR_HURT_18,
            IllagerSounds.VINDICATOR_HURT_19,
            IllagerSounds.VINDICATOR_HURT_20,
            IllagerSounds.VINDICATOR_HURT_21,
            IllagerSounds.VINDICATOR_HURT_22,
            IllagerSounds.VINDICATOR_HURT_23,
            IllagerSounds.VINDICATOR_HURT_24,
            IllagerSounds.VINDICATOR_HURT_25,
            IllagerSounds.VINDICATOR_HURT_26
        )

        private val VINDICATOR_VICTORY_SOUNDS = arrayOf(
            IllagerSounds.VINDICATOR_VICTORY_01,
            IllagerSounds.VINDICATOR_VICTORY_02,
            IllagerSounds.VINDICATOR_VICTORY_03,
            IllagerSounds.VINDICATOR_VICTORY_04,
            IllagerSounds.VINDICATOR_VICTORY_05,
            IllagerSounds.VINDICATOR_VICTORY_06,
            IllagerSounds.VINDICATOR_VICTORY_07,
            IllagerSounds.VINDICATOR_VICTORY_08,
            IllagerSounds.VINDICATOR_VICTORY_09,
            IllagerSounds.VINDICATOR_VICTORY_10,
            IllagerSounds.VINDICATOR_VICTORY_11,
            IllagerSounds.VINDICATOR_VICTORY_12,
            IllagerSounds.VINDICATOR_VICTORY_13
        )

        // Sound category arrays
        private val EVOKER_AMBIENT_NOISE_SOUNDS = arrayOf(
            IllagerSounds.EVOKER_AMBIENT_NOISE_01,
            IllagerSounds.EVOKER_AMBIENT_NOISE_02,
            IllagerSounds.EVOKER_AMBIENT_NOISE_03,
            IllagerSounds.EVOKER_AMBIENT_NOISE_04,
            IllagerSounds.EVOKER_AMBIENT_NOISE_05,
            IllagerSounds.EVOKER_AMBIENT_NOISE_06,
            IllagerSounds.EVOKER_AMBIENT_NOISE_07,
            IllagerSounds.EVOKER_AMBIENT_NOISE_08,
            IllagerSounds.EVOKER_AMBIENT_NOISE_09,
            IllagerSounds.EVOKER_AMBIENT_NOISE_10,
            IllagerSounds.EVOKER_AMBIENT_NOISE_11,
            IllagerSounds.EVOKER_AMBIENT_NOISE_12,
            IllagerSounds.EVOKER_AMBIENT_NOISE_13,
            IllagerSounds.EVOKER_AMBIENT_NOISE_14
        )

        private val EVOKER_AMBIENT_TALK_SOUNDS = arrayOf(
            IllagerSounds.EVOKER_AMBIENT_TALK_01,
            IllagerSounds.EVOKER_AMBIENT_TALK_02,
            IllagerSounds.EVOKER_AMBIENT_TALK_03,
            IllagerSounds.EVOKER_AMBIENT_TALK_04,
            IllagerSounds.EVOKER_AMBIENT_TALK_05,
            IllagerSounds.EVOKER_AMBIENT_TALK_06,
            IllagerSounds.EVOKER_AMBIENT_TALK_07,
            IllagerSounds.EVOKER_AMBIENT_TALK_08,
            IllagerSounds.EVOKER_AMBIENT_TALK_09,
            IllagerSounds.EVOKER_AMBIENT_TALK_10,
            IllagerSounds.EVOKER_AMBIENT_TALK_11,
            IllagerSounds.EVOKER_AMBIENT_TALK_12,
            IllagerSounds.EVOKER_AMBIENT_TALK_13,
            IllagerSounds.EVOKER_AMBIENT_TALK_14,
            IllagerSounds.EVOKER_AMBIENT_TALK_15,
            IllagerSounds.EVOKER_AMBIENT_TALK_16,
            IllagerSounds.EVOKER_AMBIENT_TALK_17,
            IllagerSounds.EVOKER_AMBIENT_TALK_18,
            IllagerSounds.EVOKER_AMBIENT_TALK_19,
            IllagerSounds.EVOKER_AMBIENT_TALK_20,
            IllagerSounds.EVOKER_AMBIENT_TALK_21,
            IllagerSounds.EVOKER_AMBIENT_TALK_22,
            IllagerSounds.EVOKER_AMBIENT_TALK_23,
            IllagerSounds.EVOKER_AMBIENT_TALK_24,
            IllagerSounds.EVOKER_AMBIENT_TALK_25,
            IllagerSounds.EVOKER_AMBIENT_TALK_26,
            IllagerSounds.EVOKER_AMBIENT_TALK_27,
            IllagerSounds.EVOKER_AMBIENT_TALK_28,
            IllagerSounds.EVOKER_AMBIENT_TALK_29,
            IllagerSounds.EVOKER_AMBIENT_TALK_30,
            IllagerSounds.EVOKER_AMBIENT_TALK_31,
            IllagerSounds.EVOKER_AMBIENT_TALK_32,
            IllagerSounds.EVOKER_AMBIENT_TALK_33
        )

        private val EVOKER_BATTLE_SOUNDS = arrayOf(
            IllagerSounds.EVOKER_BATTLE_01,
            IllagerSounds.EVOKER_BATTLE_02,
            IllagerSounds.EVOKER_BATTLE_03,
            IllagerSounds.EVOKER_BATTLE_04,
            IllagerSounds.EVOKER_BATTLE_05,
            IllagerSounds.EVOKER_BATTLE_06,
            IllagerSounds.EVOKER_BATTLE_07,
            IllagerSounds.EVOKER_BATTLE_08,
            IllagerSounds.EVOKER_BATTLE_09,
            IllagerSounds.EVOKER_BATTLE_10,
            IllagerSounds.EVOKER_BATTLE_11,
            IllagerSounds.EVOKER_BATTLE_12,
            IllagerSounds.EVOKER_BATTLE_13,
            IllagerSounds.EVOKER_BATTLE_14
        )

        private val EVOKER_HURT_SOUNDS = arrayOf(
            IllagerSounds.EVOKER_HURT_01,
            IllagerSounds.EVOKER_HURT_02,
            IllagerSounds.EVOKER_HURT_03,
            IllagerSounds.EVOKER_HURT_04,
            IllagerSounds.EVOKER_HURT_05,
            IllagerSounds.EVOKER_HURT_06,
            IllagerSounds.EVOKER_HURT_07,
            IllagerSounds.EVOKER_HURT_08,
            IllagerSounds.EVOKER_HURT_09,
            IllagerSounds.EVOKER_HURT_10,
            IllagerSounds.EVOKER_HURT_11,
            IllagerSounds.EVOKER_HURT_12,
            IllagerSounds.EVOKER_HURT_13,
            IllagerSounds.EVOKER_HURT_14,
            IllagerSounds.EVOKER_HURT_15,
            IllagerSounds.EVOKER_HURT_16,
            IllagerSounds.EVOKER_HURT_17,
            IllagerSounds.EVOKER_HURT_18,
            IllagerSounds.EVOKER_HURT_19
        )

        private val EVOKER_SPOTTED_SOUNDS = arrayOf(
            IllagerSounds.EVOKER_SPOTTED_01,
            IllagerSounds.EVOKER_SPOTTED_02,
            IllagerSounds.EVOKER_SPOTTED_03,
            IllagerSounds.EVOKER_SPOTTED_04,
            IllagerSounds.EVOKER_SPOTTED_05,
            IllagerSounds.EVOKER_SPOTTED_06
        )

        private val EVOKER_VICTORY_SOUNDS = arrayOf(
            IllagerSounds.EVOKER_VICTORY_01,
            IllagerSounds.EVOKER_VICTORY_02,
            IllagerSounds.EVOKER_VICTORY_03,
            IllagerSounds.EVOKER_VICTORY_04,
            IllagerSounds.EVOKER_VICTORY_05,
            IllagerSounds.EVOKER_VICTORY_06,
            IllagerSounds.EVOKER_VICTORY_07,
            IllagerSounds.EVOKER_VICTORY_08,
            IllagerSounds.EVOKER_VICTORY_09,
            IllagerSounds.EVOKER_VICTORY_10,
            IllagerSounds.EVOKER_VICTORY_11,
            IllagerSounds.EVOKER_VICTORY_12,
            IllagerSounds.EVOKER_VICTORY_13,
            IllagerSounds.EVOKER_VICTORY_14
        )
    }

    init {
        val typeName = when(illagerType) {
            IllagerType.EVOKER -> "EVOKER"
            IllagerType.VINDICATOR -> "VINDICATOR"
            IllagerType.PILLAGER -> "PILLAGER"
        }
        LOGGER.info("VOICE MANAGER CREATED FOR $typeName AT ${illager.x}, ${illager.y}, ${illager.z}")
    }

    private var postVictoryCooldown: Int = 0

    // Updated method with illagerType parameter
    private fun adjustCooldownBasedOnCrowding(entity: LivingEntity, state: IllagerState, baseCooldown: Int): Int {
        // Skip adjustment for hurt states
        if (state is IllagerState.Hurt) {
            return baseCooldown
        }

        val world = entity.world

        // Select the correct entity class based on illager type
        val entityClass = when (illagerType) { // Now illagerType is accessible as an instance property
            IllagerType.EVOKER -> net.minecraft.entity.mob.EvokerEntity::class.java
            IllagerType.VINDICATOR -> net.minecraft.entity.mob.VindicatorEntity::class.java
            IllagerType.PILLAGER -> net.minecraft.entity.mob.PillagerEntity::class.java
        }

        val nearbyIllagers = world.getEntitiesByClass(
            entityClass,
            entity.boundingBox.expand(15.0)
        ) { true }

        val count = nearbyIllagers.size

        // If only one illager or somehow zero, use base cooldown
        if (count <= 1) {
            return baseCooldown
        }

        // Different scaling based on state type
        val scaleFactor = when (state) {
            is IllagerState.Passive -> 0.3  // Passive chatter reduces heavily in crowds
            is IllagerState.Combat -> 0.2   // Combat shouts reduce moderately
            is IllagerState.Spotted -> 0.15 // Spotted calls reduce slightly
            is IllagerState.Victory -> 0.1  // Victory celebrations reduce minimally
            else -> 0.25 // Default
        }

        // Calculate adjusted cooldown
        val adjustedCooldown = (baseCooldown * (1 + (count - 1) * scaleFactor)).toInt()

        // For debugging
        //val illagerTypeName = illagerType.name.lowercase().capitalize()
        //LOGGER.info("$illagerTypeName crowd adjustment: $count nearby ${illagerTypeName}s, base cooldown $baseCooldown → $adjustedCooldown")

        return adjustedCooldown
    }


    // The current state of the illager
    private var currentState: IllagerState = IllagerState.Passive
    var hadTarget: Boolean = false

    // The random number generator for choosing sounds and cooldowns
    private val random: Random = illager.world.random

    // Cooldown timers (in ticks - 20 ticks = 1 second)
    private var soundCooldown: Int = 0

    // Post-hurt cooldown timer
    private var postHurtCooldown: Int = 0

    // Whether a sound is currently playing
    private var isSpeaking: Boolean = false

    // Timer for speaking duration
    private var speakingTimer: Int = 0

    // Track current playing sound and its type
    private var currentlyPlayingSound: SoundEvent? = null
    private var currentSoundType: IllagerState? = null
    private var isShortHurtSound: Boolean = false

    // Duration map in ticks (seconds × 20)
    private val soundDurations = mapOf(
        // Add these to the soundDurations map

        // Add these to the soundDurations map


        // Pillager ambient noise durations
        IllagerSounds.PILLAGER_AMBIENT_NOISE_01 to (0.846145 * 20).toInt(), // Track 2
        IllagerSounds.PILLAGER_AMBIENT_NOISE_02 to (0.624989 * 20).toInt(), // Track 3
        IllagerSounds.PILLAGER_AMBIENT_NOISE_03 to (0.620181 * 20).toInt(), // Track 4
        IllagerSounds.PILLAGER_AMBIENT_NOISE_04 to (1.139433 * 20).toInt(), // Track 5
        IllagerSounds.PILLAGER_AMBIENT_NOISE_05 to (0.504807 * 20).toInt(), // Track 6
        IllagerSounds.PILLAGER_AMBIENT_NOISE_06 to (0.826916 * 20).toInt(), // Track 7
        IllagerSounds.PILLAGER_AMBIENT_NOISE_07 to (1.427891 * 20).toInt(), // Track 8
        IllagerSounds.PILLAGER_AMBIENT_NOISE_08 to (0.716349 * 20).toInt(), // Track 9
        IllagerSounds.PILLAGER_AMBIENT_NOISE_09 to (0.995193 * 20).toInt(), // Track 10
        IllagerSounds.PILLAGER_AMBIENT_NOISE_10 to (0.870181 * 20).toInt(), // Track 11
        IllagerSounds.PILLAGER_AMBIENT_NOISE_11 to (0.918277 * 20).toInt(), // Track 12
        IllagerSounds.PILLAGER_AMBIENT_NOISE_12 to (1.235578 * 20).toInt(), // Track 13
        IllagerSounds.PILLAGER_AMBIENT_NOISE_13 to (1.182698 * 20).toInt(), // Track 14
        IllagerSounds.PILLAGER_AMBIENT_NOISE_14 to (1.274036 * 20).toInt(), // Noise 2-14

        // Pillager ambient talk durations
        IllagerSounds.PILLAGER_AMBIENT_TALK_01 to (3.572109 * 20).toInt(), // Track 16
        IllagerSounds.PILLAGER_AMBIENT_TALK_02 to (4.139433 * 20).toInt(), // Track 17
        IllagerSounds.PILLAGER_AMBIENT_TALK_03 to (2.365374 * 20).toInt(), // Track 18
        IllagerSounds.PILLAGER_AMBIENT_TALK_04 to (4.495193 * 20).toInt(), // Track 19
        IllagerSounds.PILLAGER_AMBIENT_TALK_05 to (1.173084 * 20).toInt(), // Track 20
        IllagerSounds.PILLAGER_AMBIENT_TALK_06 to (3.740385 * 20).toInt(), // Track 21
        IllagerSounds.PILLAGER_AMBIENT_TALK_07 to (4.033651 * 20).toInt(), // Track 22
        IllagerSounds.PILLAGER_AMBIENT_TALK_08 to (2.110567 * 20).toInt(), // Track 23
        IllagerSounds.PILLAGER_AMBIENT_TALK_09 to (4.004807 * 20).toInt(), // Track 24
        IllagerSounds.PILLAGER_AMBIENT_TALK_10 to (2.096145 * 20).toInt(), // Track 25
        IllagerSounds.PILLAGER_AMBIENT_TALK_11 to (2.192313 * 20).toInt(), // Track 26
        IllagerSounds.PILLAGER_AMBIENT_TALK_12 to (1.865374 * 20).toInt(), // Track 27
        IllagerSounds.PILLAGER_AMBIENT_TALK_13 to (2.471156 * 20).toInt(), // Track 28
        IllagerSounds.PILLAGER_AMBIENT_TALK_14 to (2.504807 * 20).toInt(), // Track 29
        IllagerSounds.PILLAGER_AMBIENT_TALK_15 to (2.971156 * 20).toInt(), // Track 30
        IllagerSounds.PILLAGER_AMBIENT_TALK_16 to (2.163469 * 20).toInt(), // Track 31
        IllagerSounds.PILLAGER_AMBIENT_TALK_17 to (2.764422 * 20).toInt(), // Track 32
        IllagerSounds.PILLAGER_AMBIENT_TALK_18 to (3.038458 * 20).toInt(), // Track 33
        IllagerSounds.PILLAGER_AMBIENT_TALK_19 to (2.331723 * 20).toInt(), // Track 34
        IllagerSounds.PILLAGER_AMBIENT_TALK_20 to (2.716349 * 20).toInt(), // Track 35
        IllagerSounds.PILLAGER_AMBIENT_TALK_21 to (1.668277 * 20).toInt(), // Track 36
        IllagerSounds.PILLAGER_AMBIENT_TALK_22 to (2.129819 * 20).toInt(), // Track 37
        IllagerSounds.PILLAGER_AMBIENT_TALK_23 to (3.528844 * 20).toInt(), // Talk 16-37

        // Pillager spotted durations
        IllagerSounds.PILLAGER_SPOTTED_01 to (4.350952 * 20).toInt(), // Track 39
        IllagerSounds.PILLAGER_SPOTTED_02 to (3.129819 * 20).toInt(), // Track 40
        IllagerSounds.PILLAGER_SPOTTED_03 to (1.514422 * 20).toInt(), // Track 41
        IllagerSounds.PILLAGER_SPOTTED_04 to (3.355760 * 20).toInt(), // Track 42
        IllagerSounds.PILLAGER_SPOTTED_05 to (3.072109 * 20).toInt(), // Track 43
        IllagerSounds.PILLAGER_SPOTTED_06 to (2.269229 * 20).toInt(), // Spotted 39-43

        // Pillager battle durations
        IllagerSounds.PILLAGER_BATTLE_01 to (0.812494 * 20).toInt(), // Track 45
        IllagerSounds.PILLAGER_BATTLE_02 to (0.802880 * 20).toInt(), // Track 46
        IllagerSounds.PILLAGER_BATTLE_03 to (2.447120 * 20).toInt(), // Track 47
        IllagerSounds.PILLAGER_BATTLE_04 to (3.533651 * 20).toInt(), // Track 48
        IllagerSounds.PILLAGER_BATTLE_05 to (3.639433 * 20).toInt(), // Track 49
        IllagerSounds.PILLAGER_BATTLE_06 to (3.879819 * 20).toInt(), // Track 50
        IllagerSounds.PILLAGER_BATTLE_07 to (2.086531 * 20).toInt(), // Battle 45-50

        // Pillager hurt durations
        IllagerSounds.PILLAGER_HURT_01 to (0.908662 * 20).toInt(), // Track 52
        IllagerSounds.PILLAGER_HURT_02 to (1.100952 * 20).toInt(), // Track 53
        IllagerSounds.PILLAGER_HURT_03 to (1.125011 * 20).toInt(), // Track 54
        IllagerSounds.PILLAGER_HURT_04 to (0.894240 * 20).toInt(), // Track 55
        IllagerSounds.PILLAGER_HURT_05 to (0.764422 * 20).toInt(), // Track 56
        IllagerSounds.PILLAGER_HURT_06 to (0.769229 * 20).toInt(), // Track 57
        IllagerSounds.PILLAGER_HURT_07 to (0.778844 * 20).toInt(), // Track 58
        IllagerSounds.PILLAGER_HURT_08 to (0.884626 * 20).toInt(), // Track 59
        IllagerSounds.PILLAGER_HURT_09 to (0.764422 * 20).toInt(), // Track 60
        IllagerSounds.PILLAGER_HURT_10 to (0.788458 * 20).toInt(), // Track 61
        IllagerSounds.PILLAGER_HURT_11 to (0.764422 * 20).toInt(), // Track 62
        IllagerSounds.PILLAGER_HURT_12 to (0.764422 * 20).toInt(), // Track 63
        IllagerSounds.PILLAGER_HURT_13 to (1.043265 * 20).toInt(), // Track 64
        IllagerSounds.PILLAGER_HURT_14 to (0.865374 * 20).toInt(), // Track 65
        IllagerSounds.PILLAGER_HURT_15 to (0.817302 * 20).toInt(), // Track 66
        IllagerSounds.PILLAGER_HURT_16 to (1.312494 * 20).toInt(), // Track 67
        IllagerSounds.PILLAGER_HURT_17 to (0.831723 * 20).toInt(), // Track 68
        IllagerSounds.PILLAGER_HURT_18 to (0.793265 * 20).toInt(), // Track 69
        IllagerSounds.PILLAGER_HURT_19 to (0.899048 * 20).toInt(), // Track 70
        IllagerSounds.PILLAGER_HURT_20 to (0.908662 * 20).toInt(), // Track 71
        IllagerSounds.PILLAGER_HURT_21 to (1.019229 * 20).toInt(), // Track 72
        IllagerSounds.PILLAGER_HURT_22 to (1.076916 * 20).toInt(), // Track 73
        IllagerSounds.PILLAGER_HURT_23 to (1.033651 * 20).toInt(), // HURT 52-73

        // Pillager victory durations
        IllagerSounds.PILLAGER_VICTORY_01 to (4.389433 * 20).toInt(), // Track 75
        IllagerSounds.PILLAGER_VICTORY_02 to (1.658662 * 20).toInt(), // Track 76
        IllagerSounds.PILLAGER_VICTORY_03 to (1.576916 * 20).toInt(), // Track 77
        IllagerSounds.PILLAGER_VICTORY_04 to (1.548073 * 20).toInt(), // Track 78
        IllagerSounds.PILLAGER_VICTORY_05 to (1.062494 * 20).toInt(), // Track 79
        IllagerSounds.PILLAGER_VICTORY_06 to (0.923084 * 20).toInt(), // Track 80
        IllagerSounds.PILLAGER_VICTORY_07 to (1.475964 * 20).toInt(), // Track 81
        IllagerSounds.PILLAGER_VICTORY_08 to (2.947120 * 20).toInt(), // Track 82
        IllagerSounds.PILLAGER_VICTORY_09 to (2.812494 * 20).toInt(), // Track 83
        IllagerSounds.PILLAGER_VICTORY_10 to (1.846145 * 20).toInt(), // Track 84
        IllagerSounds.PILLAGER_VICTORY_11 to (1.394240 * 20).toInt(), // Track 85
        IllagerSounds.PILLAGER_VICTORY_12 to (1.408662 * 20).toInt(), // Track 86
        IllagerSounds.PILLAGER_VICTORY_13 to (1.610567 * 20).toInt(), // Track 87
        IllagerSounds.PILLAGER_VICTORY_14 to (1.408662 * 20).toInt(), // Track 88
        IllagerSounds.PILLAGER_VICTORY_15 to (1.062494 * 20).toInt(), // Track 89
        IllagerSounds.PILLAGER_VICTORY_16 to (1.375011 * 20).toInt(), // Track 90
        IllagerSounds.PILLAGER_VICTORY_17 to (2.096145 * 20).toInt(), // Track 91
        IllagerSounds.PILLAGER_VICTORY_18 to (3.413469 * 20).toInt(), // Track 92
        IllagerSounds.PILLAGER_VICTORY_19 to (3.745193 * 20).toInt(), // Victory 75-92

        // Vindicator ambient noise durations
        IllagerSounds.VINDICATOR_AMBIENT_NOISE_01 to (0.538458 * 20).toInt(), // Track 2
        IllagerSounds.VINDICATOR_AMBIENT_NOISE_02 to (0.394240 * 20).toInt(), // Track 3
        IllagerSounds.VINDICATOR_AMBIENT_NOISE_03 to (0.677891 * 20).toInt(), // Track 4
        IllagerSounds.VINDICATOR_AMBIENT_NOISE_04 to (0.865374 * 20).toInt(), // Track 5
        IllagerSounds.VINDICATOR_AMBIENT_NOISE_05 to (0.322109 * 20).toInt(), // Track 6
        IllagerSounds.VINDICATOR_AMBIENT_NOISE_06 to (0.389433 * 20).toInt(), // Track 7
        IllagerSounds.VINDICATOR_AMBIENT_NOISE_07 to (0.802880 * 20).toInt(), // Track 8
        IllagerSounds.VINDICATOR_AMBIENT_NOISE_08 to (0.543265 * 20).toInt(), // Track 9
        IllagerSounds.VINDICATOR_AMBIENT_NOISE_09 to (0.740385 * 20).toInt(), // Track 10
        IllagerSounds.VINDICATOR_AMBIENT_NOISE_10 to (0.543265 * 20).toInt(), // Track 11
        IllagerSounds.VINDICATOR_AMBIENT_NOISE_11 to (0.552880 * 20).toInt(), // Track 12
        IllagerSounds.VINDICATOR_AMBIENT_NOISE_12 to (0.552880 * 20).toInt(), // Track 13
        IllagerSounds.VINDICATOR_AMBIENT_NOISE_13 to (0.725964 * 20).toInt(), // Track 14
        IllagerSounds.VINDICATOR_AMBIENT_NOISE_14 to (0.370181 * 20).toInt(), // Track 15
        IllagerSounds.VINDICATOR_AMBIENT_NOISE_15 to (0.514422 * 20).toInt(), // Track 16
        IllagerSounds.VINDICATOR_AMBIENT_NOISE_16 to (0.947120 * 20).toInt(), // Track 17
        IllagerSounds.VINDICATOR_AMBIENT_NOISE_17 to (1.004807 * 20).toInt(), // Track 18
        IllagerSounds.VINDICATOR_AMBIENT_NOISE_18 to (0.543265 * 20).toInt(), // Track 19
        IllagerSounds.VINDICATOR_AMBIENT_NOISE_19 to (0.432698 * 20).toInt(), // Track 20
        IllagerSounds.VINDICATOR_AMBIENT_NOISE_20 to (1.235578 * 20).toInt(), // Track 21
        IllagerSounds.VINDICATOR_AMBIENT_NOISE_21 to (1.038458 * 20).toInt(), // Track 22
        IllagerSounds.VINDICATOR_AMBIENT_NOISE_22 to (0.596145 * 20).toInt(), // Track 23
        IllagerSounds.VINDICATOR_AMBIENT_NOISE_23 to (1.081723 * 20).toInt(), // Track 24
        IllagerSounds.VINDICATOR_AMBIENT_NOISE_24 to (0.802880 * 20).toInt(), // Track 25
        IllagerSounds.VINDICATOR_AMBIENT_NOISE_25 to (0.552880 * 20).toInt(), // Track 26
        IllagerSounds.VINDICATOR_AMBIENT_NOISE_26 to (0.423084 * 20).toInt(), // Track 27
        IllagerSounds.VINDICATOR_AMBIENT_NOISE_27 to (0.687506 * 20).toInt(), // Track 28
        IllagerSounds.VINDICATOR_AMBIENT_NOISE_28 to (0.923084 * 20).toInt(), // Track 29
        IllagerSounds.VINDICATOR_AMBIENT_NOISE_29 to (0.966349 * 20).toInt(), // Track 30
        IllagerSounds.VINDICATOR_AMBIENT_NOISE_30 to (0.490385 * 20).toInt(), // Track 31
        IllagerSounds.VINDICATOR_AMBIENT_NOISE_31 to (0.480771 * 20).toInt(), // Track 32

         // Vindicator ambient talk durations
        IllagerSounds.VINDICATOR_AMBIENT_TALK_01 to (1.274036 * 20).toInt(), // Track 34
        IllagerSounds.VINDICATOR_AMBIENT_TALK_02 to (1.870181 * 20).toInt(), // Track 35
        IllagerSounds.VINDICATOR_AMBIENT_TALK_03 to (1.634626 * 20).toInt(), // Track 36
        IllagerSounds.VINDICATOR_AMBIENT_TALK_04 to (1.692313 * 20).toInt(), // Track 37
        IllagerSounds.VINDICATOR_AMBIENT_TALK_05 to (1.899048 * 20).toInt(), // Track 38
        IllagerSounds.VINDICATOR_AMBIENT_TALK_06 to (2.759615 * 20).toInt(), // Track 39
        IllagerSounds.VINDICATOR_AMBIENT_TALK_07 to (1.677891 * 20).toInt(), // Track 40
        IllagerSounds.VINDICATOR_AMBIENT_TALK_08 to (1.697120 * 20).toInt(), // Track 41
        IllagerSounds.VINDICATOR_AMBIENT_TALK_09 to (1.658662 * 20).toInt(), // Track 42
        IllagerSounds.VINDICATOR_AMBIENT_TALK_10 to (0.923084 * 20).toInt(), // Track 43
        IllagerSounds.VINDICATOR_AMBIENT_TALK_11 to (1.384626 * 20).toInt(), // Track 44
        IllagerSounds.VINDICATOR_AMBIENT_TALK_12 to (2.158662 * 20).toInt(), // Track 45
        IllagerSounds.VINDICATOR_AMBIENT_TALK_13 to (1.774036 * 20).toInt(), // Track 46
        IllagerSounds.VINDICATOR_AMBIENT_TALK_14 to (1.629819 * 20).toInt(), // Track 47
        IllagerSounds.VINDICATOR_AMBIENT_TALK_15 to (1.788458 * 20).toInt(), // Track 48
        IllagerSounds.VINDICATOR_AMBIENT_TALK_16 to (1.826916 * 20).toInt(), // Track 49
        IllagerSounds.VINDICATOR_AMBIENT_TALK_17 to (2.009615 * 20).toInt(), // Track 50
        IllagerSounds.VINDICATOR_AMBIENT_TALK_18 to (1.365374 * 20).toInt(), // Track 51
        IllagerSounds.VINDICATOR_AMBIENT_TALK_19 to (1.456735 * 20).toInt(), // Track 52
        IllagerSounds.VINDICATOR_AMBIENT_TALK_20 to (1.783651 * 20).toInt(), // Track 53
        IllagerSounds.VINDICATOR_AMBIENT_TALK_21 to (1.596145 * 20).toInt(), // Track 54
        IllagerSounds.VINDICATOR_AMBIENT_TALK_22 to (2.326916 * 20).toInt(), // Track 55
        IllagerSounds.VINDICATOR_AMBIENT_TALK_23 to (3.134626 * 20).toInt(), // Track 56
        IllagerSounds.VINDICATOR_AMBIENT_TALK_24 to (1.485578 * 20).toInt(), // Track 57

        // Vindicator spotted durations
        IllagerSounds.VINDICATOR_SPOTTED_01 to (1.480771 * 20).toInt(), // Track 59
        IllagerSounds.VINDICATOR_SPOTTED_02 to (1.158662 * 20).toInt(), // Track 60
        IllagerSounds.VINDICATOR_SPOTTED_03 to (1.591338 * 20).toInt(), // Track 61
        IllagerSounds.VINDICATOR_SPOTTED_04 to (1.278844 * 20).toInt(), // Track 62
        IllagerSounds.VINDICATOR_SPOTTED_05 to (2.067302 * 20).toInt(), // Track 63
        IllagerSounds.VINDICATOR_SPOTTED_06 to (1.403855 * 20).toInt(), // Track 64
        IllagerSounds.VINDICATOR_SPOTTED_07 to (1.682698 * 20).toInt(), // Track 65
        IllagerSounds.VINDICATOR_SPOTTED_08 to (2.134626 * 20).toInt(), // Track 66
        IllagerSounds.VINDICATOR_SPOTTED_09 to (0.937506 * 20).toInt(), // Track 67
        IllagerSounds.VINDICATOR_SPOTTED_10 to (0.716349 * 20).toInt(), // Track 68
        IllagerSounds.VINDICATOR_SPOTTED_11 to (1.048073 * 20).toInt(), // Track 69

        // Vindicator battle durations
        IllagerSounds.VINDICATOR_BATTLE_01 to (1.836531 * 20).toInt(), // Track 71
        IllagerSounds.VINDICATOR_BATTLE_02 to (2.038458 * 20).toInt(), // Track 72
        IllagerSounds.VINDICATOR_BATTLE_03 to (1.298073 * 20).toInt(), // Track 73
        IllagerSounds.VINDICATOR_BATTLE_04 to (1.423084 * 20).toInt(), // Track 74
        IllagerSounds.VINDICATOR_BATTLE_05 to (1.615374 * 20).toInt(), // Track 75
        IllagerSounds.VINDICATOR_BATTLE_06 to (1.158662 * 20).toInt(), // Track 76
        IllagerSounds.VINDICATOR_BATTLE_07 to (1.221156 * 20).toInt(), // Track 77
        IllagerSounds.VINDICATOR_BATTLE_08 to (1.543265 * 20).toInt(), // Track 78
        IllagerSounds.VINDICATOR_BATTLE_09 to (0.759615 * 20).toInt(), // Track 79
        IllagerSounds.VINDICATOR_BATTLE_10 to (2.024036 * 20).toInt(), // Track 80
        IllagerSounds.VINDICATOR_BATTLE_11 to (1.144240 * 20).toInt(), // Track 81
        IllagerSounds.VINDICATOR_BATTLE_12 to (1.346145 * 20).toInt(), // Track 82
        IllagerSounds.VINDICATOR_BATTLE_13 to (2.293265 * 20).toInt(), // Battle 71-82

        // Vindicator hurt durations
        IllagerSounds.VINDICATOR_HURT_01 to (0.975964 * 20).toInt(), // Track 84
        IllagerSounds.VINDICATOR_HURT_02 to (0.822109 * 20).toInt(), // Track 85
        IllagerSounds.VINDICATOR_HURT_03 to (0.754807 * 20).toInt(), // Track 86
        IllagerSounds.VINDICATOR_HURT_04 to (0.750000 * 20).toInt(), // Track 87
        IllagerSounds.VINDICATOR_HURT_05 to (0.778844 * 20).toInt(), // Track 88
        IllagerSounds.VINDICATOR_HURT_06 to (0.850952 * 20).toInt(), // Track 89
        IllagerSounds.VINDICATOR_HURT_07 to (0.783651 * 20).toInt(), // Track 90
        IllagerSounds.VINDICATOR_HURT_08 to (0.745193 * 20).toInt(), // Track 91
        IllagerSounds.VINDICATOR_HURT_09 to (0.725964 * 20).toInt(), // Track 92
        IllagerSounds.VINDICATOR_HURT_10 to (0.706735 * 20).toInt(), // Track 93
        IllagerSounds.VINDICATOR_HURT_11 to (0.706735 * 20).toInt(), // Track 94
        IllagerSounds.VINDICATOR_HURT_12 to (0.649048 * 20).toInt(), // Track 95
        IllagerSounds.VINDICATOR_HURT_13 to (0.524036 * 20).toInt(), // Track 96
        IllagerSounds.VINDICATOR_HURT_14 to (0.514422 * 20).toInt(), // Track 97
        IllagerSounds.VINDICATOR_HURT_15 to (0.528844 * 20).toInt(), // Track 98
        IllagerSounds.VINDICATOR_HURT_16 to (0.471156 * 20).toInt(), // Track 99
        IllagerSounds.VINDICATOR_HURT_17 to (0.500000 * 20).toInt(), // Track 100
        IllagerSounds.VINDICATOR_HURT_18 to (0.485578 * 20).toInt(), // Track 101
        IllagerSounds.VINDICATOR_HURT_19 to (0.591338 * 20).toInt(), // Track 102
        IllagerSounds.VINDICATOR_HURT_20 to (0.548073 * 20).toInt(), // Track 103
        IllagerSounds.VINDICATOR_HURT_21 to (0.543265 * 20).toInt(), // Track 104
        IllagerSounds.VINDICATOR_HURT_22 to (0.480771 * 20).toInt(), // Track 105
        IllagerSounds.VINDICATOR_HURT_23 to (0.557687 * 20).toInt(), // Track 106
        IllagerSounds.VINDICATOR_HURT_24 to (0.764422 * 20).toInt(), // Track 107
        IllagerSounds.VINDICATOR_HURT_25 to (0.798073 * 20).toInt(), // Track 108
        IllagerSounds.VINDICATOR_HURT_26 to (1.081723 * 20).toInt(), // Hurt 84-108

        // Vindicator victory durations
        IllagerSounds.VINDICATOR_VICTORY_01 to (1.389433 * 20).toInt(), // Track 110
        IllagerSounds.VINDICATOR_VICTORY_02 to (0.995193 * 20).toInt(), // Track 111
        IllagerSounds.VINDICATOR_VICTORY_03 to (1.557687 * 20).toInt(), // Track 112
        IllagerSounds.VINDICATOR_VICTORY_04 to (1.134626 * 20).toInt(), // Track 113
        IllagerSounds.VINDICATOR_VICTORY_05 to (1.163469 * 20).toInt(), // Track 114
        IllagerSounds.VINDICATOR_VICTORY_06 to (1.538458 * 20).toInt(), // Track 115
        IllagerSounds.VINDICATOR_VICTORY_07 to (1.187506 * 20).toInt(), // Track 116
        IllagerSounds.VINDICATOR_VICTORY_08 to (0.788458 * 20).toInt(), // Track 117
        IllagerSounds.VINDICATOR_VICTORY_09 to (1.413469 * 20).toInt(), // Track 118
        IllagerSounds.VINDICATOR_VICTORY_10 to (1.875011 * 20).toInt(), // Track 119
        IllagerSounds.VINDICATOR_VICTORY_11 to (1.620181 * 20).toInt(), // Track 120
        IllagerSounds.VINDICATOR_VICTORY_12 to (1.956735 * 20).toInt(), // Track 121
        IllagerSounds.VINDICATOR_VICTORY_13 to (2.158662 * 20).toInt(), // Victory 110-121

        // Ambient noises - all 14 files
        IllagerSounds.EVOKER_AMBIENT_NOISE_01 to (1.68 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_NOISE_02 to (0.43 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_NOISE_03 to (1.85 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_NOISE_04 to (0.51 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_NOISE_05 to (1.02 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_NOISE_06 to (1.04 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_NOISE_07 to (1.43 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_NOISE_08 to (1.38 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_NOISE_09 to (3.12 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_NOISE_10 to (0.77 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_NOISE_11 to (2.31 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_NOISE_12 to (2.53 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_NOISE_13 to (1.04 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_NOISE_14 to (0.92 * 20).toInt(),

        // Ambient talk - all 33 files
        IllagerSounds.EVOKER_AMBIENT_TALK_01 to (4.62 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_TALK_02 to (2.89 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_TALK_03 to (1.85 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_TALK_04 to (3.23 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_TALK_05 to (5.08 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_TALK_06 to (3.69 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_TALK_07 to (5.88 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_TALK_08 to (5.97 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_TALK_09 to (4.38 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_TALK_10 to (5.22 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_TALK_11 to (1.94 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_TALK_12 to (1.80 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_TALK_13 to (4.72 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_TALK_14 to (3.69 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_TALK_15 to (3.00 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_TALK_16 to (1.84 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_TALK_17 to (5.12 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_TALK_18 to (2.82 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_TALK_19 to (2.20 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_TALK_20 to (1.62 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_TALK_21 to (2.32 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_TALK_22 to (5.15 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_TALK_23 to (5.04 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_TALK_24 to (2.94 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_TALK_25 to (3.96 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_TALK_26 to (4.25 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_TALK_27 to (3.37 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_TALK_28 to (6.51 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_TALK_29 to (3.32 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_TALK_30 to (7.74 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_TALK_31 to (5.54 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_TALK_32 to (3.87 * 20).toInt(),
        IllagerSounds.EVOKER_AMBIENT_TALK_33 to (5.49 * 20).toInt(),

        // Battle sounds - all 14 files
        IllagerSounds.EVOKER_BATTLE_01 to (2.94 * 20).toInt(),
        IllagerSounds.EVOKER_BATTLE_02 to (2.37 * 20).toInt(),
        IllagerSounds.EVOKER_BATTLE_03 to (1.24 * 20).toInt(),
        IllagerSounds.EVOKER_BATTLE_04 to (1.22 * 20).toInt(),
        IllagerSounds.EVOKER_BATTLE_05 to (1.93 * 20).toInt(),
        IllagerSounds.EVOKER_BATTLE_06 to (2.22 * 20).toInt(),
        IllagerSounds.EVOKER_BATTLE_07 to (1.91 * 20).toInt(),
        IllagerSounds.EVOKER_BATTLE_08 to (2.52 * 20).toInt(),
        IllagerSounds.EVOKER_BATTLE_09 to (1.12 * 20).toInt(),
        IllagerSounds.EVOKER_BATTLE_10 to (3.03 * 20).toInt(),
        IllagerSounds.EVOKER_BATTLE_11 to (1.37 * 20).toInt(),
        IllagerSounds.EVOKER_BATTLE_12 to (2.00 * 20).toInt(),
        IllagerSounds.EVOKER_BATTLE_13 to (1.79 * 20).toInt(),
        IllagerSounds.EVOKER_BATTLE_14 to (2.13 * 20).toInt(),

        // Hurt sounds - all 19 files
        IllagerSounds.EVOKER_HURT_01 to (2.29 * 20).toInt(),
        IllagerSounds.EVOKER_HURT_02 to (0.32 * 20).toInt(),
        IllagerSounds.EVOKER_HURT_03 to (0.25 * 20).toInt(),
        IllagerSounds.EVOKER_HURT_04 to (0.74 * 20).toInt(),
        IllagerSounds.EVOKER_HURT_05 to (0.31 * 20).toInt(),
        IllagerSounds.EVOKER_HURT_06 to (0.85 * 20).toInt(),
        IllagerSounds.EVOKER_HURT_07 to (0.31 * 20).toInt(),
        IllagerSounds.EVOKER_HURT_08 to (0.32 * 20).toInt(),
        IllagerSounds.EVOKER_HURT_09 to (0.35 * 20).toInt(),
        IllagerSounds.EVOKER_HURT_10 to (0.93 * 20).toInt(),
        IllagerSounds.EVOKER_HURT_11 to (1.39 * 20).toInt(),
        IllagerSounds.EVOKER_HURT_12 to (1.12 * 20).toInt(),
        IllagerSounds.EVOKER_HURT_13 to (1.27 * 20).toInt(),
        IllagerSounds.EVOKER_HURT_14 to (1.61 * 20).toInt(),
        IllagerSounds.EVOKER_HURT_15 to (1.84 * 20).toInt(),
        IllagerSounds.EVOKER_HURT_16 to (1.31 * 20).toInt(),
        IllagerSounds.EVOKER_HURT_17 to (1.27 * 20).toInt(),
        IllagerSounds.EVOKER_HURT_18 to (0.58 * 20).toInt(),
        IllagerSounds.EVOKER_HURT_19 to (0.58 * 20).toInt(),

        // Spotted sounds - all 6 files
        IllagerSounds.EVOKER_SPOTTED_01 to (3.86 * 20).toInt(),
        IllagerSounds.EVOKER_SPOTTED_02 to (2.20 * 20).toInt(),
        IllagerSounds.EVOKER_SPOTTED_03 to (3.18 * 20).toInt(),
        IllagerSounds.EVOKER_SPOTTED_04 to (3.08 * 20).toInt(),
        IllagerSounds.EVOKER_SPOTTED_05 to (5.28 * 20).toInt(),
        IllagerSounds.EVOKER_SPOTTED_06 to (3.54 * 20).toInt(),

        // Victory sounds - all 14 files
        IllagerSounds.EVOKER_VICTORY_01 to (3.55 * 20).toInt(),
        IllagerSounds.EVOKER_VICTORY_02 to (0.39 * 20).toInt(),
        IllagerSounds.EVOKER_VICTORY_03 to (0.47 * 20).toInt(),
        IllagerSounds.EVOKER_VICTORY_04 to (1.30 * 20).toInt(),
        IllagerSounds.EVOKER_VICTORY_05 to (1.94 * 20).toInt(),
        IllagerSounds.EVOKER_VICTORY_06 to (1.79 * 20).toInt(),
        IllagerSounds.EVOKER_VICTORY_07 to (2.68 * 20).toInt(),
        IllagerSounds.EVOKER_VICTORY_08 to (1.36 * 20).toInt(),
        IllagerSounds.EVOKER_VICTORY_09 to (2.20 * 20).toInt(),
        IllagerSounds.EVOKER_VICTORY_10 to (3.55 * 20).toInt(),
        IllagerSounds.EVOKER_VICTORY_11 to (3.10 * 20).toInt(),
        IllagerSounds.EVOKER_VICTORY_12 to (5.05 * 20).toInt(),
        IllagerSounds.EVOKER_VICTORY_13 to (2.13 * 20).toInt(),
        IllagerSounds.EVOKER_VICTORY_14 to (0.42 * 20).toInt()
    )

    // Update the manager - called every tick
    fun update() {
        // Handle the speaking timer
        if (isSpeaking) {
            speakingTimer--
            if (speakingTimer <= 0) {
                isSpeaking = false
                currentlyPlayingSound = null
                currentSoundType = null
                isShortHurtSound = false
                //Logger.info("FINISHED PLAYING SOUND")

                // Get base cooldown based on state
                val baseCooldown = when (currentState){
                    is IllagerState.Passive -> 30 + random.nextInt(60) //
                    is IllagerState.Combat -> 40 + random.nextInt(60) // 2-5 Seconds
                    is IllagerState.Spotted -> 100 + random.nextInt(40) // 5-7 seconds
                    is IllagerState.Hurt -> 20 + random.nextInt(20) // 1-2 seconds
                    is IllagerState.Victory -> 40 + random.nextInt(20) // 2-3 seconds
                }

                // Apply crowd adjustment
                soundCooldown = adjustCooldownBasedOnCrowding(illager, currentState, baseCooldown)

                if (currentState is IllagerState.Victory) {
                    postVictoryCooldown = 100  // 5 seconds of cooldown after victory
                    LOGGER.info("${illagerType.name} VICTORY COMPLETE - Setting post-victory cooldown")
                }

                // If we just finished a hurt sound, set post-hurt cooldown
                if (currentState is IllagerState.Hurt) {
                    //Logger.info("SETTING POST-HURT COOLDOWN")
                    postHurtCooldown = 60  // 3 seconds
                }


            }
        }

        // Decrease cooldown timers
        if (soundCooldown > 0) {
            soundCooldown--
        }

        if (postHurtCooldown > 0) {
            postHurtCooldown--
            // Skip all sound playing during post-hurt cooldown
            return
        }

        if (postVictoryCooldown > 0) {
            postVictoryCooldown--

            // During victory cooldown, only allow hurt sounds
            if (currentState !is IllagerState.Hurt) {
                return // Skip sound playing for non-hurt states
            }
        }

        // Check if we should play a high priority sound
        if (shouldPlaySoundForState(currentState)) {
            // High priority sounds can play even if something else is speaking
            if (isSpeaking) {
                if (shouldInterruptCurrentSound(currentState)) {
                    // If we can interrupt, reset speaking state
                    isSpeaking = false
                    currentlyPlayingSound = null
                    currentSoundType = null
                    isShortHurtSound = false
                    //Logger.info("SOUND INTERRUPTED FOR HIGH PRIORITY STATE")
                    playAppropriateSound()
                }
            } else {
                // Nothing speaking, play the high priority sound
                playAppropriateSound()
            }
        } else if (soundCooldown <= 0 && !isSpeaking) {
            // Regular sound playing when nothing is playing and not on cooldown
            playAppropriateSound()
        }
    }

    // Determine if the current state should play immediately
    private fun shouldPlaySoundForState(state: IllagerState): Boolean {
        // Special cases for high-priority states
        return when (state) {
            is IllagerState.Hurt -> true  // Hurt always plays
            is IllagerState.Spotted -> true  // Spotted always plays
            else -> false  // Other states follow normal cooldown
        }
    }

    // Replace the problematic method with this:
    private fun shouldInterruptCurrentSound(state: IllagerState): Boolean {
        if (!isSpeaking) return false // Nothing to interrupt

        // Special handling for hurt sounds
        if (state is IllagerState.Hurt) {
            // NEVER interrupt another hurt sound that's already playing
            if (currentSoundType is IllagerState.Hurt) {
                return false
            }

            // Hurt can interrupt any non-hurt sound
            return true
        }

        // Spotted can always interrupt non-hurt sounds
        if (state is IllagerState.Spotted && currentSoundType !is IllagerState.Hurt) {
            return true
        }

        // All other states follow normal rules
        return false
    }


    // In IllagerVoiceManager class (not companion)
    fun setState(state: IllagerState) {
        // For spotted sounds, check group cooldown (except for vindicators)
        if (state is IllagerState.Spotted &&
            (illagerType == IllagerType.PILLAGER || illagerType == IllagerType.EVOKER)) {

            val currentTime = System.currentTimeMillis()
            val lastTime = IllagerVoiceRegistry.getLastGroupSpottedTime(illagerType)

            if (currentTime - lastTime < 3000) { // 3 seconds in ms
                // Someone else already played the spotted sound recently
                currentState = IllagerState.Combat // Go straight to combat instead
                return
            }


            IllagerVoiceRegistry.setLastGroupSpottedTime(illagerType, currentTime)
        }

        // Set the state as normal
        currentState = state
    }


    private fun playAppropriateSound() {
        //LOGGER.info("Selecting sound for state: $currentState")

        // Choose a sound based on current state
        val sound = when (currentState) {
            is IllagerState.Passive -> {
                //Logger.info("Choosing PASSIVE sound")
                choosePassiveSound()
            }
            is IllagerState.Combat -> {
                //Logger.info("Choosing COMBAT sound")
                chooseCombatSound()
            }
            is IllagerState.Spotted -> {
                //Logger.info("Choosing SPOTTED sound")
                chooseSpottedSound()
            }
            is IllagerState.Hurt -> {
                //Logger.info("Choosing HURT sound")
                chooseHurtSound()
            }
            is IllagerState.Victory -> {
                //Logger.info("Choosing VICTORY sound")
                chooseVictorySound()
            }
        }

        //LOGGER.info("Selected sound: $sound")
        playSound(sound)

    }

    // methods to select random sounds from each category
    private fun choosePassiveSound(): SoundEvent {
        // 50% chance for ambient noise, 50% for ambient talk
        return if (random.nextInt(100) < 50) {
            // Choose appropriate ambient noise array
            when (illagerType) {
                IllagerType.EVOKER -> EVOKER_AMBIENT_NOISE_SOUNDS.random(threadSafeRandom)
                IllagerType.VINDICATOR -> VINDICATOR_AMBIENT_NOISE_SOUNDS.random(threadSafeRandom)
                IllagerType.PILLAGER -> PILLAGER_AMBIENT_NOISE_SOUNDS.random(threadSafeRandom)
            }
        } else {
            when (illagerType) {
                IllagerType.EVOKER -> EVOKER_AMBIENT_TALK_SOUNDS.random(threadSafeRandom)
                IllagerType.VINDICATOR -> VINDICATOR_AMBIENT_TALK_SOUNDS.random(threadSafeRandom)
                IllagerType.PILLAGER -> PILLAGER_AMBIENT_TALK_SOUNDS.random(threadSafeRandom)
            }
        }
    }


    private fun chooseSpottedSound(): SoundEvent {
        return when (illagerType) {
            IllagerType.EVOKER -> EVOKER_SPOTTED_SOUNDS.random(threadSafeRandom)
            IllagerType.VINDICATOR -> VINDICATOR_SPOTTED_SOUNDS.random(threadSafeRandom)
            IllagerType.PILLAGER -> PILLAGER_SPOTTED_SOUNDS.random(threadSafeRandom)
        }
    }

    private fun chooseCombatSound(): SoundEvent {
        // 30% chance for ambient noise, 70% for battle sound during combat
        return if (random.nextInt(100) < 30) {
            when (illagerType) {
                IllagerType.EVOKER -> EVOKER_AMBIENT_NOISE_SOUNDS.random(threadSafeRandom)
                IllagerType.VINDICATOR -> VINDICATOR_AMBIENT_NOISE_SOUNDS.random(threadSafeRandom)
                IllagerType.PILLAGER -> PILLAGER_AMBIENT_NOISE_SOUNDS.random(threadSafeRandom)
            }
        } else {
            when (illagerType) {
                IllagerType.EVOKER -> EVOKER_BATTLE_SOUNDS.random(threadSafeRandom)
                IllagerType.VINDICATOR -> VINDICATOR_BATTLE_SOUNDS.random(threadSafeRandom)
                IllagerType.PILLAGER -> PILLAGER_BATTLE_SOUNDS.random(threadSafeRandom)
            }
        }
    }

    private fun chooseHurtSound(): SoundEvent {
        return when (illagerType) {
            IllagerType.EVOKER -> EVOKER_HURT_SOUNDS.random(threadSafeRandom)
            IllagerType.VINDICATOR -> VINDICATOR_HURT_SOUNDS.random(threadSafeRandom)
            IllagerType.PILLAGER -> PILLAGER_HURT_SOUNDS.random(threadSafeRandom)
        }
    }

    private fun chooseVictorySound(): SoundEvent {
        return when (illagerType) {
            IllagerType.EVOKER -> EVOKER_VICTORY_SOUNDS.random(threadSafeRandom)
            IllagerType.VINDICATOR -> VINDICATOR_VICTORY_SOUNDS.random(threadSafeRandom)
            IllagerType.PILLAGER -> PILLAGER_VICTORY_SOUNDS.random(threadSafeRandom)
        }
    }


    private fun playSound(sound: SoundEvent) {
        // Use threadSafeRandom instead of world random
        val randomPitch = when (currentState) {
            is IllagerState.Hurt -> 0.9f + threadSafeRandom.nextFloat() * 0.25f  // Range: 0.75-1.0
            else -> 0.9f + threadSafeRandom.nextFloat() * 0.1f  // Range: 0.8-1.0
        }

        illager.world.playSound(
            null, // No player - play for all nearby
            illager.x, illager.y, illager.z, // Location
            sound, // Which sound
            SoundCategory.HOSTILE, // Category
            1.0f, // Volume
            randomPitch // Random pitch
        )

        // Set speaking state with accurate timer based on sound duration
        isSpeaking = true
        currentlyPlayingSound = sound
        currentSoundType = currentState
        speakingTimer = soundDurations[sound] ?: 40  // Fallback to 2 seconds if unknown

        // Check if this is a short hurt sound
        if (currentState is IllagerState.Hurt && speakingTimer < 20) {
            isShortHurtSound = true
        }

        //LOGGER.info("PLAYING SOUND WITH PITCH: $randomPitch, WILL PLAY FOR ${speakingTimer} TICKS")
    }


    private fun <T> Array<T>.random(random: java.util.Random): T = this[random.nextInt(this.size)]
}
