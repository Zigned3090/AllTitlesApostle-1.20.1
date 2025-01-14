package z1gned.goetyrevelation.mixin;

import com.Polarice3.Goety.common.entities.projectiles.AbstractSpellCloud;
import com.Polarice3.Goety.common.entities.projectiles.HellCloud;
import com.Polarice3.Goety.utils.MobUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractSpellCloud.class)
public class AbstractSpellCloudMixin {

    @Inject(at = @At("RETURN"), method = "tick")
    private void tracker(CallbackInfo ci) {
        AbstractSpellCloud cloud = (AbstractSpellCloud) (Object) this;
        if (cloud instanceof HellCloud hellCloud) {
            if (hellCloud.getOwner() instanceof Player) {
                if (!hellCloud.level().isClientSide && hellCloud.isStaff()) {
                    if (hellCloud.getTarget() == null) {

                        for (LivingEntity livingEntity : hellCloud.level().getEntitiesOfClass(LivingEntity.class, hellCloud.getBoundingBox().inflate(16.0))) {
                            if (MobUtil.ownedPredicate(hellCloud).test(livingEntity)) {
                                cloud.setTarget(livingEntity);
                            }
                        }
                    }

                    float speed = 0.175F;
                    if (hellCloud.getTarget() != null && hellCloud.getTarget().isAlive()) {
                        hellCloud.setDeltaMovement(Vec3.ZERO);
                        double d0 = hellCloud.getTarget().getX() - hellCloud.getX();
                        double d1 = hellCloud.getTarget().getY() + 4.0 - hellCloud.getY();
                        double d2 = hellCloud.getTarget().getZ() - hellCloud.getZ();
                        double d = Math.sqrt(d0 * d0 + d2 * d2);
                        double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                        if (d > 0.5) {
                            hellCloud.setDeltaMovement(hellCloud.getDeltaMovement().add(d0 / d3, d1 / d3, d2 / d3).scale((double)speed));
                        }
                    }

                    hellCloud.move(MoverType.SELF, hellCloud.getDeltaMovement());
                }
            }
        }
    }

}
