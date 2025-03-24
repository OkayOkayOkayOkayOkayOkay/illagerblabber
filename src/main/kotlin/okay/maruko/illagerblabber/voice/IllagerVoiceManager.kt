package okay.maruko.illagerblabber.voice

import net.minecraft.entity.LivingEntity
import net.minecraft.entity.mob.EvokerEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent
import net.minecraft.util.math.random.Random
import okay.maruko.illagerblabber.IllagerSounds
import org.slf4j.LoggerFactory

class IllagerVoiceManager(private val illager: EvokerEntity) {

    companion object {
        private val LOGGER = LoggerFactory.getLogger("illagerblabber")

        // Sound category arrays
        private val AMBIENT_NOISE_SOUNDS = arrayOf(
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

        private val AMBIENT_TALK_SOUNDS = arrayOf(
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

        private val BATTLE_SOUNDS = arrayOf(
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

        private val HURT_SOUNDS = arrayOf(
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

        private val SPOTTED_SOUNDS = arrayOf(
            IllagerSounds.EVOKER_SPOTTED_01,
            IllagerSounds.EVOKER_SPOTTED_02,
            IllagerSounds.EVOKER_SPOTTED_03,
            IllagerSounds.EVOKER_SPOTTED_04,
            IllagerSounds.EVOKER_SPOTTED_05,
            IllagerSounds.EVOKER_SPOTTED_06
        )

        private val VICTORY_SOUNDS = arrayOf(
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
        LOGGER.info("VOICE MANAGER CREATED FOR EVOKER AT ${illager.x}, ${illager.y}, ${illager.z}")
    }

    private fun adjustCooldownBasedOnCrowding(entity: LivingEntity, state: IllagerState, baseCooldown: Int): Int {
        // Skip adjustment for hurt states
        if (state is IllagerState.Hurt) {
            return baseCooldown
        }

        // Get all evokers in a 15 block radius
        val world = entity.world
        val nearbyEvokers = world.getEntitiesByClass(
            EvokerEntity::class.java,
            entity.boundingBox.expand(15.0)
        ) { true }

        val count = nearbyEvokers.size

        // If only one evoker or somehow zero, use base cooldown
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
        LOGGER.info("Evoker crowd adjustment: $count nearby evokers, base cooldown $baseCooldown → $adjustedCooldown")

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

    // Determine if the current state should interrupt what's playing
    private fun shouldInterruptCurrentSound(state: IllagerState): Boolean {
        if (!isSpeaking) return false // Nothing to interrupt

        // Special handling for hurt sounds
        if (state is IllagerState.Hurt) {
            // If another hurt sound is already playing, only allow interruption
            // if the current sound is NOT a short hurt sound
            if (currentSoundType is IllagerState.Hurt) {
                // Only let very short hurt sounds (< 1 sec) interrupt other hurt sounds
                val soundToPlay = chooseHurtSound()
                val duration = soundDurations[soundToPlay] ?: 20

                // If this is a short hurt sound (< 1 sec), allow it to play
                return duration < 20 // 20 ticks = 1 second
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

    fun setState(state: IllagerState) {
        // Don't log if state hasn't changed
        // if (state::class.java != currentState::class.java) {
            //Logger.info("STATE CHANGED TO: $state (previous was $currentState)")
        // }

        // Update state
        currentState = state

        // Note: We handle interruption in the update method now
        // This is cleaner as we check if we should play immediately first
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

        // Cooldown is now set AFTER the sound finishes playing, not here
    }

    // Helper methods to select random sounds from each category
    private fun choosePassiveSound(): SoundEvent {
        // 40% chance for ambient noise, 60% for ambient talk (since talks are more interesting)
        return if (random.nextInt(100) < 50) {
            // Choose random ambient noise from all 14 options
            AMBIENT_NOISE_SOUNDS.random(random)
        } else {
            // Choose random ambient talk from all 33 options
            AMBIENT_TALK_SOUNDS.random(random)
        }
    }

    private fun chooseSpottedSound(): SoundEvent {
        // Choose from all 6 spotted sounds
        return SPOTTED_SOUNDS.random(random)
    }

    private fun chooseCombatSound(): SoundEvent {
        // 30% chance for ambient noise, 70% for battle sound during combat
        return if (random.nextInt(100) < 30) {
            // Choose random ambient noise from all 14 options
            AMBIENT_NOISE_SOUNDS.random(random)
        } else {
            // Choose from all 14 battle sounds
            BATTLE_SOUNDS.random(random)
        }
    }

    private fun chooseHurtSound(): SoundEvent {
        // Choose from all 19 hurt sounds
        return HURT_SOUNDS.random(random)
    }

    private fun chooseVictorySound(): SoundEvent {
        // Choose from all 14 victory sounds
        return VICTORY_SOUNDS.random(random)
    }

    private fun playSound(sound: SoundEvent) {
        //LOGGER.info("PLAYING SOUND: $sound at ${illager.x}, ${illager.y}, ${illager.z}")

        // Generate a random pitch variation - favoring lower pitches for more menacing sound
        val randomPitch = when (currentState) {
            is IllagerState.Hurt -> 0.9f + random.nextFloat() * 0.25f  // Range: 0.75-1.0
            else -> 0.9f + random.nextFloat() * 0.1f  // Range: 0.8-1.0
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

    // Helper extension to pick a random element from an array
    private fun <T> Array<T>.random(random: Random): T = this[random.nextInt(this.size)]
}
