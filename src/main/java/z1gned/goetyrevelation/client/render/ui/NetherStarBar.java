package z1gned.goetyrevelation.client.render.ui;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix4f;
import org.joml.Vector2d;
import org.joml.Vector4f;
import z1gned.goetyrevelation.client.event.NetherStarShaders;

@OnlyIn(Dist.CLIENT)
public class NetherStarBar {

    private static final Minecraft mc = Minecraft.getInstance();

    public static void blitCosmicBar(PoseStack stack, ResourceLocation p_283272_, float p_283605_, float p_281879_, float p_282809_, float p_282942_, int p_281922_, int p_282385_, int p_282596_, int p_281699_, boolean shake, int hurt) {
        blitCosmicBar(stack, p_283272_, p_283605_, p_281879_, p_281922_, p_282385_, p_282809_, p_282942_, p_281922_, p_282385_, p_282596_, p_281699_, shake, hurt);
    }

    public static void blitCosmicBar(PoseStack stack, ResourceLocation p_282034_, float p_283671_, float p_282377_, int p_282058_, int p_281939_, float p_282285_, float p_283199_, int p_282186_, int p_282322_, int p_282481_, int p_281887_, boolean shake, int hurt) {
        blitCosmicBar(stack, p_282034_, p_283671_, p_283671_ + p_282058_, p_282377_, p_282377_ + p_281939_, 0, p_282186_, p_282322_, p_282285_, p_283199_, p_282481_, p_281887_, shake, hurt);
    }

    static void blitCosmicBar(PoseStack stack, ResourceLocation p_282639_, float p_282732_, float p_283541_, float p_281760_, float p_283298_, int p_283429_, int p_282193_, int p_281980_, float p_282660_, float p_281522_, int p_282315_, int p_281436_, boolean shake, int hurt) {
        blitCosmicBar(stack, p_282639_, p_282732_, p_283541_, p_281760_, p_283298_, p_283429_, (p_282660_ + 0.0F) / (float) p_282315_, (p_282660_ + (float) p_282193_) / (float) p_282315_, (p_281522_ + 0.0F) / (float) p_281436_, (p_281522_ + (float) p_281980_) / (float) p_281436_, shake, hurt);
    }

    static void blitCosmicBar(PoseStack stack, ResourceLocation p_283461_, float p_281399_, float p_283222_, float p_283615_, float p_283430_, int p_281729_, float p_283247_, float p_282598_, float p_282883_, float p_283017_, boolean shake, int hurt) {
        RenderSystem.setShaderTexture(0, p_283461_);
        Camera camera = mc.gameRenderer.getMainCamera();
        float yd = (float) camera.getPosition().y;
        float xzd = (float) new Vector2d(camera.getPosition().x, camera.getPosition().y).distance(0, 0);
        float pitch = 0;
        float yaw = 0;
        if (mc.player != null) {
           /*pitch = -((float) ((double) (mc.player.getXRot()) * Math.PI / 360.0) - yd / 16F);
           yaw = -(float) ((double) (mc.player.getYRot()) * Math.PI / 360.0) - xzd / 16F;*/
            yaw = (float) (mc.player.getYRot() * 0.00001);
            pitch = (float) (mc.player.getXRot() * 0.00001);
        }
        float scale = 100;
        enableCosmicShader(yaw, pitch, scale, hurt);
        Matrix4f matrix4f = stack.last().pose();
        Vector4f color = shake ? new Vector4f(1F, 1f, 1f, 2f) : new Vector4f(1F);
        if (shake) {
            mc.gameRenderer.lightTexture().turnOnLightLayer();
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        }
        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.BLOCK);
        bufferbuilder.vertex(matrix4f, p_281399_, (float) p_283615_, (float) p_281729_).color(color.x, color.y, color.z, color.w).uv(p_283247_, p_282883_).uv2(0xF000F0).normal(0f, 0f, 0f).endVertex();
        bufferbuilder.vertex(matrix4f, p_281399_, (float) p_283430_, (float) p_281729_).color(color.x, color.y, color.z, color.w).uv(p_283247_, p_283017_).uv2(0xF000F0).normal(0f, 0f, 0f).endVertex();
        bufferbuilder.vertex(matrix4f, p_283222_, (float) p_283430_, (float) p_281729_).color(color.x, color.y, color.z, color.w).uv(p_282598_, p_283017_).uv2(0xF000F0).normal(0f, 0f, 0f).endVertex();
        bufferbuilder.vertex(matrix4f, p_283222_, (float) p_283615_, (float) p_281729_).color(color.x, color.y, color.z, color.w).uv(p_282598_, p_282883_).uv2(0xF000F0).normal(0f, 0f, 0f).endVertex();
        BufferUploader.drawWithShader(bufferbuilder.end());
        if (shake) {
            RenderSystem.defaultBlendFunc();
            mc.gameRenderer.lightTexture().turnOffLightLayer();
        }
    }

    static void enableCosmicShader(float yaw, float pitch, float scale, int hurt) {
        NetherStarShaders.cosmicTime.set(Util.getMillis() / 20.0F);
        NetherStarShaders.cosmicYaw.set(yaw);
        NetherStarShaders.cosmicPitch.set(pitch);
        NetherStarShaders.cosmicExternalScale.set(scale);
        NetherStarShaders.cosmicOpacity.set(10.0F);
        NetherStarShaders.cosmicHurt.set(hurt);
        NetherStarShaders.cosmicShader.setCosmicIcon();
        NetherStarShaders.cosmicShader.apply();
        RenderSystem.setShader(() -> NetherStarShaders.cosmicShader);
    }

}
