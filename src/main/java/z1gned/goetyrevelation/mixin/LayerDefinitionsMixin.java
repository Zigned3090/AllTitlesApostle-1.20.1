package z1gned.goetyrevelation.mixin;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import z1gned.goetyrevelation.client.model.PlayerHaloModel;
import z1gned.goetyrevelation.client.ModLayerAndModels;

@Mixin(LayerDefinitions.class)
public class LayerDefinitionsMixin {
    @Redirect(at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableMap;builder()Lcom/google/common/collect/ImmutableMap$Builder;"), method = "createRoots")
    private static ImmutableMap.Builder<ModelLayerLocation, LayerDefinition> addRoots() {
        ImmutableMap.Builder<ModelLayerLocation, LayerDefinition> builder = ImmutableMap.builder();
        builder.put(ModLayerAndModels.PLAYER_HEAD_MODEL, PlayerHaloModel.createBodyLayer(new CubeDeformation(0.0F)));
        return builder;
    }

}
