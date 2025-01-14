package z1gned.goetyrevelation.client.event;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import z1gned.goetyrevelation.ModMain;
import z1gned.goetyrevelation.client.shader.core.NetherStarShaderInstance;
import z1gned.goetyrevelation.client.uniform.ATAUniform;

import java.util.Objects;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class NetherStarShaders {
    public static final float[] COSMIC_UVS = new float[40];
    public static NetherStarShaderInstance cosmicShader;
    public static ATAUniform cosmicTime;
    public static ATAUniform cosmicYaw;
    public static ATAUniform cosmicPitch;
    public static ATAUniform cosmicExternalScale;
    public static ATAUniform cosmicOpacity;
    public static ATAUniform cosmicHurt;
    public static ATAUniform cosmicUVs;
    public static ATAUniform cosmicUVs1;
    public static ATAUniform cosmicUVs2;
    public static ATAUniform cosmicUVs3;
    public static ATAUniform cosmicUVs4;
    public static ATAUniform cosmicUVs5;
    public static ATAUniform cosmicUVs6;
    public static ATAUniform cosmicUVs7;
    public static ATAUniform cosmicUVs8;
    public static ATAUniform cosmicUVs9;
    public static RenderType COSMIC_RENDER_TYPE = create();
    public static boolean inventoryRender;

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void drawScreenPre(ScreenEvent.Render.Pre e) {
        inventoryRender = true;
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void drawScreenPost(ScreenEvent.Render.Post e) {
        inventoryRender = false;
    }

    public static void onRegisterShaders(RegisterShadersEvent event) {
        event.registerShader(NetherStarShaderInstance.create(event.getResourceProvider(), new ResourceLocation(ModMain.MODID, "cosmic"), DefaultVertexFormat.BLOCK), (e) -> {
            cosmicShader = (NetherStarShaderInstance) e;
            cosmicTime = Objects.requireNonNull(cosmicShader.getUniform("time"));
            cosmicYaw = Objects.requireNonNull(cosmicShader.getUniform("yaw"));
            cosmicPitch = Objects.requireNonNull(cosmicShader.getUniform("pitch"));
            cosmicExternalScale = Objects.requireNonNull(cosmicShader.getUniform("externalScale"));
            cosmicOpacity = Objects.requireNonNull(cosmicShader.getUniform("opacity"));
            cosmicHurt = Objects.requireNonNull(cosmicShader.getUniform("hurt"));
            cosmicUVs = Objects.requireNonNull(cosmicShader.getUniform("cosmicuvs"));
            cosmicUVs1 = Objects.requireNonNull(cosmicShader.getUniform("cosmicuvs1"));
            cosmicUVs2 = Objects.requireNonNull(cosmicShader.getUniform("cosmicuvs2"));
            cosmicUVs3 = Objects.requireNonNull(cosmicShader.getUniform("cosmicuvs3"));
            cosmicUVs4 = Objects.requireNonNull(cosmicShader.getUniform("cosmicuvs4"));
            cosmicUVs5 = Objects.requireNonNull(cosmicShader.getUniform("cosmicuvs5"));
            cosmicUVs6 = Objects.requireNonNull(cosmicShader.getUniform("cosmicuvs6"));
            cosmicUVs7 = Objects.requireNonNull(cosmicShader.getUniform("cosmicuvs7"));
            cosmicUVs8 = Objects.requireNonNull(cosmicShader.getUniform("cosmicuvs8"));
            cosmicUVs9 = Objects.requireNonNull(cosmicShader.getUniform("cosmicuvs9"));
        });
    }

    public static RenderType create() {
        return RenderType.create("goety_revelation:cosmic", new VertexFormat(ImmutableMap.<String, VertexFormatElement>builder().put("Position", DefaultVertexFormat.ELEMENT_POSITION).put("Color", DefaultVertexFormat.ELEMENT_COLOR).put("UV0", DefaultVertexFormat.ELEMENT_UV0).put("UV2", DefaultVertexFormat.ELEMENT_UV2).put("Normal", DefaultVertexFormat.ELEMENT_NORMAL).put("Padding", DefaultVertexFormat.ELEMENT_PADDING).build()), VertexFormat.Mode.QUADS, 2097152, true, false, RenderType.CompositeState.builder().setShaderState(new RenderStateShard.ShaderStateShard(() -> cosmicShader)).setDepthTestState(RenderStateShard.EQUAL_DEPTH_TEST).setLightmapState(RenderStateShard.LIGHTMAP).setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY).setTextureState(RenderStateShard.BLOCK_SHEET_MIPPED).createCompositeState(true));
    }

}
