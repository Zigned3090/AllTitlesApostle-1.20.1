package z1gned.goetyrevelation.event;

import com.Polarice3.Goety.common.entities.boss.Apostle;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import z1gned.goetyrevelation.data.DefeatApollyonInNetherState;
import z1gned.goetyrevelation.item.ModItems;
import z1gned.goetyrevelation.util.ApollyonAbilityHelper;

@Mod.EventBusSubscriber
public class LivingDeathListener {

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Apostle apostle) {
            if (((ApollyonAbilityHelper) apostle).allTitlesApostle_1_20_1$isApollyon() && apostle.isInNether()) {

                ItemEntity medal = new ItemEntity(entity.level(), apostle.getX(), apostle.getY(), apostle.getZ(), new ItemStack(ModItems.DOOM_MEDAL.get()));

                ServerLevel world = (ServerLevel) entity.level();
                world.addFreshEntity(medal);
                DefeatApollyonInNetherState state = world.getDataStorage().computeIfAbsent(DefeatApollyonInNetherState::createNbt, DefeatApollyonInNetherState::new, "killed_apollyon_in_nether");
                if (!state.isDropped()) {
                    state.setDropped(true);
                    ItemEntity item = new ItemEntity(world, apostle.getX(), apostle.getY(), apostle.getZ(), new ItemStack(ModItems.WITHER_QUIETUS.get()));
                    world.addFreshEntity(item);
                    state.setDirty();
                }
            }
        }
    }
}
