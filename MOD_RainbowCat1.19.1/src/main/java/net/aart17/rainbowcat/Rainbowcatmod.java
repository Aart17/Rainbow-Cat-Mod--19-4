package net.aart17.rainbowcat;

import com.mojang.logging.LogUtils;
import net.aart17.rainbowcat.block.ModBlocks;
import net.aart17.rainbowcat.item.ModItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Rainbowcatmod.MOD_ID)
public class Rainbowcatmod
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "rainbowcatmod";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    public Rainbowcatmod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItems.register(modEventBus);

        ModBlocks.BLOCKS.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        modEventBus.addListener(this::addCreative);

        MinecraftForge.EVENT_BUS.register(this);
    }
    private void addCreative(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(ModItems.RAINBOWCATB_ITEM);
        }
        if (event.getTab() == CreativeModeTabs.REDSTONE_BLOCKS) {
            event.accept(ModItems.RAINBOW_POWDER);
            event.accept(ModItems.RAINBOWCATB_ITEM);
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
        }
    }
}
