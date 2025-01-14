package z1gned.goetyrevelation.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import z1gned.goetyrevelation.client.ModLayerAndModels;
import z1gned.goetyrevelation.client.layer.PhantomServantEyesLayer;
import z1gned.goetyrevelation.client.model.PhantomServantModel;
import z1gned.goetyrevelation.entitiy.PhantomServant;

@OnlyIn(Dist.CLIENT)
public class PhantomServantRenderer extends MobRenderer<PhantomServant, PhantomServantModel<PhantomServant>> {
    private static final ResourceLocation PHANTOM_LOCATION = new ResourceLocation("textures/entity/phantom.png");

    public PhantomServantRenderer(EntityRendererProvider.Context p_174338_) {
        super(p_174338_, new PhantomServantModel<>(p_174338_.bakeLayer(ModLayerAndModels.PHANTOM_SERVANT_MODEL)), 0.75F);
        this.addLayer(new PhantomServantEyesLayer<>(this));
    }

    public ResourceLocation getTextureLocation(PhantomServant p_115679_) {
        return PHANTOM_LOCATION;
    }

    protected void scale(PhantomServant p_115681_, PoseStack p_115682_, float p_115683_) {
        int i = p_115681_.getPhantomServantSize();
        float f = 1.0F + 0.15F * (float)i;
        p_115682_.scale(f, f, f);
        p_115682_.translate(0.0F, 1.3125F, 0.1875F);
    }

    protected void setupRotations(PhantomServant p_115685_, PoseStack p_115686_, float p_115687_, float p_115688_, float p_115689_) {
        super.setupRotations(p_115685_, p_115686_, p_115687_, p_115688_, p_115689_);
        p_115686_.mulPose(Axis.XP.rotationDegrees(p_115685_.getXRot()));
    }
}
