package z1gned.goetyrevelation.goal;

import com.Polarice3.Goety.common.entities.boss.Apostle;
import com.Polarice3.Goety.common.entities.hostile.cultists.SpellCastingCultist;
import com.Polarice3.Goety.init.ModSounds;
import net.minecraft.sounds.SoundEvent;
import org.jetbrains.annotations.Nullable;
import z1gned.goetyrevelation.util.ApollyonAbilityHelper;

public class ApollyonDeathArrowGoal extends CastingGoal {
    Apostle apostle;
    public ApollyonDeathArrowGoal(Apostle mob) {
        super(mob);
        this.apostle = mob;
    }

    @Override
    protected void castSpell() {

    }

    @Override
    protected int getCastingTime() {
        return 100;
    }

    @Override
    protected int getCastingInterval() {
        return 1600;
    }

    @Override
    protected int getCastWarmupTime() {
        return 100;
    }

    @Override
    public boolean canUse() {
        if (!super.canUse()) {
            return false;
        } else if (this.apostle.getTarget() == null) {
            return false;
        } else {
            return ((ApollyonAbilityHelper) this.apostle).allTitlesApostle_1_20_1$isApollyon() && this.apostle.isInNether();
        }
    }

    @Override
    public void start() {
        super.start();
        ((ApollyonAbilityHelper) this.apostle).allTitlesApostle_1_20_1$setShooting(true);
    }

    @Override
    public void stop() {
        super.stop();
        ((ApollyonAbilityHelper) this.apostle).allTitlesApostle_1_20_1$setShooting(false);
    }

    @Override
    protected @Nullable SoundEvent getSpellPrepareSound() {
        return ModSounds.APOSTLE_PREPARE_SPELL.get();
    }

    @Override
    protected SpellCastingCultist.SpellType getSpellType() {
        return SpellCastingCultist.SpellType.FIRE;
    }
}
