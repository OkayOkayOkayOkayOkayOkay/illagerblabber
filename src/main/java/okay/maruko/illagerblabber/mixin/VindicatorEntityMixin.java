package okay.maruko.illagerblabber.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.VindicatorEntity;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import okay.maruko.illagerblabber.IllagerSounds;
import okay.maruko.illagerblabber.voice.IllagerType;
import okay.maruko.illagerblabber.voice.IllagerVoiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VindicatorEntity.class)
public abstract class VindicatorEntityMixin extends IllagerEntity {

    @Unique
    private static final Logger LOGGER = LoggerFactory.getLogger("illagerblabber");

    // Constructor is required for extending IllagerEntity
    protected VindicatorEntityMixin(EntityType<? extends IllagerEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "mobTick", at = @At("TAIL"))
    private void onMobTick(CallbackInfo ci) {
        VindicatorEntity vindicator = (VindicatorEntity)(Object)this;
        IllagerVoiceRegistry.updateIllager(vindicator, IllagerType.VINDICATOR);
    }

    @Inject(method = "getHurtSound", at = @At("HEAD"), cancellable = true)
    private void onHurt(DamageSource source, CallbackInfoReturnable<SoundEvent> cir) {
        VindicatorEntity vindicator = (VindicatorEntity)(Object)this;
        IllagerVoiceRegistry.setHurtState(vindicator);

        // Cancel vanilla sound
        cir.setReturnValue(null);
    }

    @Inject(method = "getCelebratingSound", at = @At("HEAD"), cancellable = true)
    private void onGetCelebratingSound(CallbackInfoReturnable<SoundEvent> cir) {
        //LOGGER.info("VINDICATOR RAID VICTORY DETECTED!");
        VindicatorEntity vindicator = (VindicatorEntity)(Object)this;
        IllagerVoiceRegistry.setVictoryState(vindicator);

        // Return our silent sound instead of null
        cir.setReturnValue(IllagerSounds.INSTANCE.getSILENCE());
    }

    // Cancel the vanilla ambient sound
    @Inject(method = "getAmbientSound", at = @At("HEAD"), cancellable = true)
    private void onGetAmbientSound(CallbackInfoReturnable<SoundEvent> cir) {
        // Return null to prevent vanilla sound
        cir.setReturnValue(null);
    }
}
