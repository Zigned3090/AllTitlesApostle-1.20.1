package z1gned.goetyrevelation.mixin;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import z1gned.goetyrevelation.util.ATAHelper;

@Mixin(TargetGoal.class)
public class TargetGoalMixin {

    @Shadow @Final protected Mob mob;

    @Inject(at = @At("HEAD"), method = "canContinueToUse", cancellable = true)
    private void canUseToPlayer(CallbackInfoReturnable<Boolean> cir) {
        if (this.mob.getMobType() == MobType.UNDEAD) {
            if (this.mob.getTarget() != null) {
                if (ATAHelper.hasHalo(this.mob.getTarget())) {
                    cir.setReturnValue(false);
                }
            }
        }
    }

}
