package z1gned.goetyrevelation.mixin;

import com.Polarice3.Goety.client.render.model.ApostleModel;
import com.Polarice3.Goety.client.render.model.CultistModel;
import com.Polarice3.Goety.common.entities.boss.Apostle;
import com.Polarice3.Goety.utils.MobUtil;
import net.minecraft.client.model.geom.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import z1gned.goetyrevelation.util.ApollyonAbilityHelper;

@Mixin(ApostleModel.class)
public class ApostleModelMixin<T extends Apostle> extends CultistModel<T> {
    @Shadow public ModelPart halo1;

    @Shadow public ModelPart hat2;

    public ApostleModelMixin(ModelPart p_170677_) {
        super(p_170677_);
    }

    @Inject(at = @At("HEAD"), method = "setupAnim(Lcom/Polarice3/Goety/common/entities/boss/Apostle;FFFFF)V", cancellable = true, remap = false)
    private void hatVisible(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        ci.cancel();
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        this.nose.visible = !entity.isSecondPhase();
        this.hat.visible = false;
        this.halo1.zRot = entity.getSpin();
        this.hat2.visible = ((ApollyonAbilityHelper) entity).allTitlesApostle_1_20_1$isApollyon() || (!entity.isSecondPhase() || !MobUtil.healthIsHalved(entity));
    }

}
