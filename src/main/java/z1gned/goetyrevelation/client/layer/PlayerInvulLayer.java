package z1gned.goetyrevelation.client.layer;

import com.Polarice3.Goety.Goety;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import z1gned.goetyrevelation.client.model.PlayerHaloModel;
import z1gned.goetyrevelation.util.PlayerAbilityHelper;

public class PlayerInvulLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    private static final ResourceLocation ARMOR = Goety.location("textures/entity/cultist/apostle_aura.png");
    private final PlayerModel<AbstractClientPlayer> model;

    public PlayerInvulLayer(RenderLayerParent p_117346_, EntityModelSet entityModelSet) {
        super(p_117346_);
        this.model = new PlayerModel<>(entityModelSet.bakeLayer(this.getParentModel().slim ? ModelLayers.PLAYER_SLIM : ModelLayers.PLAYER), this.getParentModel().slim);
    }

    public void render(PoseStack p_116970_, MultiBufferSource p_116971_, int p_116972_, AbstractClientPlayer p_116973_, float p_116974_, float p_116975_, float p_116976_, float p_116977_, float p_116978_, float p_116979_) {
        if (((PlayerAbilityHelper) p_116973_).getInvulTick() > 0 && !p_116973_.isDeadOrDying()) {
            float f = (float)p_116973_.tickCount + p_116976_;
            PlayerModel<AbstractClientPlayer> entitymodel = this.model;
            entitymodel.prepareMobModel(p_116973_, p_116974_, p_116975_, p_116976_);
            this.getParentModel().copyPropertiesTo(entitymodel);
            VertexConsumer vertexconsumer = p_116971_.getBuffer(RenderType.energySwirl(ARMOR, this.xOffset(f) % 1.0F, f * 0.01F % 1.0F));
            entitymodel.setupAnim(p_116973_, p_116974_, p_116975_, p_116977_, p_116978_, p_116979_);
            entitymodel.renderToBuffer(p_116970_, vertexconsumer, p_116972_, OverlayTexture.NO_OVERLAY, 0.5F, 0.5F, 0.5F, 1.0F);
            if (entitymodel instanceof PlayerHaloModel<AbstractClientPlayer> playerHaloModel) {
                playerHaloModel.halo.visible = false;
            }
        }

    }

    protected float xOffset(float p_225634_1_) {
        return Mth.cos(p_225634_1_ * 0.02F) * 3.0F;
    }

}
