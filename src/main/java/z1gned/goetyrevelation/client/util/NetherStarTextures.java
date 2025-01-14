package z1gned.goetyrevelation.client.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import z1gned.goetyrevelation.ModMain;

@OnlyIn(Dist.CLIENT)
public class NetherStarTextures {
    public static TextureAtlasSprite[] COSMIC_SPRITES = new TextureAtlasSprite[10];

    public static void init() {
        for (int i = 0; i < COSMIC_SPRITES.length; i++) {
            COSMIC_SPRITES[i] = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(rl(i));
        }
    }

    public static ResourceLocation rl(int i) {
        return rl("item/stars/cosmic_" + i);
    }

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(ModMain.MODID, path);
    }

}
