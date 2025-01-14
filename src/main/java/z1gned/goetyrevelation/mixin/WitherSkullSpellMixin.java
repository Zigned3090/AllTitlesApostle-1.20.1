package z1gned.goetyrevelation.mixin;

import com.Polarice3.Goety.common.enchantments.ModEnchantments;
import com.Polarice3.Goety.common.entities.projectiles.NetherMeteor;
import com.Polarice3.Goety.common.magic.Spell;
import com.Polarice3.Goety.common.magic.SpellStat;
import com.Polarice3.Goety.common.magic.spells.nether.WitherSkullSpell;
import com.Polarice3.Goety.utils.WandUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import z1gned.goetyrevelation.util.ATAHelper;

@Mixin(WitherSkullSpell.class)
public abstract class WitherSkullSpellMixin extends Spell {
    @Unique
    Player allTitlesApostle_1_20_1$user;

    @Inject(at = @At("RETURN"), method = "defaultSpellCooldown", cancellable = true, remap = false)
    private void getSpellCooldown(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(ATAHelper.halfValueCondition(ATAHelper.hasHalo(this.allTitlesApostle_1_20_1$user), cir.getReturnValueI()));
    }

    @Inject(at = @At("RETURN"), method = "defaultCastDuration", cancellable = true, remap = false)
    private void getUseTime(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(ATAHelper.halfValueCondition(ATAHelper.hasHalo(this.allTitlesApostle_1_20_1$user), cir.getReturnValueI()));
    }

    @Inject(at = @At("HEAD"), method = "SpellResult", cancellable = true, remap = false)
    private void createStar(ServerLevel worldIn, LivingEntity caster, ItemStack staff, SpellStat spellStat, CallbackInfo ci) {
        this.allTitlesApostle_1_20_1$user = (Player) caster;
        if (this.rightStaff(staff) && ATAHelper.hasHalo(this.allTitlesApostle_1_20_1$user)) {
            ci.cancel();
            Vec3 vector3d = caster.getViewVector(1.0F);
            float extraBlast = (float) WandUtil.getLevels(ModEnchantments.RADIUS.get(), caster) / 2.5F;
            NetherMeteor netherStar = new NetherMeteor(caster.level(), caster.getX() + vector3d.x / 2.0, caster.getEyeY() - 0.2, caster.getZ() + vector3d.z / 2.0, vector3d.x, vector3d.y, vector3d.z);
            netherStar.setOwner(caster);
            if (this.isShifting(caster)) {
                netherStar.setDangerous(true);
            }

            netherStar.setExtraDamage((float)WandUtil.getLevels(ModEnchantments.POTENCY.get(), caster));
            netherStar.setFiery(WandUtil.getLevels(ModEnchantments.BURNING.get(), caster));
            netherStar.setExplosionPower(netherStar.getExplosionPower() + extraBlast);
            worldIn.playSound(null, caster.getX(), caster.getY(), caster.getZ(), SoundEvents.BLAZE_SHOOT, this.getSoundSource(), 1.0F, 1.0F);
            worldIn.addFreshEntity(netherStar);
        }
    }

}
