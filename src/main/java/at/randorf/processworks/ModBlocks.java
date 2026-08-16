package at.randorf.processworks;

import at.randorf.processworks.blocks.WoodenBasket;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Processworks.MOD_ID);
    public static final DeferredBlock<Block> WOODEN_BASKET = BLOCKS.register("wooden_basket",registryName -> new WoodenBasket(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, registryName)).instabreak().sound(SoundType.CHERRY_WOOD).noOcclusion()));

    public static final DeferredRegister<MapCodec<? extends Block>> CODECS = DeferredRegister.create(BuiltInRegistries.BLOCK_TYPE, Processworks.MOD_ID);
}
