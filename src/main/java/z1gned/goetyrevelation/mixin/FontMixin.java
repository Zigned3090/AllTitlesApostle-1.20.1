package z1gned.goetyrevelation.mixin;

import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.TextColor;
import net.minecraft.util.FormattedCharSequence;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import z1gned.goetyrevelation.util.ATAHelper;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Mixin(Font.class)
public abstract class FontMixin {

    @Shadow public abstract void drawInBatch8xOutline(FormattedCharSequence p_168646_, float p_168647_, float p_168648_, int p_168649_, int p_168650_, Matrix4f p_254170_, MultiBufferSource p_168652_, int p_168653_);

    /*@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;drawInternal(Lnet/minecraft/util/FormattedCharSequence;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/gui/Font$DisplayMode;II)I"), method = "drawInBatch(Lnet/minecraft/util/FormattedCharSequence;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/gui/Font$DisplayMode;II)I")
    private int drawOut(Font instance, FormattedCharSequence p_273025_, float p_273121_, float p_272717_, int p_273653_, boolean p_273531_, Matrix4f p_273265_, MultiBufferSource p_273560_, Font.DisplayMode p_273342_, int p_273373_, int p_273266_) {
        AtomicInteger i = new AtomicInteger();
        AtomicReference<String> str = new AtomicReference<>("");
        p_273025_.accept(((index, style, codePoint) -> {
            Optional<TextColor> optional = Optional.ofNullable(style.getColor());
            if (optional.isPresent()) {
                TextColor color1 = optional.get();
                i.set(color1.getValue());
                str.set(color1.serialize());
            }

            return true;
        }));

        int secondColor = 0;
        boolean bl = i.get() == 0 && str.get().equals("bred");
        if (bl) {
            if (i.get() == 0) {
                secondColor = 11141120;
            }

            this.drawInBatch8xOutline(p_273025_, p_273121_, p_272717_, i.get(), secondColor, p_273265_, p_273560_, p_273266_);
            return p_273653_;
        } else {
            return this.drawInternal(p_273025_, p_273121_, p_272717_, p_273653_, p_273531_, p_273265_, p_273560_, p_273342_, p_273373_, p_273266_);
        }
    }*/

    @Inject(at = @At("TAIL"), method = "drawInBatch(Lnet/minecraft/util/FormattedCharSequence;FFIZLorg/joml/Matrix4f;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/client/gui/Font$DisplayMode;II)I")
    private void drawOutLine(FormattedCharSequence p_273262_, float p_273006_, float p_273254_, int p_273375_, boolean p_273674_, Matrix4f p_273525_, MultiBufferSource p_272624_, Font.DisplayMode p_273418_, int p_273330_, int p_272981_, CallbackInfoReturnable<Integer> cir) {
        if (ATAHelper.getFormattingName(p_273262_).equals("apollyon")) {
            this.drawInBatch8xOutline(p_273262_, p_273006_, p_273254_, p_273375_, 11141120, p_273525_, p_272624_, p_272981_);
        }
    }

}
