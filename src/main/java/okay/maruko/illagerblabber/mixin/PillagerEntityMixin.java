package okay.maruko.illagerblabber.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import okay.maruko.illagerblabber.IllagerSounds;
import okay.maruko.illagerblabber.voice.IllagerVoiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PillagerEntity.class)
public abstract class PillagerEntityMixin extends IllagerEntity {

    @Unique
    private static final Logger LOGGER = LoggerFactory.getLogger("illagerblabber");

    // Constructor is required for extending IllagerEntity
    protected PillagerEntityMixin(EntityType<? extends IllagerEntity> entityType, World world) {
        super(entityType, world);
    }



    @Inject(method = "getHurtSound", at = @At("HEAD"), cancellable = true)
    private void onHurt(DamageSource source, CallbackInfoReturnable<SoundEvent> cir) {
        PillagerEntity pillager = (PillagerEntity)(Object)this;
        IllagerVoiceRegistry.setHurtState(pillager);

        // Cancel vanilla sound
        cir.setReturnValue(null);
    }

    @Inject(method = "getCelebratingSound", at = @At("HEAD"), cancellable = true)
    private void onGetCelebratingSound(CallbackInfoReturnable<SoundEvent> cir) {
        LOGGER.info("PILLAGER RAID VICTORY DETECTED!");
        PillagerEntity pillager = (PillagerEntity)(Object)this;
        IllagerVoiceRegistry.setVictoryState(pillager);

        // Return our silent sound
        cir.setReturnValue(IllagerSounds.INSTANCE.getSILENCE());
    }

    // Cancel the vanilla ambient sound
    @Inject(method = "getAmbientSound", at = @At("HEAD"), cancellable = true)
    private void onGetAmbientSound(CallbackInfoReturnable<SoundEvent> cir) {
        // Return null to prevent vanilla sound
        cir.setReturnValue(null);
    }
}
