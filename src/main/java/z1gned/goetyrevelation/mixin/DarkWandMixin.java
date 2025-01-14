package z1gned.goetyrevelation.mixin;

import com.Polarice3.Goety.api.items.magic.IWand;
import com.Polarice3.Goety.api.magic.ISpell;
import com.Polarice3.Goety.common.items.ModItems;
import com.Polarice3.Goety.common.items.magic.DarkWand;
import com.Polarice3.Goety.init.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import z1gned.goetyrevelation.util.ATAHelper;

@Mixin(DarkWand.class)
public abstract class DarkWandMixin implements IWand {

    @Unique
    LivingEntity allTitlesApostle_1_20_1$user;
    @Unique
    ItemStack allTitlesApostle_1_20_1$stack;

    @Inject(at = @At("HEAD"), method = "onUseTick")
    private void getUser(Level worldIn, LivingEntity livingEntityIn, ItemStack stack, int count, CallbackInfo ci) {
        this.allTitlesApostle_1_20_1$user = livingEntityIn;
        this.allTitlesApostle_1_20_1$stack = stack;
    }

    @ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"), index = 4, method = "onUseTick")
    private SoundEvent getSound(SoundEvent p_46547_) {
        ItemStack focus = IWand.getFocus(allTitlesApostle_1_20_1$stack);
        boolean currentFocusMagic = focus.is(ModItems.WITHER_SKULL_FOCUS.get()) || focus.is(ModItems.CYCLONE_FOCUS.get()) || focus.is(ModItems.UPDRAFT_FOCUS.get()) || focus.is(ModItems.LAVABALL_FOCUS.get()) || focus.is(ModItems.FIREBALL_FOCUS.get());
        boolean currentFocusSummon = focus.is(ModItems.BLAZING_FOCUS.get()) || focus.is(ModItems.ROTTING_FOCUS.get()) || focus.is(ModItems.SKULL_FOCUS.get()) || focus.is(ModItems.HAIL_FOCUS.get()) || focus.is(ModItems.GHASTLY_FOCUS.get()) || focus.is(ModItems.BARRICADE_FOCUS.get());
        if (ATAHelper.hasHalo(this.allTitlesApostle_1_20_1$user) && allTitlesApostle_1_20_1$stack.is(ModItems.NETHER_STAFF.get())) {
            if (currentFocusMagic) {
                p_46547_ = ModSounds.APOSTLE_PREPARE_SPELL.get();
            }

            if (currentFocusSummon) {
                p_46547_ = ModSounds.APOSTLE_PREPARE_SUMMON.get();
            }
        }

        return p_46547_;
    }

}
