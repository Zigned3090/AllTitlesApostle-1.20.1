package z1gned.goetyrevelation.mixin;

import com.Polarice3.Goety.common.entities.boss.Apostle;
import com.Polarice3.Goety.common.entities.projectiles.DeathArrow;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import z1gned.goetyrevelation.util.ApollyonAbilityHelper;
import z1gned.goetyrevelation.util.ArrowUtil;

@Mixin(DeathArrow.class)
public abstract class DeathArrowMixin extends Arrow implements ArrowUtil {
    @Unique
    private boolean allTitleApostle$isExplosive;

    public DeathArrowMixin(EntityType<? extends Arrow> p_36858_, Level p_36859_) {
        super(p_36858_, p_36859_);
    }

    @Inject(at = @At("HEAD"), method = "onHit")
    private void createExplosion(HitResult p_37260_, CallbackInfo ci) {
        if (p_37260_ instanceof BlockHitResult) {
            if (this.allTitleApostle$isExplosive) {
                this.level().explode(this.getOwner() == null ? this : this.getOwner(), this.getX(), this.getY(), this.getZ(), 3.0F, Level.ExplosionInteraction.NONE);
                this.discard();
            }

            if (this.getOwner() != null) {
                if (this.getOwner() instanceof Apostle apostle) {
                    int i = ((ApollyonAbilityHelper) apostle).allTitleApostle$getTitleNumber();
                    if (((ApollyonAbilityHelper) apostle).allTitlesApostle_1_20_1$isApollyon() && apostle.isInNether() && (i == 2 || i == 12)) {
                        if (this.random.nextInt(5) == 0) {
                            AreaEffectCloud areaEffectCloud = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
                            areaEffectCloud.setDuration(240);
                            areaEffectCloud.addEffect(new MobEffectInstance(MobEffects.POISON, 20 * 4, 1));
                            areaEffectCloud.setOwner(apostle);
                            this.level().addFreshEntity(areaEffectCloud);
                            this.discard();
                        }
                    }
                }
            }
        }
    }

    @Override
    public void allTitleApostle$setExplosive(boolean explosive) {
        allTitleApostle$isExplosive = explosive;
    }

    @Override
    public boolean allTitleApostle$getExplosive() {
        return this.allTitleApostle$isExplosive;
    }

}
