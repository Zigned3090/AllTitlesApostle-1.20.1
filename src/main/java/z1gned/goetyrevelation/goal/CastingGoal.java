package z1gned.goetyrevelation.goal;

import com.Polarice3.Goety.common.entities.boss.Apostle;
import com.Polarice3.Goety.common.entities.hostile.cultists.SpellCastingCultist;
import com.Polarice3.Goety.init.ModSounds;
import net.minecraft.sounds.SoundEvent;
import org.jetbrains.annotations.Nullable;

public abstract class CastingGoal extends UseSpellGoal {
    Apostle apostle;

    protected CastingGoal(SpellCastingCultist mob) {
        super(mob);
        this.apostle = (Apostle) mob;
    }

    public boolean canContinueToUse() {
        return super.canContinueToUse() && !apostle.isSettingUpSecond();
    }

    public void start() {
        super.start();
        apostle.setCasting(true);
    }

    public void stop() {
        super.stop();
        apostle.setCasting(false);
    }

    protected float castingVolume() {
        return 2.0F;
    }

    @Override
    protected int getCastingTime() {
        return 60;
    }

    protected int getCastingInterval() {
        return 0;
    }

    @Nullable
    @Override
    protected SoundEvent getSpellPrepareSound() {
        return ModSounds.APOSTLE_PREPARE_SUMMON.get();
    }

    @Override
    protected SpellCastingCultist.SpellType getSpellType() {
        return SpellCastingCultist.SpellType.RANGED;
    }
}
