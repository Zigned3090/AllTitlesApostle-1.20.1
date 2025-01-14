package z1gned.goetyrevelation.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import z1gned.goetyrevelation.util.ATAHelper;

@Mixin(Entity.class)
public class EntityMixin {

    @Inject(at = @At("HEAD"), method = "isOnFire", cancellable = true)
    private void canFire(CallbackInfoReturnable<Boolean> cir) {
        if ((Entity) (Object) this instanceof Player player) {
            if (ATAHelper.hasHalo(player)) {
                cir.setReturnValue(false);
            }
        }
    }

}
