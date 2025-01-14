package z1gned.goetyrevelation.mixin;

import net.minecraft.ChatFormatting;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

@Mixin(ChatFormatting.class)
public class ChatFormattingMixin {

    @Shadow
    @Final
    @Mutable
    private static ChatFormatting[] $VALUES;

    ChatFormattingMixin(String id, int ordinal, String name, char code, int colorIndex, @Nullable Integer colorValue) {
        throw new AssertionError("NONE");
    }

    @Inject(at = @At(value = "FIELD", shift = At.Shift.AFTER, target = "Lnet/minecraft/ChatFormatting;$VALUES:[Lnet/minecraft/ChatFormatting;"), method = "<clinit>")
    private static void addFormatting(CallbackInfo ci) {
        int ordinal = $VALUES.length;
        $VALUES = Arrays.copyOf($VALUES, ordinal + 1);
        $VALUES[ordinal] = (ChatFormatting) (Object) new ChatFormattingMixin("APOLLYON", ordinal, "APOLLYON", 'q', 0, 0);
    }

}
