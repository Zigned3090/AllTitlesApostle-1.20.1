package z1gned.goetyrevelation.mixin;

import com.Polarice3.Goety.client.events.BossBarEvent;
import com.Polarice3.Goety.common.entities.boss.Apostle;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import z1gned.goetyrevelation.ModMain;
import z1gned.goetyrevelation.client.render.ui.NetherStarBar;
import z1gned.goetyrevelation.config.ModConfig;
import z1gned.goetyrevelation.util.ApollyonAbilityHelper;

@Mixin(BossBarEvent.class)
public class BossBarEventMixin {

    @Unique private static ResourceLocation SKY = new ResourceLocation(ModMain.MODID, "textures/ui/nether.png");
    @Unique private static ResourceLocation BAR = new ResourceLocation(ModMain.MODID, "textures/ui/boss_bar.png");
    @Unique private static ResourceLocation BAR2 = new ResourceLocation(ModMain.MODID, "textures/ui/boss_bar2.png");

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Mob;getMaxHealth()F"), method = "drawBar")
    private static float setPercent(Mob instance) {
        if (instance instanceof Apostle apostle && ((ApollyonAbilityHelper) apostle).allTitlesApostle_1_20_1$isApollyon()) {
            return (float) (1.0D * ModConfig.APOLLYON_HEALTH.get());
        }

        return instance.getMaxHealth();
    }

    /*@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V", ordinal = 0, shift = At.Shift.AFTER), method = "drawBar")
    private static void renderNetherStar(GuiGraphics guiGraphics, int pX, int pY, float partialTicks, Mob pEntity, CallbackInfo ci) {
        if (pEntity instanceof Apostle apostle && ((ApollyonInterface) apostle).allTitlesApostle_1_20_1$isApollyon()) {
            NetherStarBar.blitCosmicBar(guiGraphics.pose(), BAR2, pX + 9, pY + 4, 0.0F, 0.0F, getPercent(apostle), 8, 256, 256, false);
        }
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V", ordinal = 1), method = "drawBar")
    private static void close3(GuiGraphics instance, ResourceLocation p_283272_, int p_283605_, int p_281879_, float p_282809_, float p_282942_, int p_281922_, int p_282385_, int p_282596_, int p_281699_) {
        NetherStarBar.blitCosmicBar(instance.pose(), BAR2, p_283605_, p_281879_, p_282809_, p_282942_, p_281922_, p_282385_, p_282596_, p_281699_, false);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V", ordinal = 2), method = "drawBar")
    private static void close2(GuiGraphics instance, ResourceLocation p_283272_, int p_283605_, int p_281879_, float p_282809_, float p_282942_, int p_281922_, int p_282385_, int p_282596_, int p_281699_) {
        NetherStarBar.blitCosmicBar(instance.pose(), BAR2, p_283605_, p_281879_, p_282809_, p_282942_, p_281922_, p_282385_, p_282596_, p_281699_, false);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V", ordinal = 3), method = "drawBar")
    private static void close1(GuiGraphics instance, ResourceLocation p_283272_, int p_283605_, int p_281879_, float p_282809_, float p_282942_, int p_281922_, int p_282385_, int p_282596_, int p_281699_) {
        NetherStarBar.blitCosmicBar(instance.pose(), BAR2, p_283605_, p_281879_, p_282809_, p_282942_, p_281922_, p_282385_, p_282596_, p_281699_, false);
    }*/

    @Inject(at = @At("HEAD"), method = "drawBar", cancellable = true, remap = false)
    private static void renderApollyonBar(GuiGraphics guiGraphics, int pX, int pY, float partialTicks, Mob pEntity, CallbackInfo ci) {
        if (pEntity instanceof Apostle apostle && ((ApollyonAbilityHelper) apostle).allTitlesApostle_1_20_1$isApollyon()) {
            ci.cancel();

            float health = (apostle.getHealth() / apostle.getMaxHealth());
            int i = (int) (health * 182);
            int pX2 = pX + 9;
            int pY2 = pY + 4;

            if (i > 0) {
                guiGraphics.blit(BAR, pX2, pY2, 0, 32, i, 8, 256, 256);
                NetherStarBar.blitCosmicBar(guiGraphics.pose(), BAR, pX2, pY2, 0, 32, i, 8, 256, 256, true, ((ApollyonAbilityHelper) apostle).getApollyonTime());
            }

            guiGraphics.blit(BAR, pX, pY, 0.0F, apostle.isSecondPhase() ? 16.0F : 0.0F, 200, 16, 256, 256);

        }
    }

    @Unique
    private static int getPercent(Apostle apostle) {
        return (int) ((apostle.getHealth() / (1.0D * ModConfig.APOLLYON_HEALTH.get())) * 182);
    }

}
