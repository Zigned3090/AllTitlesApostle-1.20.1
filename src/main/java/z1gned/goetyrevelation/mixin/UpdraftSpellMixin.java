package z1gned.goetyrevelation.mixin;

import com.Polarice3.Goety.common.enchantments.ModEnchantments;
import com.Polarice3.Goety.common.entities.ModEntityType;
import com.Polarice3.Goety.common.entities.util.FireBlastTrap;
import com.Polarice3.Goety.common.items.ModItems;
import com.Polarice3.Goety.common.magic.Spell;
import com.Polarice3.Goety.common.magic.SpellStat;
import com.Polarice3.Goety.common.magic.spells.wind.UpdraftSpell;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.utils.WandUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import z1gned.goetyrevelation.util.ATAHelper;

@Mixin(UpdraftSpell.class)
public abstract class UpdraftSpellMixin extends Spell {

    @Inject(at = @At("HEAD"), method = "SpellResult", cancellable = true, remap = false)
    private void createFireBlastTrap(ServerLevel worldIn, LivingEntity caster, ItemStack staff, SpellStat spellStat, CallbackInfo ci) {
        if (ATAHelper.hasHalo(caster) && staff.is(ModItems.NETHER_STAFF.get())) {
            ci.cancel();
            fire(worldIn, caster, 48);
            worldIn.playSound(null, caster.getX(), caster.getY(), caster.getZ(), ModSounds.APOSTLE_PREPARE_SPELL.get(), this.getSoundSource(), 1.0F, 1.0F);
        }
    }

    @Unique
    public void fire(ServerLevel worldIn, LivingEntity entityLiving, int range) {
        double radius = 2.0;
        if (WandUtil.enchantedFocus(entityLiving)) {
            range += WandUtil.getLevels(ModEnchantments.RANGE.get(), entityLiving);
            radius += WandUtil.getLevels(ModEnchantments.RADIUS.get(), entityLiving);
        }

        HitResult rayTraceResult = this.rayTrace(worldIn, entityLiving, range, radius);
        LivingEntity target = this.getTarget(entityLiving, range);
        if (target != null) {
            FireBlastTrap fireBlastTrap = new FireBlastTrap(ModEntityType.FIRE_BLAST_TRAP.get(), worldIn);
            fireBlastTrap.setOwner(entityLiving);
            fireBlastTrap.setPos(target.position());
            fireBlastTrap.setAreaOfEffect(7.5F);
            worldIn.addFreshEntity(fireBlastTrap);
        } else if (rayTraceResult instanceof BlockHitResult) {
            BlockPos blockPos = ((BlockHitResult) rayTraceResult).getBlockPos();
            FireBlastTrap fireBlastTrap = new FireBlastTrap(ModEntityType.FIRE_BLAST_TRAP.get(), worldIn);
            fireBlastTrap.setOwner(entityLiving);
            fireBlastTrap.setAreaOfEffect(7.5F);
            fireBlastTrap.setPos((float)blockPos.getX() + 0.5F, (float)blockPos.getY() + 1.0F, (float)blockPos.getZ() + 0.5F);
            worldIn.addFreshEntity(fireBlastTrap);
        }

    }

}
