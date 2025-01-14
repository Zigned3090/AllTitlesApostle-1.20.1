package z1gned.goetyrevelation.mixin;

import com.Polarice3.Goety.common.blocks.entities.DarkAltarBlockEntity;
import com.Polarice3.Goety.common.entities.util.SummonApostle;
import com.Polarice3.Goety.common.ritual.SummonRitual;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import z1gned.goetyrevelation.config.ModConfig;
import z1gned.goetyrevelation.entitiy.ModEntityType;
import z1gned.goetyrevelation.entitiy.SummonApollyon;

@Mixin(SummonRitual.class)
public class SummonRitualMixin {
    @Unique
    Level allTitlesApostle_1_20_1$level;
    @Unique
    BlockPos allTitlesApostle_1_20_1$blockPos;

    @Inject(at = @At("HEAD"), method = "finish", remap = false)
    private void getWorld(Level world, BlockPos blockPos, DarkAltarBlockEntity tileEntity, Player castingPlayer, ItemStack activationItem, CallbackInfo ci) {
        this.allTitlesApostle_1_20_1$level = world;
        this.allTitlesApostle_1_20_1$blockPos = blockPos;
    }

    @ModifyArg(at = @At(value = "INVOKE", target = "Lcom/Polarice3/Goety/common/ritual/SummonRitual;spawnEntity(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/level/Level;)V", ordinal = 1), index = 1, method = "finish", remap = false)
    private Entity summonApollyonRandom(Entity entity) {
        if (!(entity instanceof LivingEntity)) {
            if (entity instanceof SummonApostle) {
                if (this.allTitlesApostle_1_20_1$level.random.nextInt(ModConfig.SUMMON_APOLLYON_RANDOM_VALUE.get()) == 0) {
                    SummonApollyon summonApollyon = new SummonApollyon(ModEntityType.SUMMON_APOLLYON.get(), this.allTitlesApostle_1_20_1$level);
                    summonApollyon.absMoveTo((float) allTitlesApostle_1_20_1$blockPos.getX() + 0.5F, (float) allTitlesApostle_1_20_1$blockPos.getY() + 0.5F, (float) allTitlesApostle_1_20_1$blockPos.getZ() + 0.5F, (float) this.allTitlesApostle_1_20_1$level.random.nextInt(360), 0.0F);
                    entity = summonApollyon;
                }
            }
        }

        return entity;
    }

}
