package z1gned.goetyrevelation.entitiy;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static z1gned.goetyrevelation.ModMain.MODID;

public class ModEntityType {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MODID);
    public static final RegistryObject<EntityType<PhantomServant>> PHANTOM_SERVANT = register("phantom_servant", EntityType.Builder.of(PhantomServant::new, MobCategory.MONSTER).sized(0.9F, 0.5F).clientTrackingRange(8));
    public static final RegistryObject<EntityType<WitherServant>> WITHER_SERVANT = register("wither_servant", EntityType.Builder.of(WitherServant::new, MobCategory.MONSTER).sized(0.9F, 3.5F).clientTrackingRange(8));
    public static final RegistryObject<EntityType<SummonApollyon>> SUMMON_APOLLYON = register("summon_apollyon", EntityType.Builder.of(SummonApollyon::new, MobCategory.MISC).fireImmune().sized(2.0F, 0.5F).clientTrackingRange(10).updateInterval(Integer.MAX_VALUE));

    public ModEntityType() {
    }

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String p_20635_, EntityType.Builder<T> p_20636_) {
        return ENTITY_TYPE.register(p_20635_, () -> p_20636_.build(MODID));
    }

}
