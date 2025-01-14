package z1gned.goetyrevelation;

import com.Polarice3.Goety.client.render.SummonApostleRenderer;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;
import z1gned.goetyrevelation.client.*;
import z1gned.goetyrevelation.client.event.NetherStarShaders;
import z1gned.goetyrevelation.client.model.PhantomServantModel;
import z1gned.goetyrevelation.client.model.WitherServantModel;
import z1gned.goetyrevelation.client.render.PhantomServantRenderer;
import z1gned.goetyrevelation.client.render.WitherServantRenderer;
import z1gned.goetyrevelation.entitiy.ModEntityType;
import z1gned.goetyrevelation.entitiy.PhantomServant;
import z1gned.goetyrevelation.item.ModItems;
import z1gned.goetyrevelation.tag.ModTags;

@Mod(ModMain.MODID)
public class ModMain {
    public static final String MODID = "goety_revelation";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, "goety_revelation");
    public static final RegistryObject<SoundEvent> APOLLYON_OVERWORLD_THEME = createSound("apollyon_theme_overworld");
    public static final RegistryObject<SoundEvent> APOLLYON_NETHER_THEME = createSound("apollyon_theme_nether");
    public static final RegistryObject<SoundEvent> APOLLYON_THEME_POST = createSound("apollyon_theme_post");

    static RegistryObject<SoundEvent> createSound(String name) {
        SoundEvent event = SoundEvent.createVariableRangeEvent(new ResourceLocation(MODID, name));
        return SOUNDS.register(name, () -> event);
    }

    public ModMain() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModEntityType.ENTITY_TYPE.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        modEventBus.addListener(this::setupEntityAttributeCreation);
        modEventBus.addListener(this::commonSetup);
        ModTags.init();
        SOUNDS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, z1gned.goetyrevelation.config.ModConfig.SPEC, "goety_revelation.toml");
        z1gned.goetyrevelation.config.ModConfig.loadConfig(z1gned.goetyrevelation.config.ModConfig.SPEC, FMLPaths.CONFIGDIR.get().resolve("goety_revelation.toml").toString());
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("Apollyon is Coming!");
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Apollyon is Coming!");
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(ModLayerAndModels.PHANTOM_SERVANT_MODEL, PhantomServantModel::createBodyLayer);
            event.registerLayerDefinition(ModLayerAndModels.WITHER_SERVANT_MODEL, WitherServantModel::createBodyLayer);
        }

        @SubscribeEvent
        public static void onRegisterRenders(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(ModEntityType.PHANTOM_SERVANT.get(), PhantomServantRenderer::new);
            event.registerEntityRenderer(ModEntityType.WITHER_SERVANT.get(), WitherServantRenderer::new);
            event.registerEntityRenderer(ModEntityType.SUMMON_APOLLYON.get(), SummonApostleRenderer::new);
        }

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void onRegisterShaders(RegisterShadersEvent event) {
            NetherStarShaders.onRegisterShaders(event);
        }
    }

    private void setupEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(ModEntityType.PHANTOM_SERVANT.get(), PhantomServant.setCustomAttributes().build());
        event.put(ModEntityType.WITHER_SERVANT.get(), WitherBoss.createAttributes().build());
    }

}
