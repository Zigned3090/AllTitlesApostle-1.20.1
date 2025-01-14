package z1gned.goetyrevelation.client;

import com.google.common.collect.Sets;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Set;

import static z1gned.goetyrevelation.ModMain.MODID;

@OnlyIn(Dist.CLIENT)
public class ModLayerAndModels {
    private static final Set<ModelLayerLocation> ALL_MODELS = Sets.newHashSet();
    public static final ModelLayerLocation PHANTOM_SERVANT_MODEL = register("phantom_servant");
    public static final ModelLayerLocation WITHER_SERVANT_MODEL = register("wither_servant");
    public static final ModelLayerLocation PLAYER_HEAD_MODEL = register("player", "headlayer");

    public static void ModModelLayer() {
    }

    private static ModelLayerLocation register(String p_171294_) {
        return register(p_171294_, "main");
    }

    private static ModelLayerLocation register(String p_171296_, String p_171297_) {
        ModelLayerLocation modellayerlocation = createLocation(p_171296_, p_171297_);
        if (!ALL_MODELS.add(modellayerlocation)) {
            throw new IllegalStateException("Duplicate registration for " + modellayerlocation);
        } else {
            return modellayerlocation;
        }
    }

    private static ModelLayerLocation createLocation(String p_171301_, String p_171302_) {
        return new ModelLayerLocation(new ResourceLocation(MODID, p_171301_), p_171302_);
    }

}
