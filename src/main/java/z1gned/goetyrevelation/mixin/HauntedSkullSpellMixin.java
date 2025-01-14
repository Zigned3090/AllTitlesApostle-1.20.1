package z1gned.goetyrevelation.mixin;

import com.Polarice3.Goety.common.effects.GoetyEffects;
import com.Polarice3.Goety.common.enchantments.ModEnchantments;
import com.Polarice3.Goety.common.entities.ModEntityType;
import com.Polarice3.Goety.common.entities.ally.undead.HauntedSkull;
import com.Polarice3.Goety.common.entities.hostile.servants.Damned;
import com.Polarice3.Goety.common.items.ModItems;
import com.Polarice3.Goety.common.magic.SpellStat;
import com.Polarice3.Goety.common.magic.SummonSpell;
import com.Polarice3.Goety.common.magic.spells.necromancy.HauntedSkullSpell;
import com.Polarice3.Goety.utils.EffectsUtil;
import com.Polarice3.Goety.utils.MathHelper;
import com.Polarice3.Goety.utils.WandUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import z1gned.goetyrevelation.util.ATAHelper;

import java.util.Iterator;

@Mixin(HauntedSkullSpell.class)
public abstract class HauntedSkullSpellMixin extends SummonSpell {
    @Shadow public abstract void commonResult(ServerLevel worldIn, LivingEntity caster);

    @Unique
    ItemStack stack;

    @Inject(at = @At("HEAD"), method = "SpellResult", cancellable = true, remap = false)
    private void createDamned(ServerLevel worldIn, LivingEntity caster, ItemStack staff, SpellStat spellStat, CallbackInfo ci) {
        this.stack = staff;
        if (ATAHelper.hasHalo(caster) && staff.is(ModItems.NETHER_STAFF.get())) {
            ci.cancel();
            this.commonResult(worldIn, caster);
            int potency = spellStat.getPotency();
            int duration = spellStat.getDuration();
            int burning = spellStat.getBurning();
            double radius = spellStat.getRadius();
            if (WandUtil.enchantedFocus(caster)) {
                potency += WandUtil.getLevels((Enchantment)ModEnchantments.POTENCY.get(), caster);
                duration += WandUtil.getLevels((Enchantment)ModEnchantments.DURATION.get(), caster) + 1;
                burning = WandUtil.getLevels((Enchantment)ModEnchantments.BURNING.get(), caster);
                radius = (double)WandUtil.getLevels((Enchantment)ModEnchantments.RADIUS.get(), caster);
            }

            if (!this.isShifting(caster)) {
                int i = 1;
                if (this.rightStaff(staff)) {
                    i = 3;
                }

                for(int i1 = 0; i1 < i; ++i1) {
                    BlockPos blockpos = caster.blockPosition().offset(-2 + caster.getRandom().nextInt(5), 1, -2 + caster.getRandom().nextInt(5));
                    Damned summonedentity = new Damned(ModEntityType.DAMNED.get(), worldIn);
                    summonedentity.setTrueOwner(caster);
                    summonedentity.moveTo(blockpos, 0.0F, 0.0F);
                    summonedentity.finalizeSpawn(worldIn, caster.level().getCurrentDifficultyAt(blockpos), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                    summonedentity.setLimitedLife(MathHelper.minutesToTicks(1) * duration);
                    if (potency > 0) {
                        int boost = Mth.clamp(potency - 1, 0, 10);
                        summonedentity.addEffect(new MobEffectInstance(GoetyEffects.BUFF.get(), EffectsUtil.infiniteEffect(), boost));
                    }

                    this.setTarget(caster, summonedentity);
                    worldIn.addFreshEntity(summonedentity);
                    this.summonAdvancement(caster, summonedentity);
                }

                worldIn.playSound(null, caster.getX(), caster.getY(), caster.getZ(), SoundEvents.EVOKER_CAST_SPELL, this.getSoundSource(), 1.0F, 1.0F);
            }

            /*if (!this.isShifting(caster)) {
                int i = 3 + worldIn.random.nextInt(6);

                for(int i1 = 0; i1 < i; ++i1) {
                    BlockPos blockpos = caster.blockPosition().offset(-2 + caster.getRandom().nextInt(5), 1, -2 + caster.getRandom().nextInt(5));
                    Damned summonedentity = new Damned(ModEntityType.DAMNED.get(), worldIn);
                    summonedentity.setTrueOwner(caster);
                    summonedentity.moveTo(blockpos, 0.0F, 0.0F);
                    summonedentity.finalizeSpawn(worldIn, caster.level().getCurrentDifficultyAt(blockpos), MobSpawnType.MOB_SUMMONED, null, null);
                    if (spellStat.potency > 0) {
                        int boost = Mth.clamp(spellStat.potency - 1, 0, 10);
                        summonedentity.addEffect(new MobEffectInstance(GoetyEffects.BUFF.get(), EffectsUtil.infiniteEffect(), boost));
                    }

                    this.setTarget(caster, summonedentity);
                    worldIn.addFreshEntity(summonedentity);
                    this.summonAdvancement(caster, summonedentity);
                }

                worldIn.playSound(null, caster.getX(), caster.getY(), caster.getZ(), SoundEvents.EVOKER_CAST_SPELL, this.getSoundSource(), 1.0F, 1.0F);
            }*/
        }
    }

    @Inject(at = @At("HEAD"), method = "commonResult", cancellable = true, remap = false)
    private void result(ServerLevel worldIn, LivingEntity caster, CallbackInfo ci) {
        if (ATAHelper.hasHalo(caster) && this.stack.is(ModItems.NETHER_STAFF.get())) {
            ci.cancel();
            if (this.isShifting(caster)) {

                for (Entity entity : worldIn.getAllEntities()) {
                    if (entity instanceof Damned) {
                        this.teleportServants(caster, entity);
                    }
                }

                for(int i = 0; i < caster.level().random.nextInt(35) + 10; ++i) {
                    worldIn.sendParticles(ParticleTypes.POOF, caster.getX(), caster.getEyeY(), caster.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
                }

                worldIn.playSound(null, caster.getX(), caster.getY(), caster.getZ(), SoundEvents.EVOKER_CAST_SPELL, this.getSoundSource(), 1.0F, 1.0F);
            }
        }
    }

}
