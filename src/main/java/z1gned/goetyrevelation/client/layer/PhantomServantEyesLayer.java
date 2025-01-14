package z1gned.goetyrevelation.client.layer;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import z1gned.goetyrevelation.client.model.PhantomServantModel;
import z1gned.goetyrevelation.entitiy.PhantomServant;

@OnlyIn(Dist.CLIENT)
public class PhantomServantEyesLayer<T extends PhantomServant> extends EyesLayer<T, PhantomServantModel<T>> {
    private static final RenderType PHANTOM_EYES = RenderType.eyes(new ResourceLocation("textures/entity/phantom_eyes.png"));

    public PhantomServantEyesLayer(RenderLayerParent<T, PhantomServantModel<T>> p_117342_) {
        super(p_117342_);
    }

    public RenderType renderType() {
        return PHANTOM_EYES;
    }
}
