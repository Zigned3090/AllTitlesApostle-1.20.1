package z1gned.goetyrevelation.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import z1gned.goetyrevelation.client.ModLayerAndModels;
import z1gned.goetyrevelation.client.layer.WitherServantArmorLayer;
import z1gned.goetyrevelation.client.model.WitherServantModel;
import z1gned.goetyrevelation.entitiy.WitherServant;

public class WitherServantRenderer extends MobRenderer<WitherServant, WitherServantModel<WitherServant>> {
    private static final ResourceLocation WITHER_INVULNERABLE_LOCATION = new ResourceLocation("textures/entity/wither/wither_invulnerable.png");
    private static final ResourceLocation WITHER_LOCATION = new ResourceLocation("textures/entity/wither/wither.png");

    public WitherServantRenderer(EntityRendererProvider.Context p_174445_) {
        super(p_174445_, new WitherServantModel<>(p_174445_.bakeLayer(ModLayerAndModels.WITHER_SERVANT_MODEL)), 1.0F);
        this.addLayer(new WitherServantArmorLayer(this, p_174445_.getModelSet()));
    }

    protected int getBlockLightLevel(WitherServant p_116443_, BlockPos p_116444_) {
        return 15;
    }

    public ResourceLocation getTextureLocation(WitherServant p_116437_) {
        int i = p_116437_.getInvulnerableTicks();
        return i > 0 && (i > 80 || i / 5 % 2 != 1) ? WITHER_INVULNERABLE_LOCATION : WITHER_LOCATION;
    }

    protected void scale(WitherServant p_116439_, PoseStack p_116440_, float p_116441_) {
        float f = 2.0F;
        int i = p_116439_.getInvulnerableTicks();
        if (i > 0) {
            f -= ((float)i - p_116441_) / 220.0F * 0.5F;
        }

        p_116440_.scale(f, f, f);
    }
}
