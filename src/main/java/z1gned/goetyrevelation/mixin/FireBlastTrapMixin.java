package z1gned.goetyrevelation.mixin;

import com.Polarice3.Goety.common.entities.util.FireBlastTrap;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FireBlastTrap.class)
public abstract class FireBlastTrapMixin extends Entity {

    @Shadow public LivingEntity owner;

    @Shadow public abstract float getAreaOfEffect();

    public FireBlastTrapMixin(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z", ordinal = 1), method = "tick")
    private boolean hurtByBlast(Entity instance, DamageSource p_19946_, float p_19947_) {
        if (this.owner instanceof Player) {
            p_19947_ = 7.5F * 4.75F + this.getAreaOfEffect() * 1.235F;
        }

        return instance.hurt(this.damageSources().indirectMagic(this, this.owner), p_19947_);
    }
}
