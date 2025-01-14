package z1gned.goetyrevelation.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class AscensionHalo extends Item implements ICurioItem {
    public AscensionHalo() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC));
    }

    @Override
    public boolean isEnchantable(ItemStack p_41456_) {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack p_41421_, @Nullable Level p_41422_, List<Component> p_41423_, TooltipFlag p_41424_) {
        super.appendHoverText(p_41421_, p_41422_, p_41423_, p_41424_);
        if (Screen.hasShiftDown()) {
            p_41423_.add(Component.translatable("tooltip.goety_revelation.tip1"));
            p_41423_.add(Component.translatable("tooltip.goety_revelation.tip2"));
            p_41423_.add(Component.translatable("tooltip.goety_revelation.tip3"));
            p_41423_.add(Component.translatable("tooltip.goety_revelation.tip4"));
            p_41423_.add(Component.translatable("tooltip.goety_revelation.tip5"));
            p_41423_.add(Component.translatable("tooltip.goety_revelation.tip6"));
            p_41423_.add(Component.translatable("tooltip.goety_revelation.tip7"));
            p_41423_.add(Component.translatable("tooltip.goety_revelation.tip8"));
            p_41423_.add(Component.translatable("tooltip.goety_revelation.tip9"));
            p_41423_.add(Component.translatable("tooltip.goety_revelation.tip10"));
            p_41423_.add(Component.translatable("tooltip.goety_revelation.tip11"));
            p_41423_.add(Component.translatable("tooltip.goety_revelation.tip12"));
        } else if (Screen.hasAltDown()) {
            p_41423_.add(Component.translatable("tooltip.goety_revelation.tip1.ability"));
            p_41423_.add(Component.translatable("tooltip.goety_revelation.tip2.ability"));
            p_41423_.add(Component.translatable("tooltip.goety_revelation.tip3.ability"));
            p_41423_.add(Component.translatable("tooltip.goety_revelation.tip4.ability"));
            p_41423_.add(Component.translatable("tooltip.goety_revelation.tip5.ability"));
            p_41423_.add(Component.translatable("tooltip.goety_revelation.tip6.ability"));
            p_41423_.add(Component.translatable("tooltip.goety_revelation.tip7.ability"));
            p_41423_.add(Component.translatable("tooltip.goety_revelation.tip8.ability"));
            p_41423_.add(Component.translatable("tooltip.goety_revelation.tip9.ability"));
            p_41423_.add(Component.translatable("tooltip.goety_revelation.tip10.ability"));
            p_41423_.add(Component.translatable("tooltip.goety_revelation.tip11.ability"));
            p_41423_.add(Component.translatable("tooltip.goety_revelation.tip12.ability"));
            p_41423_.add(Component.translatable("tooltip.goety_revelation.tip13.ability"));
            p_41423_.add(Component.translatable("tooltip.goety_revelation.tip14.ability"));
            p_41423_.add(Component.translatable("tooltip.goety_revelation.tip15.ability"));
            p_41423_.add(Component.translatable("tooltip.goety_revelation.tip16.ability"));
            p_41423_.add(Component.translatable("tooltip.goety_revelation.tip17.ability"));
            p_41423_.add(Component.translatable("tooltip.goety_revelation.tip18.ability"));
            p_41423_.add(Component.translatable("tooltip.goety_revelation.tip19.ability"));
        } else {
            p_41423_.add(Component.translatable("tooltip.goety_revelation.tip").withStyle(ChatFormatting.GRAY));
            p_41423_.add(Component.translatable("tooltip.goety_revelation.tip0").withStyle(ChatFormatting.GRAY));
        }
    }

}
