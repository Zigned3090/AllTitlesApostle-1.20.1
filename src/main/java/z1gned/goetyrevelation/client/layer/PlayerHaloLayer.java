package z1gned.goetyrevelation.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import z1gned.goetyrevelation.client.model.PlayerHaloModel;
import z1gned.goetyrevelation.client.ModLayerAndModels;
import z1gned.goetyrevelation.util.ATAHelper;
import z1gned.goetyrevelation.util.PlayerAbilityHelper;

import static z1gned.goetyrevelation.ModMain.MODID;

@OnlyIn(Dist.CLIENT)
public class PlayerHaloLayer<T extends AbstractClientPlayer, M extends PlayerModel<T>> extends RenderLayer<T, M> {
    private static final ResourceLocation BROKEN_HALO = new ResourceLocation(MODID, "textures/entity/player/broken_halo.png");
    private static final ResourceLocation HALO = new ResourceLocation(MODID, "textures/entity/player/halo.png");
    private static final ResourceLocation HALO_SECOND = new ResourceLocation(MODID, "textures/entity/player/halo_second.png");

    private final PlayerHaloModel<T> model;

    public PlayerHaloLayer(RenderLayerParent<T, M> p_117346_, EntityModelSet modelSet) {
        super(p_117346_);
        this.model = new PlayerHaloModel<>(modelSet.bakeLayer(ModLayerAndModels.PLAYER_HEAD_MODEL), false);
    }

    @Override
    public void render(PoseStack p_116924_, MultiBufferSource p_116925_, int p_116926_, T p_116927_, float p_116928_, float p_116929_, float p_116930_, float p_116931_, float p_116932_, float p_116933_) {
        p_116924_.pushPose();
        if (ATAHelper.hasBrokenHalo(p_116927_)) {
            this.model.halo.translateAndRotate(p_116924_);
            this.model.halo1.render(p_116924_, p_116925_.getBuffer(RenderType.energySwirl(BROKEN_HALO, 1.0F, 1.0F)), p_116926_, OverlayTexture.NO_OVERLAY);
        } else if (ATAHelper.hasHalo(p_116927_)) {
            this.model.halo.translateAndRotate(p_116924_);
            p_116924_.mulPose(Axis.ZP.rotationDegrees(90 * ((PlayerAbilityHelper) p_116927_).getSpin()));
            this.model.halo1.render(p_116924_, p_116925_.getBuffer(RenderType.energySwirl(((PlayerAbilityHelper) p_116927_).getMeteoring() > 0 ? HALO_SECOND : HALO, 1.0F, 1.0F)), p_116926_, OverlayTexture.NO_OVERLAY);
        }
        p_116924_.popPose();
    }

}
