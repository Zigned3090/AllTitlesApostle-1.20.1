package z1gned.goetyrevelation.client.event;

import com.Polarice3.Goety.client.render.model.ApostleModel;
import com.Polarice3.Goety.common.entities.boss.Apostle;
import com.mojang.blaze3d.Blaze3D;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import z1gned.goetyrevelation.util.Easing;
import z1gned.goetyrevelation.util.RendererUtils;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientHandler {

    /**
     * For mod : Goety:Revelation
     */
    @SubscribeEvent
    public static void apollyonDeath(RenderLivingEvent<Apostle, ApostleModel<Apostle>> event) {
        if (event.getEntity() instanceof Apostle apostle) {
            if (apostle.level().dimensionTypeId() == BuiltinDimensionTypes.NETHER) {
                if (apostle.isDeadOrDying()) {
                    if (apostle.deathTime > 200) return;
                    //获取平滑的死亡ticks
                    float partialTick = apostle.deathTime + event.getPartialTick();
                    //渲染进度:ticks < 40时增长, ticks > 150时减小, 最后被Easing平滑处理
                    float percent = Easing.IN_OUT_SINE.calculate(partialTick < 60F ? partialTick / 60F : partialTick > 150F ? (1F - (partialTick - 150F) / (200F - 150F)) : 1F);
                    //缩放大小:1F -> 半径1方块
                    float scale = (8F) * (Math.max(0F, percent));
                    PoseStack stack = event.getPoseStack();
                    System.out.println(1);
                    MultiBufferSource.BufferSource source = Minecraft.getInstance().renderBuffers().bufferSource();
                    stack.pushPose();
                    float degrees = (float) Blaze3D.getTime() * 50F;
                    //旋转
                    stack.mulPose(Axis.XP.rotationDegrees(degrees));
                    stack.mulPose(Axis.YP.rotationDegrees(degrees));
                    stack.mulPose(Axis.ZP.rotationDegrees(degrees));
                    //炫！
                    RendererUtils.renderRegularIcosahedron(stack, source, scale, 0xF000F0, 138F / 255F, 0F, 27F / 255F, percent, percent);
                    stack.popPose();
                }
            }
        }
    }
}
