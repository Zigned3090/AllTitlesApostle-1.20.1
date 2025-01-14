package z1gned.goetyrevelation.util;

import com.Polarice3.Goety.utils.CuriosFinder;
import net.minecraft.network.chat.TextColor;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.LivingEntity;
import z1gned.goetyrevelation.item.AscensionHalo;
import z1gned.goetyrevelation.item.BrokenAscensionHalo;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class ATAHelper {

    public static boolean hasHalo(LivingEntity player) {
        return CuriosFinder.hasCurio(player, stack -> stack.getItem() instanceof AscensionHalo);
    }

    public static boolean hasBrokenHalo(LivingEntity player) {
        return CuriosFinder.hasCurio(player, stack -> stack.getItem() instanceof BrokenAscensionHalo);
    }

    public static int halfValueCondition(boolean bl, int i) {
        return bl ? i / 2 : i;
    }

    public static String getFormattingName(FormattedCharSequence fcs) {
        AtomicReference<String> str = new AtomicReference<>("");
        fcs.accept(((index, style, codePoint) -> {
            Optional<TextColor> optional = Optional.ofNullable(style.getColor());
            if (optional.isPresent()) {
                TextColor color1 = optional.get();
                str.set(color1.serialize());
            }

            return true;
        }));

        return str.get();
    }

}
