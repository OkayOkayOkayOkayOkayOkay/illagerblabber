package okay.maruko.illagerblabber.mixin;

import net.minecraft.entity.mob.EvokerEntity;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.entity.mob.VindicatorEntity;
import net.minecraft.entity.raid.RaiderEntity;
import okay.maruko.illagerblabber.voice.IllagerType;
import okay.maruko.illagerblabber.voice.IllagerVoiceRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RaiderEntity.class)
public class RaiderEntityMixin {
    @Inject(method = "tickMovement", at = @At("TAIL"))
    private void onTickMovement(CallbackInfo ci) {

        if (!((RaiderEntity) (Object) this).getWorld().isClient) {
            if ((Object) this instanceof PillagerEntity pillager) {
                IllagerVoiceRegistry.INSTANCE.updateIllager(pillager, IllagerType.PILLAGER);
            }
            else if ((Object) this instanceof EvokerEntity evoker) {
                IllagerVoiceRegistry.INSTANCE.updateIllager(evoker, IllagerType.EVOKER);
            }
            else if ((Object) this instanceof VindicatorEntity vindicator) {
                IllagerVoiceRegistry.INSTANCE.updateIllager(vindicator, IllagerType.VINDICATOR);
            }
        }
    }
}


