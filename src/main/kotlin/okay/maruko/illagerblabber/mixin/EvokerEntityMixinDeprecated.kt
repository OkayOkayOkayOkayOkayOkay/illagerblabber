package okay.maruko.illagerblabber.mixin

import net.minecraft.entity.EntityType
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.mob.EvokerEntity
import net.minecraft.entity.mob.SpellcastingIllagerEntity
import net.minecraft.world.World
import okay.maruko.illagerblabber.voice.IllagerState
import okay.maruko.illagerblabber.voice.IllagerVoiceManager
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Unique
import org.spongepowered.asm.mixin.injection.At
import org.spongepowered.asm.mixin.injection.Inject
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo

@Mixin(EvokerEntity::class)
abstract class EvokerEntityMixinDeprecated(entityType: EntityType<out SpellcastingIllagerEntity>, world: World) : SpellcastingIllagerEntity(entityType, world) {

    // Use @Unique to avoid conflicts
    @Unique
    private var voiceManager: IllagerVoiceManager? = null

    @Unique
    private var hadTargetLastTick = false

    @Unique
    private fun getVoiceManager(): IllagerVoiceManager {
        if (voiceManager == null) {
            // This cast is safe within this context
            val thisAsEvoker = this as EvokerEntity
            voiceManager = IllagerVoiceManager(thisAsEvoker)
        }
        return voiceManager!!
    }

    // FIXED: Using "baseTick" instead of "tick"
    @Inject(method = ["mobTick()V"], at = [At("TAIL")])
    private fun onMobTick(ci: CallbackInfo) {
        getVoiceManager().update()
        updateState()
    }

    @Inject(method = ["damage"], at = [At("HEAD")])
    private fun onDamage(source: DamageSource, amount: Float, ci: CallbackInfo) {
        getVoiceManager().setState(IllagerState.Hurt)
    }

    @Unique
    private fun updateState() {
        val target = this.target
        if (target != null && target.isAlive) {
            if (!hadTargetLastTick) {
                getVoiceManager().setState(IllagerState.Spotted)
                hadTargetLastTick = true
            } else {
                getVoiceManager().setState(IllagerState.Combat)
            }
        } else {
            if (hadTargetLastTick) {
                getVoiceManager().setState(IllagerState.Victory)
                hadTargetLastTick = false
            } else {
                getVoiceManager().setState(IllagerState.Passive)
            }
        }
    }
}
