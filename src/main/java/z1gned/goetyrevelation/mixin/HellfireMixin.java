package z1gned.goetyrevelation.mixin;

import com.Polarice3.Goety.common.entities.projectiles.Hellfire;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import z1gned.goetyrevelation.config.ModConfig;

@Mixin(Hellfire.class)
public class HellfireMixin {
    @Unique
    LivingEntity allTitlesApostle_1_20_1$target;

    @Inject(at = @At("HEAD"), method = "dealDamageTo", remap = false)
    private void getTarget(LivingEntity target, CallbackInfo ci) {
        this.allTitlesApostle_1_20_1$target = target;
    }

    @ModifyVariable(at = @At("STORE"), method = "dealDamageTo", remap = false)
    private float damageAmount(float value) {
        return (float) (allTitlesApostle_1_20_1$target.getMaxHealth() * ModConfig.HELLFIRE_DAMAGE_AMOUNT.get());
    }
}
