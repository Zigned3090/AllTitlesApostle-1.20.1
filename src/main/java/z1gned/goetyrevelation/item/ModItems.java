package z1gned.goetyrevelation.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import z1gned.goetyrevelation.ModMain;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModMain.MODID);
    public static final RegistryObject<Item> ASCENSION_HALO = ITEMS.register("ascension_halo", AscensionHalo::new);
    public static final RegistryObject<Item> BROKEN_HALO = ITEMS.register("broken_halo", BrokenAscensionHalo::new);
    public static final RegistryObject<Item> DOOM_ICON = ITEMS.register("doom_icon", DoomIcon::new);
    public static final RegistryObject<Item> DOOM_MEDAL = ITEMS.register("doom_medal", DoomIcon::new);

    public static final RegistryObject<Item> WITHER_QUIETUS = ITEMS.register("wither_quietus_focus", ModFocusItem::new);
}
