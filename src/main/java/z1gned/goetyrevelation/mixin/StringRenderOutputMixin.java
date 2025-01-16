package z1gned.goetyrevelation.mixin;

import com.mojang.blaze3d.font.GlyphInfo;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.client.gui.font.glyphs.EmptyGlyph;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.util.FastColor;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Font.StringRenderOutput.class)
public abstract class StringRenderOutputMixin {
    @Shadow public float x;

    @Shadow public float y;

    @Shadow public float r;

    @Shadow public float g;

    @Shadow public float b;

    @Shadow public float a;

    @Shadow public MultiBufferSource bufferSource;

    @Shadow public boolean dropShadow;

    @Shadow public Font.DisplayMode mode;

    @Shadow public Matrix4f pose;

    @Shadow public int packedLightCoords;

    @Shadow public float dimFactor;

    @Shadow public abstract void addEffect(BakedGlyph.Effect p_92965_);

    @Inject(method = "accept",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/network/chat/Style;getColor()Lnet/minecraft/network/chat/TextColor;",
                    shift = At.Shift.AFTER
            ),
            locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    public void accept(int p_92967_, Style p_92968_, int p_92969_, CallbackInfoReturnable<Boolean> cir, FontSet fontset, GlyphInfo glyphinfo, BakedGlyph bakedglyph, boolean flag, float f3) {
        TextColor textcolor = p_92968_.getColor();
        if (textcolor == null) return;
        String serializeName = textcolor.serialize();
        if (serializeName.equals("apollyon")) {
            final float temX = x;
            final float temY = y;
            final float[] rgba = new float[] {r, g, b, a};
            for(int j = -1; j <= 1; ++j) {
                for(int k = -1; k <= 1; ++k) {
                    if (j != 0 || k != 0) {
                        float[] afloat = new float[]{temX};
                        x = afloat[0] + (float)j * glyphinfo.getShadowOffset();
                        y = temY + (float)k * glyphinfo.getShadowOffset();
                        afloat[0] += glyphinfo.getAdvance(flag);
                        float f6 = flag ? glyphinfo.getBoldOffset() : 0.0F;
                        float f7 = this.dropShadow ? glyphinfo.getShadowOffset() : 0.0F;
                        VertexConsumer vertexconsumer = this.bufferSource.getBuffer(bakedglyph.renderType(this.mode));  
                        if (!(bakedglyph instanceof EmptyGlyph)) {

                                int i = allTitleApostle$adjustColor(11141120);
                                float f = (float) (i >> 16 & 255) / 255.0F * this.dimFactor;
                                float f1 = (float) (i >> 8 & 255) / 255.0F * this.dimFactor;
                                float f2 = (float) (i & 255) / 255.0F * this.dimFactor;
                                allTitleApostle$renderChar(bakedglyph, flag, p_92968_.isItalic(), f6, this.x + f7, this.y + f7, this.pose, vertexconsumer, f, f1, f2, f3, this.packedLightCoords);

                        }
                    }
                }
            }
            this.x = temX;
            this.y = temY;
            this.r = rgba[0];
            this.g = rgba[1];
            this.b = rgba[2];
            this.a = rgba[3];
        }
    }
    @Unique
    private void allTitleApostle$renderChar(BakedGlyph p_254105_, boolean p_254001_, boolean p_254262_, float p_254256_, float p_253753_, float p_253629_, Matrix4f p_254014_, VertexConsumer p_253852_, float p_254317_, float p_253809_, float p_253870_, float p_254287_, int p_253905_) {
        p_254105_.render(p_254262_, p_253753_, p_253629_, p_254014_, p_253852_, p_254317_, p_253809_, p_253870_, p_254287_, p_253905_);
        if (p_254001_) {
            p_254105_.render(p_254262_, p_253753_ + p_254256_, p_253629_, p_254014_, p_253852_, p_254317_, p_253809_, p_253870_, p_254287_, p_253905_);
        }

    }
    @Unique
    private static int allTitleApostle$adjustColor(int p_92720_) {
        return (p_92720_ & -67108864) == 0 ? p_92720_ | -16777216 : p_92720_;
    }
    @Unique
    private static int allTitleApostle$getDarkColor(int i) {
        double d0 = 0.4D;
        int j = (int) ((double) FastColor.ARGB32.red(i) * d0);
        int k = (int) ((double) FastColor.ARGB32.green(i) * d0);
        int l = (int) ((double) FastColor.ARGB32.blue(i) * d0);
        return FastColor.ARGB32.color(0, j, k, l);
    }
}
