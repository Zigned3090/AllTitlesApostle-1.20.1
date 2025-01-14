package z1gned.goetyrevelation.item.focus;

import com.Polarice3.Goety.common.magic.Spell;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class WitherQuietusSpell extends Spell {
    @Override
    public int defaultSoulCost() {
        return 0;
    }

    @Override
    public int defaultCastDuration() {
        return 0;
    }

    @Override
    public @Nullable SoundEvent CastingSound() {
        return null;
    }

    @Override
    public int defaultSpellCooldown() {
        return 0;
    }

    @Override
    public void SpellResult(ServerLevel serverLevel, LivingEntity livingEntity, ItemStack itemStack) {

    }
}
