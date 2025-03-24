package okay.maruko.illagerblabber.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.EvokerEntity;
import net.minecraft.entity.mob.SpellcastingIllagerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import okay.maruko.illagerblabber.IllagerSounds;
import okay.maruko.illagerblabber.voice.IllagerState;
import okay.maruko.illagerblabber.voice.IllagerVoiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EvokerEntity.class)
public abstract class EvokerEntityMixin extends SpellcastingIllagerEntity {

    @Unique
    private static final Logger LOGGER = LoggerFactory.getLogger("illagerblabber");

    @Unique
    private IllagerVoiceManager voiceManager;

    @Unique
    private boolean hadTargetLastTick = false;

    @Unique
    private int victoryTimer = 0;

    @Unique
    private int combatDebounceTimer = 0;

    // Constructor is required for extending SpellcastingIllagerEntity
    protected EvokerEntityMixin(EntityType<? extends SpellcastingIllagerEntity> entityType, World world) {
        super(entityType, world);
    }

    @Unique
    private IllagerVoiceManager getVoiceManager() {
        if (voiceManager == null) {
            LOGGER.info("CREATING NEW VOICE MANAGER FOR EVOKER!");
            voiceManager = new IllagerVoiceManager((EvokerEntity)(Object)this);
        }
        return voiceManager;
    }

    @Inject(method = "mobTick", at = @At("TAIL"))
    private void onMobTick(CallbackInfo ci) {
        getVoiceManager().update();
        updateState();
    }

    @Inject(method = "getCelebratingSound", at = @At("HEAD"), cancellable = true)
    private void onGetCelebratingSound(CallbackInfoReturnable<SoundEvent> cir) {
        LOGGER.info("EVOKER RAID VICTORY DETECTED!");
        getVoiceManager().setState(IllagerState.Victory.INSTANCE);

        // Return our silent sound instead of null
        cir.setReturnValue(IllagerSounds.INSTANCE.getSILENCE());
    }

    @Inject(method = "getHurtSound", at = @At("HEAD"), cancellable = true)
    private void onHurt(DamageSource source, CallbackInfoReturnable<SoundEvent> cir) {
        //Logger.info("EVOKER HURT DETECTED!");
        getVoiceManager().setState(IllagerState.Hurt.INSTANCE);

        // Cancel vanilla sound
        cir.setReturnValue(null);
    }

    // Cancel the vanilla ambient sound
    @Inject(method = "getAmbientSound", at = @At("HEAD"), cancellable = true)
    private void onGetAmbientSound(CallbackInfoReturnable<SoundEvent> cir) {
        // Return null to prevent vanilla sound
        cir.setReturnValue(null);
    }



    @Unique
    private void updateState() {
        // If we're celebrating a victory, keep in victory state for a while
        if (victoryTimer > 0) {
            victoryTimer--;
            return; // Stay in victory state
        }

        // Handle combat state stickiness
        if (combatDebounceTimer > 0) {
            combatDebounceTimer--;
        }

        boolean hasTarget = this.getTarget() != null && this.getTarget().isAlive();

        if (hasTarget) {
            // We have a valid target
            if (!hadTargetLastTick) {
                //Logger.info("CHANGING STATE: SPOTTED");
                getVoiceManager().setState(IllagerState.Spotted.INSTANCE);
                hadTargetLastTick = true;
            } else {
                //Logger.info("CHANGING STATE: COMBAT");
                getVoiceManager().setState(IllagerState.Combat.INSTANCE);
            }

            // Reset combat debounce timer whenever we have a valid target
            combatDebounceTimer = 60 + random.nextInt(41); // 3-5 seconds (60-100 ticks)
        } else {
            // We don't have a target right now
            if (hadTargetLastTick) {
                // Had a target last tick but don't now
                if (combatDebounceTimer <= 0) {
                    // Debounce expired, trigger victory
                    //Logger.info("CHANGING STATE: VICTORY");
                    getVoiceManager().setState(IllagerState.Victory.INSTANCE);
                    victoryTimer = 100; // Stay in victory state for 5 seconds
                    hadTargetLastTick = false;
                } else {
                    // Still in debounce period, stay in combat
                    //Logger.info("STAYING IN COMBAT (DEBOUNCE ACTIVE): " + combatDebounceTimer + " ticks remaining");
                    getVoiceManager().setState(IllagerState.Combat.INSTANCE);
                }
            } else {
                // Never had a target, just passive
                //Logger.info("CHANGING STATE: PASSIVE");
                getVoiceManager().setState(IllagerState.Passive.INSTANCE);
            }
        }
    }
}
