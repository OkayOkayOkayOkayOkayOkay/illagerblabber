package okay.maruko.illagerblabber.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.EvokerEntity;
import net.minecraft.entity.mob.SpellcastingIllagerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import okay.maruko.illagerblabber.IllagerSounds;
import okay.maruko.illagerblabber.voice.IllagerState;
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

@Mixin(EvokerEntity.class)
public abstract class EvokerEntityMixin extends SpellcastingIllagerEntity {

    @Unique
    private static final Logger LOGGER = LoggerFactory.getLogger("illagerblabber");

    // Constructor is required for extending SpellcastingIllagerEntity
    protected EvokerEntityMixin(EntityType<? extends SpellcastingIllagerEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "mobTick", at = @At("TAIL"))
    private void onMobTick(CallbackInfo ci) {
        EvokerEntity evoker = (EvokerEntity)(Object)this;
        IllagerVoiceRegistry.INSTANCE.updateIllager(evoker, IllagerType.EVOKER);
    }

    @Inject(method = "getCelebratingSound", at = @At("HEAD"), cancellable = true)
    private void onGetCelebratingSound(CallbackInfoReturnable<SoundEvent> cir) {
        LOGGER.info("EVOKER RAID VICTORY DETECTED!");
        EvokerEntity evoker = (EvokerEntity)(Object)this;
        IllagerVoiceRegistry.INSTANCE.setVictoryState(evoker);

        // Return our silent sound instead of null
        cir.setReturnValue(IllagerSounds.INSTANCE.getSILENCE());
    }

    @Inject(method = "getHurtSound", at = @At("HEAD"), cancellable = true)
    private void onHurt(DamageSource source, CallbackInfoReturnable<SoundEvent> cir) {
        EvokerEntity evoker = (EvokerEntity)(Object)this;
        IllagerVoiceRegistry.INSTANCE.setHurtState(evoker);

        // Cancel vanilla sound
        cir.setReturnValue(null);
    }

    // Cancel the vanilla ambient sound
    @Inject(method = "getAmbientSound", at = @At("HEAD"), cancellable = true)
    private void onGetAmbientSound(CallbackInfoReturnable<SoundEvent> cir) {
        // Return null to prevent vanilla sound
        cir.setReturnValue(null);
    }
}
