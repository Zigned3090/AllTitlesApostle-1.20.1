package z1gned.goetyrevelation.mixin;

import com.Polarice3.Goety.client.render.ApostleRenderer;
import com.Polarice3.Goety.common.entities.boss.Apostle;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import z1gned.goetyrevelation.util.ApollyonAbilityHelper;

import static z1gned.goetyrevelation.ModMain.MODID;

@Mixin(ApostleRenderer.class)
public class ApostleRendererMixin {

    @Unique
    private static final ResourceLocation TEXTURE = new ResourceLocation(MODID, "textures/entity/apollyon/apollyon.png");
    @Unique
    private static final ResourceLocation TEXTURE_2 = new ResourceLocation(MODID, "textures/entity/apollyon/apollyon_second.png");

    @Inject(at = @At("HEAD"), method = "getTextureLocation(Lcom/Polarice3/Goety/common/entities/boss/Apostle;)Lnet/minecraft/resources/ResourceLocation;", cancellable = true, remap = false)
    private void setTexture(Apostle entity, CallbackInfoReturnable<ResourceLocation> cir) {
        if (((ApollyonAbilityHelper) entity).allTitlesApostle_1_20_1$isApollyon()) {
            cir.setReturnValue(entity.isSecondPhase() ? TEXTURE_2 : TEXTURE);
        }
    }

}
