package net.aart17.rainbowcat.item;

import net.aart17.rainbowcat.Rainbowcatmod;
import net.aart17.rainbowcat.block.ModBlocks;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
                DeferredRegister.create(ForgeRegistries.ITEMS, Rainbowcatmod.MOD_ID);

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
    public static final RegistryObject<Item> RAINBOW_POWDER = ITEMS.register("rainbow_powder",
            ()-> new Item(new Item.Properties()));

    public  static final RegistryObject<Item> RAINBOWCATB_ITEM =
            ITEMS.register("rainbowcatblock",
                    ()-> new BlockItem(ModBlocks.RAINBOWCATBLOCK.get(),
                    new Item.Properties()));
}
