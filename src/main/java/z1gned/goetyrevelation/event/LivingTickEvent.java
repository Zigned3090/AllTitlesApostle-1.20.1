package z1gned.goetyrevelation.event;

import com.Polarice3.Goety.common.entities.boss.Apostle;
import com.Polarice3.Goety.utils.CuriosFinder;
import com.Polarice3.Goety.utils.LichdomHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import z1gned.goetyrevelation.item.ModItems;
import z1gned.goetyrevelation.util.ApollyonAbilityHelper;

@Mod.EventBusSubscriber
public class LivingTickEvent {
    //Goety Revelation 2.0

    @SubscribeEvent
    public static void onPlayerTicking(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        /*if (ATAHelper.hasHalo(player)) {
            ItemStack stack = CuriosFinder.findCurio(player, ModItems.ASCENSION_HALO.get());
            if (stack.getEnchantmentLevel(Enchantments.BINDING_CURSE) < 1) {
                stack.enchant(Enchantments.BINDING_CURSE, 1);
            }
        }*/

        //玩家在巫妖模式下将会删除佩戴的晋升光环
        if (LichdomHelper.isInLichMode(player)) {
            ItemStack stack = CuriosFinder.findCurio(player, ModItems.ASCENSION_HALO.get());
            stack.shrink(1);
        }
    }

    @SubscribeEvent
    public static void onLivingTicking(LivingEvent.LivingTickEvent event) {
        if (event.getEntity() instanceof Apostle apostle) {
            if (apostle instanceof ApollyonAbilityHelper apollyonAbilityHelper) apollyonAbilityHelper.setApollyonTime(apollyonAbilityHelper.getApollyonTime() - 1);
        }
    }

}
