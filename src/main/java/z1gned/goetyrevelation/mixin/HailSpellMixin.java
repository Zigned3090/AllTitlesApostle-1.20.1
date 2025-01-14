package z1gned.goetyrevelation.mixin;

import com.Polarice3.Goety.common.enchantments.ModEnchantments;
import com.Polarice3.Goety.common.entities.projectiles.HellCloud;
import com.Polarice3.Goety.common.items.ModItems;
import com.Polarice3.Goety.common.magic.Spell;
import com.Polarice3.Goety.common.magic.SpellStat;
import com.Polarice3.Goety.common.magic.spells.frost.HailSpell;
import com.Polarice3.Goety.utils.WandUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import z1gned.goetyrevelation.util.ATAHelper;

@Mixin(HailSpell.class)
public abstract class HailSpellMixin extends Spell {
    @Inject(at = @At("HEAD"), method = "SpellResult", cancellable = true, remap = false)
    private void createHellCloud(ServerLevel worldIn, LivingEntity caster, ItemStack staff, SpellStat spellStat, CallbackInfo ci) {
        if (ATAHelper.hasHalo(caster) && staff.is(ModItems.NETHER_STAFF.get())) {
            ci.cancel();
            int range = 16;
            int duration = 100;
            double radius = 2.0;
            float damage = 0.0F;
            if (WandUtil.enchantedFocus(caster)) {
                range += WandUtil.getLevels(ModEnchantments.RANGE.get(), caster);
                duration *= WandUtil.getLevels(ModEnchantments.DURATION.get(), caster) + 1;
                damage += (float)WandUtil.getLevels(ModEnchantments.POTENCY.get(), caster);
                radius += WandUtil.getLevels(ModEnchantments.RADIUS.get(), caster);
            }

            HitResult rayTraceResult = this.rayTrace(worldIn, caster, range, radius);
            LivingEntity target = this.getTarget(caster, range);
            if (target != null) {
                if (target instanceof LivingEntity) {
                    HellCloud hellCloud = new HellCloud(worldIn, caster, target);
                    hellCloud.setExtraDamage(damage);
                    hellCloud.setRadius((float)radius * 4);
                    hellCloud.setLifeSpan(duration * 2);
                    hellCloud.setStaff(staff.is(ModItems.NETHER_STAFF.get()));
                    worldIn.addFreshEntity(hellCloud);
                }

                worldIn.playSound(null, caster.getX(), caster.getY(), caster.getZ(), SoundEvents.PLAYER_HURT_FREEZE, this.getSoundSource(), 1.0F, 1.0F);
            } else if (rayTraceResult instanceof BlockHitResult) {
                BlockPos blockPos = ((BlockHitResult)rayTraceResult).getBlockPos();
                HellCloud hellCloud = new HellCloud(worldIn, caster, null);
                hellCloud.setExtraDamage(damage);
                hellCloud.setRadius((float) radius * 4);
                hellCloud.setLifeSpan(duration * 2);
                hellCloud.setStaff(staff.is(ModItems.NETHER_STAFF.get()));
                hellCloud.setPos((float) blockPos.getX() + 0.5F, blockPos.getY() + 4, (float)blockPos.getZ() + 0.5F);
                worldIn.addFreshEntity(hellCloud);
            }
        }
    }
}
