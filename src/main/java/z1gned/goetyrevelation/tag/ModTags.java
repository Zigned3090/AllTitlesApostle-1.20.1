package z1gned.goetyrevelation.tag;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModTags {

    public static TagKey<Item> APOLLYON_RITUAL_ITEM = tag("apollyon_ritual_item");

    private static TagKey<Item> tag(String name) {
        return ItemTags.create(new ResourceLocation("goety_revelation", name));
    }

    public static void init() {}

}
