package z1gned.goetyrevelation.mixin;

import com.Polarice3.Goety.client.events.ClientEvents;
import com.Polarice3.Goety.common.entities.boss.Apostle;
import com.Polarice3.Goety.init.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.event.entity.living.LivingEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import z1gned.goetyrevelation.ModMain;
import z1gned.goetyrevelation.util.ApollyonAbilityHelper;

@Mixin(ClientEvents.class)
public abstract class ClientEventsMixin {

    @Shadow
    public static void playBossMusic(SoundEvent soundEvent, Mob mob) {
    }

    @Inject(at = @At(value = "INVOKE", target = "Lcom/Polarice3/Goety/client/events/ClientEvents;playBossMusic(Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/world/entity/Mob;)V", ordinal = 0), method = "onEntityTick", cancellable = true, remap = false)
    private static void playSounds(LivingEvent.LivingTickEvent event, CallbackInfo ci) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof Apostle apostle) {
            ci.cancel();

            if (((ApollyonAbilityHelper) apostle).allTitlesApostle_1_20_1$isApollyon()) {
                if (apostle.isInNether()) {
                    playBossMusic(ModMain.APOLLYON_NETHER_THEME.get(), apostle);
                } else {
                    playBossMusic(ModMain.APOLLYON_OVERWORLD_THEME.get(), apostle);
                }
            } else {
                playBossMusic(ModSounds.APOSTLE_THEME.get(), apostle);
            }
        }
    }

}
