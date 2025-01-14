package z1gned.goetyrevelation.mixin;

import com.Polarice3.Goety.common.entities.projectiles.HellCloud;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import z1gned.goetyrevelation.config.ModConfig;

@Mixin(HellCloud.class)
public class HellCloudMixin {
    @Unique
    LivingEntity allTitlesApostle_1_20_1$living;

    @Inject(at = @At("HEAD"), method = "hurtEntities", remap = false)
    private void getLivingEntity(LivingEntity livingEntity, CallbackInfo ci) {
        if (livingEntity != null) {
            this.allTitlesApostle_1_20_1$living = livingEntity;
        }
    }

    @ModifyVariable(at = @At(value = "STORE"), index = 2, method = "hurtEntities", remap = false)
    private float baseAmount(float value) {
        return (float) (allTitlesApostle_1_20_1$living.getMaxHealth() * ModConfig.HELLCLOUD_DAMAGE_AMOUNT.get() + 2.5F);
    }

}
