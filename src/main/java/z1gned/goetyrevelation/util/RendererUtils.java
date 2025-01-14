package z1gned.goetyrevelation.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import org.joml.*;
import java.lang.Math;

public class RendererUtils {
    public static void vertexRPNormal(VertexConsumer vertexConsumer, Matrix4f matrix4f, Matrix3f normal, int light, float x, float y, float z, float u, float v, float nX, float nY, float nZ, float r, float g, float b, float a) {
        vertexConsumer.vertex(matrix4f, x, y, 0.0F).color(r, g, b ,a).uv(u, v).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(light).normal(normal, nX, nY, nZ).endVertex();
    }
    public static void renderLineBox(PoseStack poseStack, VertexConsumer vertexConsumer, float minX, float minY, float minZ, float maxX, float maxY, float maxZ, float r, float g, float b, float a, float r2, float g2, float b2) {
        Matrix4f matrix4f = poseStack.last().pose();
        Matrix3f matrix3f = poseStack.last().normal();
        vertexConsumer.vertex(matrix4f, minX, minY, minZ).color(r, g2, b2, a).normal(matrix3f, 1.0F, 0.0F, 0.0F).endVertex();
        vertexConsumer.vertex(matrix4f, maxX, minY, minZ).color(r, g2, b2, a).normal(matrix3f, 1.0F, 0.0F, 0.0F).endVertex();
        vertexConsumer.vertex(matrix4f, minX, minY, minZ).color(r2, g, b2, a).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
        vertexConsumer.vertex(matrix4f, minX, maxY, minZ).color(r2, g, b2, a).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
        vertexConsumer.vertex(matrix4f, minX, minY, minZ).color(r2, g2, b, a).normal(matrix3f, 0.0F, 0.0F, 1.0F).endVertex();
        vertexConsumer.vertex(matrix4f, minX, minY, maxZ).color(r2, g2, b, a).normal(matrix3f, 0.0F, 0.0F, 1.0F).endVertex();
        vertexConsumer.vertex(matrix4f, maxX, minY, minZ).color(r, g, b, a).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
        vertexConsumer.vertex(matrix4f, maxX, maxY, minZ).color(r, g, b, a).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
        vertexConsumer.vertex(matrix4f, maxX, maxY, minZ).color(r, g, b, a).normal(matrix3f, -1.0F, 0.0F, 0.0F).endVertex();
        vertexConsumer.vertex(matrix4f, minX, maxY, minZ).color(r, g, b, a).normal(matrix3f, -1.0F, 0.0F, 0.0F).endVertex();
        vertexConsumer.vertex(matrix4f, minX, maxY, minZ).color(r, g, b, a).normal(matrix3f, 0.0F, 0.0F, 1.0F).endVertex();
        vertexConsumer.vertex(matrix4f, minX, maxY, maxZ).color(r, g, b, a).normal(matrix3f, 0.0F, 0.0F, 1.0F).endVertex();
        vertexConsumer.vertex(matrix4f, minX, maxY, maxZ).color(r, g, b, a).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
        vertexConsumer.vertex(matrix4f, minX, minY, maxZ).color(r, g, b, a).normal(matrix3f, 0.0F, -1.0F, 0.0F).endVertex();
        vertexConsumer.vertex(matrix4f, minX, minY, maxZ).color(r, g, b, a).normal(matrix3f, 1.0F, 0.0F, 0.0F).endVertex();
        vertexConsumer.vertex(matrix4f, maxX, minY, maxZ).color(r, g, b, a).normal(matrix3f, 1.0F, 0.0F, 0.0F).endVertex();
        vertexConsumer.vertex(matrix4f, maxX, minY, maxZ).color(r, g, b, a).normal(matrix3f, 0.0F, 0.0F, -1.0F).endVertex();
        vertexConsumer.vertex(matrix4f, maxX, minY, minZ).color(r, g, b, a).normal(matrix3f, 0.0F, 0.0F, -1.0F).endVertex();
        vertexConsumer.vertex(matrix4f, minX, maxY, maxZ).color(r, g, b, a).normal(matrix3f, 1.0F, 0.0F, 0.0F).endVertex();
        vertexConsumer.vertex(matrix4f, maxX, maxY, maxZ).color(r, g, b, a).normal(matrix3f, 1.0F, 0.0F, 0.0F).endVertex();
        vertexConsumer.vertex(matrix4f, maxX, minY, maxZ).color(r, g, b, a).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
        vertexConsumer.vertex(matrix4f, maxX, maxY, maxZ).color(r, g, b, a).normal(matrix3f, 0.0F, 1.0F, 0.0F).endVertex();
        vertexConsumer.vertex(matrix4f, maxX, maxY, minZ).color(r, g, b, a).normal(matrix3f, 0.0F, 0.0F, 1.0F).endVertex();
        vertexConsumer.vertex(matrix4f, maxX, maxY, maxZ).color(r, g, b, a).normal(matrix3f, 0.0F, 0.0F, 1.0F).endVertex();
    }
    public static void renderRegularPolygon5(Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer bb, float radius, int packedLight, float r, float g, float b, float a, float percentage, float depth) {
        float PI = 3.1415926F;
        float angel = PI * 0.4F;
        float alpha;
        for (alpha = 0.0F; alpha < 2F * PI; alpha += angel) {
            double cos = Mth.cos(alpha);
            double sin = Mth.sin(alpha);
            float aDelta = angel * percentage;
            double cos_ = Mth.cos(alpha + aDelta);
            double sin_ = Mth.sin(alpha + aDelta);
            float x = (float) (radius * cos);
            float y = (float) (radius * sin);
            float x2 = (float) (radius * cos_);
            float y2 = (float) (radius * sin_);
            matrix4f.translate(0, 0, depth);
            vertexRPNormal(bb, matrix4f, matrix3f, packedLight, x, y, 0, 0, 0, x2-x, 1.0F, y2-y, r, g ,b, a);
            vertexRPNormal(bb, matrix4f, matrix3f, packedLight, x2, y2, 0, 1, 0, x2-x, 1.0F, y2-y, r, g ,b, a);
            matrix4f.translate(0, 0, -depth);
        }
    }
    public static void renderRegularPolygon5div1(Matrix4f matrix4f, Matrix3f matrix3f, VertexConsumer bb, float radius, int packedLight, float r, float g, float b, float a, float percentage, float depth) {
        float PI = Mth.PI;
        float angel = PI * 0.4F;
        float alpha;
        for (alpha = 0.0F; alpha < 2F * PI; alpha += angel) {
            if (Float.floatToIntBits(alpha) == Float.floatToIntBits(angel*2F)) continue;
            double cos = Mth.cos(alpha);
            double sin = Mth.sin(alpha);
            double cos_ = Mth.lerp(percentage, cos, Mth.cos(alpha + angel));
            double sin_ = Mth.lerp(percentage, sin, Mth.sin(alpha + angel));
            float x = (float) (radius * cos);
            float y = (float) (radius * sin);
            float x2 = (float) (radius * cos_);
            float y2 = (float) (radius * sin_);
            matrix4f.translate(0, 0, depth);
            vertexRPNormal(bb, matrix4f, matrix3f, packedLight, x, y, 0, 0, 0, x2-x, 1.0F, y2-y, r, g ,b, a);
            vertexRPNormal(bb, matrix4f, matrix3f, packedLight, x2, y2, 0, 1, 0, x2-x, 1.0F, y2-y, r, g ,b, a);
            matrix4f.translate(0, 0, -depth);
        }
    }
    public static void renderRegularIcosahedron(PoseStack matrix, MultiBufferSource.BufferSource bufferSource, float radius, int packedLight, float r, float g, float b, float a, float percent) {
        float PI = 3.1415926535F;
        RenderType type = RenderType.lines();
        VertexConsumer bb = bufferSource.getBuffer(type);
        a = Math.max(0.0F, a);
        //x
        float alpha = 1.1074114103904023F;
        //y
        float beta = PI * 0.4F;
        float ri = 1.309016994F;
        float l = 1F;
        matrix.pushPose();
        matrix.mulPose(Axis.XP.rotation(-PI/2F));
        matrix.scale(radius, radius, radius);
        renderRegularPolygon5(matrix.last().pose(), matrix.last().normal(), bb, l, packedLight, r, g ,b ,a, percent, ri);
        for (int i=0;i<5;i++) {
            matrix.mulPose(Axis.ZP.rotation(beta));
            matrix.pushPose();
            matrix.mulPose(Axis.ZP.rotation(PI));
            matrix.mulPose(Axis.YP.rotation(alpha));
            renderRegularPolygon5div1(matrix.last().pose(), matrix.last().normal(), bb, l, packedLight, r, g ,b ,a, percent, ri);
            matrix.popPose();
        }
        matrix.mulPose(Axis.YP.rotation(PI));
        renderRegularPolygon5(matrix.last().pose(), matrix.last().normal(), bb, l, packedLight, r, g ,b ,a, percent, ri);
        for (int i=0;i<5;i++) {
            matrix.mulPose(Axis.ZP.rotationDegrees(72));
            matrix.pushPose();
            matrix.mulPose(Axis.ZP.rotation(PI));
            matrix.mulPose(Axis.YP.rotation(alpha));
            renderRegularPolygon5div1(matrix.last().pose(), matrix.last().normal(), bb, l, packedLight, r, g ,b ,a, percent, ri);
            matrix.popPose();
        }

        matrix.popPose();
        bufferSource.endBatch(type);
    }
}
