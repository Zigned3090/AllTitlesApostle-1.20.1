package z1gned.goetyrevelation.mixin;

import com.Polarice3.Goety.common.entities.hostile.cultists.SpellCastingCultist;
import net.minecraft.sounds.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SpellCastingCultist.class)
public interface SpellCastingCultistInvoker {

    @Invoker("getCastingSoundEvent")
    SoundEvent getSound();

}
