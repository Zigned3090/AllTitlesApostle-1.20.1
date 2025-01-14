package z1gned.goetyrevelation.mixin;

import com.Polarice3.Goety.common.effects.GoetyEffects;
import com.Polarice3.Goety.common.entities.ModEntityType;
import com.Polarice3.Goety.common.entities.hostile.servants.Inferno;
import com.Polarice3.Goety.common.magic.SpellStat;
import com.Polarice3.Goety.common.magic.SummonSpell;
import com.Polarice3.Goety.common.magic.spells.nether.BlazeSpell;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.utils.BlockFinder;
import com.Polarice3.Goety.utils.EffectsUtil;
import com.Polarice3.Goety.utils.MobUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import z1gned.goetyrevelation.util.ATAHelper;

@Mixin(BlazeSpell.class)
public abstract class BlazeSpellMixin extends SummonSpell {

    @Inject(at = @At("HEAD"), method = "SpellResult", cancellable = true, remap = false)
    private void createInferno(ServerLevel worldIn, LivingEntity caster, ItemStack staff, SpellStat spellStat, CallbackInfo ci) {
        if (ATAHelper.hasHalo(caster) && this.rightStaff(staff)) {
            ci.cancel();
            this.commonResult(worldIn, caster);
            Player player = (Player) caster;
            int i = 1;
            if (this.rightStaff(staff) && !player.isShiftKeyDown()) {
                i = 2 + caster.level().random.nextInt(4);
            }

            for(int i1 = 0; i1 < i; ++i1) {
                Inferno blazeServant = new Inferno(ModEntityType.INFERNO.get(), worldIn);
                BlockPos blockPos = BlockFinder.SummonRadius(caster.blockPosition(), blazeServant, worldIn);
                if (caster.isUnderWater()) {
                    blockPos = BlockFinder.SummonWaterRadius(caster, worldIn);
                }

                if (player.isShiftKeyDown()) {
                    blazeServant.setUpgraded(true);
                }

                blazeServant.setTrueOwner(caster);
                blazeServant.moveTo(blockPos, 0.0F, 0.0F);
                MobUtil.moveDownToGround(blazeServant);
                blazeServant.setPersistenceRequired();
                blazeServant.finalizeSpawn(worldIn, caster.level().getCurrentDifficultyAt(caster.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
                if (spellStat.potency > 0) {
                    int boost = Mth.clamp(spellStat.potency - 1, 0, 10);
                    blazeServant.addEffect(new MobEffectInstance(GoetyEffects.BUFF.get(), EffectsUtil.infiniteEffect(), boost, false, false));
                    blazeServant.setFireBallDamage(blazeServant.getFireBallDamage() + (float)spellStat.potency);
                }

                this.SummonSap(caster, blazeServant);
                this.setTarget(caster, blazeServant);
                worldIn.addFreshEntity(blazeServant);
                this.summonAdvancement(caster, caster);
            }

            this.SummonDown(caster);
            worldIn.playSound(null, caster.getX(), caster.getY(), caster.getZ(), ModSounds.SUMMON_SPELL_FIERY.get(), this.getSoundSource(), 1.0F, 1.0F);
        }
    }

}
