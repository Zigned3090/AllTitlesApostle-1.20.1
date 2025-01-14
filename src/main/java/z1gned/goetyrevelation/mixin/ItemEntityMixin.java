package z1gned.goetyrevelation.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import z1gned.goetyrevelation.item.ModItems;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {

    @Shadow public abstract ItemStack getItem();

    @Inject(at = @At("HEAD"), method = "hurt", cancellable = true)
    private void setItemInvul(DamageSource p_32013_, float p_32014_, CallbackInfoReturnable<Boolean> cir) {
        if (this.getItem().is(ModItems.ASCENSION_HALO.get()) || this.getItem().is(ModItems.BROKEN_HALO.get())) {
            cir.setReturnValue(false);
        }
    }

}
