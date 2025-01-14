package z1gned.goetyrevelation.mixin;

import com.Polarice3.Goety.common.entities.hostile.cultists.SpellCastingCultist;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SpellCastingCultist.class)
public interface SpellCastingCultistAccessor {

    @Accessor("spellTicks")
    void setSpellTick(int tick);

}
