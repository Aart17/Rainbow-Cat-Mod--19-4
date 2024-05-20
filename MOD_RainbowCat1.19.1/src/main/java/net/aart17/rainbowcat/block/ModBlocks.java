package net.aart17.rainbowcat.block;

import net.aart17.rainbowcat.Rainbowcatmod;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Rainbowcatmod.MOD_ID);
    public static final RegistryObject<Block> RAINBOWCATBLOCK =
            BLOCKS.register("rainbowcatblock",
                    RainbowCatBLock::new);
}