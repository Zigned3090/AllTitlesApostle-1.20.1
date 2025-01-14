package z1gned.goetyrevelation.goal;

import com.Polarice3.Goety.common.entities.boss.Apostle;
import com.Polarice3.Goety.common.entities.hostile.cultists.SpellCastingCultist;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import z1gned.goetyrevelation.mixin.SpellCastingCultistAccessor;
import z1gned.goetyrevelation.mixin.SpellCastingCultistInvoker;
import z1gned.goetyrevelation.util.ApollyonAbilityHelper;

import javax.annotation.Nullable;

public abstract class UseSpellGoal extends Goal {
    protected int spellWarmup;
    protected int spellCooldown;
    private final SpellCastingCultist mob;

    protected UseSpellGoal(SpellCastingCultist mob) {
        this.mob = mob;
    }

    public boolean canUse() {
        if (this.mob instanceof Apostle apostle && ((ApollyonAbilityHelper) apostle).allTitlesApostle_1_20_1$isSilent()) {
            return false;
        } else {
            LivingEntity livingentity = mob.getTarget();
            if (livingentity != null && livingentity.isAlive()) {
                if (mob.isSpellcasting()) {
                    return false;
                } else {
                    return mob.tickCount >= this.spellCooldown;
                }
            } else {
                return false;
            }
        }
    }

    public boolean canContinueToUse() {
        LivingEntity livingentity = mob.getTarget();
        return livingentity != null && livingentity.isAlive() && this.spellWarmup > 0;
    }

    public void start() {
        this.spellWarmup = this.getCastWarmupTime();
        ((SpellCastingCultistAccessor) mob).setSpellTick(this.getCastingTime());
        this.spellCooldown = mob.tickCount + this.getCastingInterval();
        SoundEvent soundevent = this.getSpellPrepareSound();
        if (soundevent != null) {
            mob.playSound(soundevent, this.castingVolume(), 1.0F);
        }

        mob.setSpellType(this.getSpellType());
    }

    public void tick() {
        --this.spellWarmup;
        if (this.spellWarmup == 0) {
            this.castSpell();
            mob.setSpellType(SpellCastingCultist.SpellType.NONE);
            mob.playSound(((SpellCastingCultistInvoker) mob).getSound(), 1.0F, 1.0F);
        }

    }

    protected float castingVolume() {
        return 1.0F;
    }

    protected abstract void castSpell();

    protected int getCastWarmupTime() {
        return 20;
    }

    protected abstract int getCastingTime();

    protected abstract int getCastingInterval();

    @Nullable
    protected abstract SoundEvent getSpellPrepareSound();

    protected abstract SpellCastingCultist.SpellType getSpellType();
}
