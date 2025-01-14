package z1gned.goetyrevelation.mixin;

import com.Polarice3.Goety.common.entities.projectiles.AbstractBeam;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBeam.class)
public class AbstractBeamMixin {

    @Inject(at = @At("RETURN"), method = "getBeamWidth", cancellable = true, remap = false)
    private void setWidth(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(0.25F);
    }

}
