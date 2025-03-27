// LivingEntityMixin.java
package okay.maruko.illagerblabber.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.EvokerEntity;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.entity.mob.VindicatorEntity;
import okay.maruko.illagerblabber.voice.IllagerType;
import okay.maruko.illagerblabber.voice.IllagerVoiceRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity)(Object)this;

        if (entity instanceof PillagerEntity pillager) {
            IllagerVoiceRegistry.updateIllager(pillager, IllagerType.PILLAGER);
        }
    }
}
