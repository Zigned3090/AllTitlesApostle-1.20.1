package z1gned.goetyrevelation.mixin;

import com.Polarice3.Goety.common.entities.ModEntityType;
import com.Polarice3.Goety.common.entities.projectiles.DeathArrow;
import com.Polarice3.Goety.init.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import z1gned.goetyrevelation.util.ATAHelper;

@Mixin(BowItem.class)
public class BowItemMixin {

    @Unique LivingEntity user;
    @Unique float f;

    @Inject(at = @At("HEAD"), method = "releaseUsing")
    private void getVar(ItemStack p_40667_, Level p_40668_, LivingEntity p_40669_, int p_40670_, CallbackInfo ci) {
        this.user = p_40669_;
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"), method = "releaseUsing")
    private boolean deathShoot(Level instance, Entity entity) {
        if (this.user instanceof Player player) {
            if (ATAHelper.hasHalo(player) && entity instanceof AbstractArrow abstractArrow) {
                DeathArrow deathArrow = new DeathArrow(instance, player);
                deathArrow.setBaseDamage(abstractArrow.getBaseDamage() * 1.4F);
                deathArrow.setCritArrow(abstractArrow.isCritArrow());
                deathArrow.setKnockback(abstractArrow.getKnockback());
                deathArrow.setSecondsOnFire(1000);
                deathArrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, f * 3.0F * 1.4F, 1.0F);
                deathArrow.pickup = abstractArrow.pickup;

                entity = deathArrow;
            }
        }

        return instance.addFreshEntity(entity);
    }

    @ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"), index = 4, method = "releaseUsing")
    private SoundEvent sound(SoundEvent p_46547_) {
        if (ATAHelper.hasHalo(this.user)) {
            p_46547_ = ModSounds.APOSTLE_SHOOT.get();
        }

        return p_46547_;
    }

    @ModifyVariable(at = @At("STORE"), index = 9, method = "releaseUsing")
    private float getPower(float value) {
        this.f = value;
        return value;
    }

    /*@ModifyVariable(at = @At("STORE"), index = 6, method = "releaseUsing")
    private boolean setShootCondition(boolean value) {

        if (ATAHelper.hasHalo(this.user)) value = true;

        return value;
    }*/

}
