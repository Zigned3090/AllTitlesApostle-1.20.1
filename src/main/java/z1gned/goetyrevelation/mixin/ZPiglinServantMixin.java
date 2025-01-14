package z1gned.goetyrevelation.mixin;

import com.Polarice3.Goety.common.entities.ally.Summoned;
import com.Polarice3.Goety.common.entities.ally.undead.zombie.ZombieServant;
import com.Polarice3.Goety.common.entities.neutral.ZPiglinServant;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import z1gned.goetyrevelation.goal.MoveToApollyonGoal;

@Mixin(ZPiglinServant.class)
public abstract class ZPiglinServantMixin extends ZombieServant {

    public ZPiglinServantMixin(EntityType<? extends Summoned> type, Level worldIn) {
        super(type, worldIn);
    }

    @Inject(at = @At("HEAD"), method = "registerGoals")
    private void addGoal(CallbackInfo ci) {
        this.goalSelector.addGoal(0, new MoveToApollyonGoal<>((ZPiglinServant) (Object) this, this.getAttributeValue(Attributes.MOVEMENT_SPEED), 64, 0));
    }

}
