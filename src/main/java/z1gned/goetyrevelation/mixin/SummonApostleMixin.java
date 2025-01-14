package z1gned.goetyrevelation.mixin;

import com.Polarice3.Goety.common.entities.ModEntityType;
import com.Polarice3.Goety.common.entities.boss.Apostle;
import com.Polarice3.Goety.common.entities.util.SummonApostle;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import z1gned.goetyrevelation.config.ModConfig;
import z1gned.goetyrevelation.entitiy.SummonApollyon;
import z1gned.goetyrevelation.util.ApollyonAbilityHelper;

@Mixin(SummonApostle.class)
public abstract class SummonApostleMixin extends Entity {

    public SummonApostleMixin(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"), method = "tick")
    private boolean apollyon(ServerLevel instance, Entity p_8837_) {
        if ((SummonApostle) (Object) this instanceof SummonApollyon) {
            Apostle apostleEntity = new Apostle(ModEntityType.APOSTLE.get(), this.level());
            apostleEntity.setPos(this.getX(), this.getY(), this.getZ());
            ((ApollyonAbilityHelper) apostleEntity).allTitlesApostle_1_20_1$setApollyon(true);
            apostleEntity.setHealth((float) (1.0D * ModConfig.APOLLYON_HEALTH.get()));
            apostleEntity.finalizeSpawn(instance, instance.getCurrentDifficultyAt(this.blockPosition()), MobSpawnType.MOB_SUMMONED, null, null);
            return instance.addFreshEntity(apostleEntity);
        }

        return instance.addFreshEntity(p_8837_);
    }

}
