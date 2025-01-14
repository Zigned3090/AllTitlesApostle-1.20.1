package z1gned.goetyrevelation.event;

import com.Polarice3.Goety.common.entities.boss.Apostle;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import z1gned.goetyrevelation.util.ATAHelper;
import z1gned.goetyrevelation.util.ApollyonAbilityHelper;

@Mod.EventBusSubscriber
public class LivingEntityHurtEvent {

    //Goety Revelation 2.0
    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player) {
            //破碎光环减伤下界30%其它维度15%
            if (ATAHelper.hasBrokenHalo(player)) {
                if (!event.getSource().is(DamageTypes.FELL_OUT_OF_WORLD)) {
                    event.setAmount(event.getAmount() * (player.level().dimension() == Level.NETHER ? 0.7F : 0.85F));
                }
            }

            //晋升光环减伤下界50%其它维度25%
            if (ATAHelper.hasHalo(player)) {
                if (!event.getSource().is(DamageTypes.FELL_OUT_OF_WORLD)) {
                    event.setAmount(event.getAmount() * (player.level().dimension() == Level.NETHER ? 0.75F : 0.5F));
                }

                //免疫部分伤害类型
                DamageSource source = event.getSource();
                if (source.is(DamageTypeTags.IS_FIRE) || source.is(DamageTypes.IN_WALL) || source.is(DamageTypeTags.IS_FALL) || source.is(DamageTypes.CRAMMING) || source.is(DamageTypeTags.IS_DROWNING)) {
                    event.setAmount(0.0F);
                }
            }
        }

        //晋升光环佩戴者免疫爆炸伤害
        if (ATAHelper.hasHalo(event.getEntity())) {
            if (event.getSource().is(DamageTypeTags.IS_EXPLOSION)) {
                event.setAmount(0.0F);
            }
        }

        if (event.getEntity() instanceof Apostle apostle && ((ApollyonAbilityHelper) apostle).allTitlesApostle_1_20_1$isApollyon()) ((ApollyonAbilityHelper) apostle).setApollyonTime(30);
    }

}
