package okay.maruko.illagerblabber.mixin;

import net.minecraft.entity.mob.PillagerEntity;
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
        // Check if on server-side
        if (!((RaiderEntity) (Object) this).getWorld().isClient) {
            if ((Object) this instanceof PillagerEntity) {
                PillagerEntity pillager = (PillagerEntity) (Object) this;
                IllagerVoiceRegistry.updateIllager(pillager, IllagerType.PILLAGER);
            }
        }
    }

}

