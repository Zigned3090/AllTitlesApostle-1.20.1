package z1gned.goetyrevelation.client.layer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EnergySwirlLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import z1gned.goetyrevelation.client.model.WitherServantModel;
import z1gned.goetyrevelation.entitiy.WitherServant;

public class WitherServantArmorLayer extends EnergySwirlLayer<WitherServant, WitherServantModel<WitherServant>> {
    private static final ResourceLocation WITHER_ARMOR_LOCATION = new ResourceLocation("textures/entity/wither/wither_armor.png");
    private final WitherServantModel<WitherServant> model;

    public WitherServantArmorLayer(RenderLayerParent<WitherServant, WitherServantModel<WitherServant>> p_174554_, EntityModelSet p_174555_) {
        super(p_174554_);
        this.model = new WitherServantModel<>(p_174555_.bakeLayer(ModelLayers.WITHER_ARMOR));
    }

    protected float xOffset(float p_117702_) {
        return Mth.cos(p_117702_ * 0.02F) * 3.0F;
    }

    protected ResourceLocation getTextureLocation() {
        return WITHER_ARMOR_LOCATION;
    }

    protected EntityModel<WitherServant> model() {
        return this.model;
    }
}
