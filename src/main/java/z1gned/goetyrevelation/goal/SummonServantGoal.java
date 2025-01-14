package z1gned.goetyrevelation.goal;

import com.Polarice3.Goety.common.entities.ModEntityType;
import com.Polarice3.Goety.common.entities.ally.Summoned;
import com.Polarice3.Goety.common.entities.ally.spider.CaveSpiderServant;
import com.Polarice3.Goety.common.entities.ally.undead.WraithServant;
import com.Polarice3.Goety.common.entities.ally.undead.skeleton.SkeletonServant;
import com.Polarice3.Goety.common.entities.ally.undead.skeleton.StrayServant;
import com.Polarice3.Goety.common.entities.ally.undead.zombie.HuskServant;
import com.Polarice3.Goety.common.entities.boss.Apostle;
import com.Polarice3.Goety.common.entities.hostile.cultists.SpellCastingCultist;
import com.Polarice3.Goety.common.entities.hostile.servants.Inferno;
import com.Polarice3.Goety.common.entities.hostile.servants.Malghast;
import com.Polarice3.Goety.common.entities.neutral.Owned;
import com.Polarice3.Goety.common.entities.util.SummonCircle;
import com.Polarice3.Goety.init.ModSounds;
import com.Polarice3.Goety.utils.BlockFinder;
import com.Polarice3.Goety.utils.MathHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import z1gned.goetyrevelation.entitiy.PhantomServant;
import z1gned.goetyrevelation.util.ApollyonAbilityHelper;

import java.util.function.Predicate;

public class SummonServantGoal extends CastingGoal {
    Apostle apostle;

    private final Predicate<Owned> RANGED_MINIONS = (owned) -> (owned instanceof Inferno || owned instanceof Malghast) && owned.getTrueOwner() == apostle;

    public SummonServantGoal(SpellCastingCultist mob) {
        super(mob);
        this.apostle = (Apostle) mob;
     }

    public boolean canUse() {
        int title = ((ApollyonAbilityHelper) apostle).allTitleApostle$getTitleNumber();
        int i = apostle.level().getEntitiesOfClass(Owned.class, apostle.getBoundingBox().inflate(64.0), RANGED_MINIONS).size();
        if (title == 13) {
            return false;
        } else if (!super.canUse()) {
            return false;
        } else {
            int cool = apostle.spellStart();
            return apostle.getCoolDown() >= cool && apostle.getSpellCycle() == 3 && !apostle.isSettingUpSecond() && apostle.getInfernoCoolDown() <= 0 && (i < 6 || (i == 11 || i == 12));
        }
    }

    protected int getCastingTime() {
        return super.getCastingTime();
    }

    public void castSpell() {
        if (!apostle.level().isClientSide) {
            ServerLevel serverLevel = (ServerLevel) apostle.level();
            LivingEntity livingentity = apostle.getTarget();
            RandomSource r = apostle.getRandom();
            int i = ((ApollyonAbilityHelper) apostle).allTitleApostle$getTitleNumber();
            int s = i == 12 ? apostle.level().random.nextInt(12) : i;
            if (livingentity != null) {
                int p0;
                int p;
                if (r.nextBoolean()) {
                    if (apostle.isSecondPhase()) {
                        p0 = (12 + r.nextInt(12)) * (r.nextBoolean() ? -1 : 1);
                        p = (12 + r.nextInt(12)) * (r.nextBoolean() ? -1 : 1);
                        BlockPos.MutableBlockPos blockpos$mutable = apostle.blockPosition().mutable().move(p0, 0, p);
                        blockpos$mutable.setX(blockpos$mutable.getX() + r.nextInt(5) - r.nextInt(5));
                        blockpos$mutable.setY((int) BlockFinder.moveDownToGround(apostle));
                        blockpos$mutable.setZ(blockpos$mutable.getZ() + r.nextInt(5) - r.nextInt(5));
                        Malghast summonedentityx = new Malghast(ModEntityType.MALGHAST.get(), apostle.level());
                        if (serverLevel.noCollision(summonedentityx, summonedentityx.getBoundingBox().move(blockpos$mutable.above(2)).inflate(0.25))) {
                            summonedentityx.setPos((double)blockpos$mutable.getX(), (double)(blockpos$mutable.getY() + 2), (double)blockpos$mutable.getZ());
                        } else {
                            blockpos$mutable = apostle.blockPosition().mutable();
                            summonedentityx.setPos(blockpos$mutable.getX(), (double)(blockpos$mutable.getY() + 2), (double)blockpos$mutable.getZ());
                        }

                        summonedentityx.setTrueOwner(apostle);
                        summonedentityx.setLimitedLife(60 * (90 + apostle.level().random.nextInt(180)));
                        summonedentityx.finalizeSpawn(serverLevel, apostle.level().getCurrentDifficultyAt(blockpos$mutable.above(2)), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                        summonedentityx.setTarget(livingentity);
                        SummonCircle summonCirclex = new SummonCircle(apostle.level(), blockpos$mutable, summonedentityx, false, false, apostle);
                        apostle.level().addFreshEntity(summonCirclex);
                    } else {
                        BlockPos blockpos = apostle.blockPosition();
                        Summoned summonedentity = new Inferno(ModEntityType.INFERNO.get(), apostle.level());
                        if (s == 7) {
                            summonedentity = new HuskServant(ModEntityType.HUSK_SERVANT.get(), apostle.level());
                        } else if (s == 2) {
                            summonedentity = new CaveSpiderServant(ModEntityType.CAVE_SPIDER_SERVANT.get(), apostle.level());
                        } else if (s == 9) {
                            summonedentity = new WraithServant(ModEntityType.WRAITH_SERVANT.get(), apostle.level());
                        } else if (s == 3) {
                            summonedentity = new SkeletonServant(ModEntityType.SKELETON_SERVANT.get(), apostle.level());
                        } else if (s == 8) {
                            summonedentity = new StrayServant(ModEntityType.STRAY_SERVANT.get(), apostle.level());
                        }
                        summonedentity.moveTo(blockpos, 0.0F, 0.0F);
                        summonedentity.setTrueOwner(apostle);
                        summonedentity.setLimitedLife(60 * (90 + apostle.level().random.nextInt(180)));
                        summonedentity.setUpgraded(true);
                        summonedentity.finalizeSpawn(serverLevel, apostle.level().getCurrentDifficultyAt(blockpos), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
                        summonedentity.setTarget(livingentity);

                        PhantomServant phantomServant = new PhantomServant(z1gned.goetyrevelation.entitiy.ModEntityType.PHANTOM_SERVANT.get(), apostle.level());
                        phantomServant.setTrueOwner(apostle);
                        phantomServant.setLimitedLife(60 * (90 + apostle.level().random.nextInt(180)));
                        phantomServant.finalizeSpawn(serverLevel, apostle.level().getCurrentDifficultyAt(blockpos.above(2)), MobSpawnType.MOB_SUMMONED, null, null);
                        phantomServant.setTarget(livingentity);

                        SummonCircle summonCircle = new SummonCircle(apostle.level(), blockpos, summonedentity, false, true, apostle);
                        if (s == 4) {
                            summonCircle = new SummonCircle(apostle.level(), blockpos, phantomServant, false, true, apostle);
                        }
                        apostle.level().addFreshEntity(summonCircle);
                        apostle.setInfernoCoolDown(MathHelper.secondsToTicks(45));
                    }
                } else {
                    p0 = apostle.isInNether() ? 2 : 1;

                    for(p = 0; p < p0 + r.nextInt(1 + p0); ++p) {
                        int k = (12 + r.nextInt(12)) * (r.nextBoolean() ? -1 : 1);
                        int l = (12 + r.nextInt(12)) * (r.nextBoolean() ? -1 : 1);
                        BlockPos.MutableBlockPos blockpos$mutablex = apostle.blockPosition().mutable().move(k, 0, l);
                        blockpos$mutablex.setX(blockpos$mutablex.getX() + r.nextInt(5) - r.nextInt(5));
                        blockpos$mutablex.setY((int)BlockFinder.moveDownToGround(apostle));
                        blockpos$mutablex.setZ(blockpos$mutablex.getZ() + r.nextInt(5) - r.nextInt(5));
                        Summoned summonedentityxx = new Inferno(ModEntityType.INFERNO.get(), apostle.level());
                        if (s == 7) {//饥荒
                            summonedentityxx = new HuskServant(ModEntityType.HUSK_SERVANT.get(), apostle.level());
                        } else if (s == 2) {//毒蝎
                            summonedentityxx = new CaveSpiderServant(ModEntityType.CAVE_SPIDER_SERVANT.get(), apostle.level());
                        } else if (s == 9) {//骇人
                            summonedentityxx = new WraithServant(ModEntityType.WRAITH_SERVANT.get(), apostle.level());
                        } else if (s == 3) {//漆黑
                            summonedentityxx = new SkeletonServant(ModEntityType.SKELETON_SERVANT.get(), apostle.level());
                        } else if (s == 8) {//寒冬
                            summonedentityxx = new StrayServant(ModEntityType.STRAY_SERVANT.get(), apostle.level());
                        }
                        Malghast malghast = new Malghast(ModEntityType.MALGHAST.get(), apostle.level());
                        malghast.setTrueOwner(apostle);
                        malghast.setLimitedLife(60 * (90 + apostle.level().random.nextInt(180)));
                        malghast.finalizeSpawn(serverLevel, apostle.level().getCurrentDifficultyAt(blockpos$mutablex.above(2)), MobSpawnType.MOB_SUMMONED, null, null);
                        malghast.setTarget(livingentity);

                        PhantomServant phantomServant = new PhantomServant(z1gned.goetyrevelation.entitiy.ModEntityType.PHANTOM_SERVANT.get(), apostle.level());
                        phantomServant.setTrueOwner(apostle);
                        phantomServant.setLimitedLife(60 * (90 + apostle.level().random.nextInt(180)));
                        phantomServant.finalizeSpawn(serverLevel, apostle.level().getCurrentDifficultyAt(blockpos$mutablex.above(2)), MobSpawnType.MOB_SUMMONED, null, null);
                        phantomServant.setTarget(livingentity);

                        summonedentityxx.moveTo(blockpos$mutablex, 0.0F, 0.0F);
                        summonedentityxx.setTrueOwner(apostle);
                        summonedentityxx.setLimitedLife(60 * (90 + apostle.level().random.nextInt(180)));
                        summonedentityxx.finalizeSpawn(serverLevel, apostle.level().getCurrentDifficultyAt(blockpos$mutablex), MobSpawnType.MOB_SUMMONED, null, null);
                        summonedentityxx.setTarget(livingentity);
                        SummonCircle summonCirclexx = new SummonCircle(apostle.level(), blockpos$mutablex, summonedentityxx, false, true, apostle);
                        if (s == 1 && apostle.isSecondPhase()) {
                            summonCirclexx = new SummonCircle(apostle.level(), blockpos$mutablex, malghast, false, true, apostle);
                        } else if (s == 4) {
                            summonCirclexx = new SummonCircle(apostle.level(), blockpos$mutablex, phantomServant, false, true, apostle);
                        }

                        apostle.level().addFreshEntity(summonCirclexx);
                    }

                    apostle.setInfernoCoolDown(MathHelper.secondsToTicks(45));
                }

                apostle.postSpellCast();
            }
        }
    }

    protected SoundEvent getSpellPrepareSound() {
        return ModSounds.APOSTLE_PREPARE_SUMMON.get();
    }

    protected SpellCastingCultist.SpellType getSpellType() {
        return SpellCastingCultist.SpellType.RANGED;
    }
}
