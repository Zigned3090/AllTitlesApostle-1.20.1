package z1gned.goetyrevelation.mixin;

import com.Polarice3.Goety.common.entities.boss.Apostle;
import com.Polarice3.Goety.common.entities.hostile.cultists.SpellCastingCultist;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import z1gned.goetyrevelation.util.ApollyonAbilityHelper;

@Mixin(SpellCastingCultist.UseSpellGoal.class)
public class UseSpellGoalMixin {
    @Unique
    SpellCastingCultist allTitlesApostle_1_20_1$living;

    @Redirect(at = @At(value = "INVOKE", target = "Lcom/Polarice3/Goety/common/entities/hostile/cultists/SpellCastingCultist;getTarget()Lnet/minecraft/world/entity/LivingEntity;"), method = "canUse")
    private LivingEntity getUser(SpellCastingCultist instance) {
        this.allTitlesApostle_1_20_1$living = instance;
        return instance.getTarget();
    }

    @Inject(at = @At("HEAD"), method = "canUse", cancellable = true)
    private void cannotUseOnSilent(CallbackInfoReturnable<Boolean> cir) {
        if (this.allTitlesApostle_1_20_1$living instanceof Apostle apostle && ((ApollyonAbilityHelper) apostle).allTitlesApostle_1_20_1$isSilent()) {
            cir.setReturnValue(false);
        }
    }

}
