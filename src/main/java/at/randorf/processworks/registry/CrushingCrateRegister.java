package at.randorf.processworks.registry;

import at.randorf.processworks.content.crushing_crate.base.CrushingCrateBlock;
import at.randorf.processworks.content.crushing_crate.base.CrushingCrateBlockEntity;
import at.randorf.processworks.content.crushing_crate.iron.IronCrushingCrateBlock;
import at.randorf.processworks.content.crushing_crate.iron.IronCrushingCrateBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.function.Supplier;

public class CrushingCrateRegister {
    public static final DeferredBlock<Block> IRON_CRUSHING_CRATE_BLOCK = ModRegister.BLOCKS.register("iron_crushing_crate", registryName -> new IronCrushingCrateBlock(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, registryName)).instabreak().sound(SoundType.CHERRY_WOOD).noOcclusion()));

    public static final DeferredItem<BlockItem> IRON_CRUSHING_CRATE_ITEM =ModRegister.ITEMS.registerSimpleBlockItem(IRON_CRUSHING_CRATE_BLOCK);

    public static final Supplier<BlockEntityType<CrushingCrateBlockEntity>> IRON_CRUSHING_CRATE_BE =ModRegister.BLOCK_ENTITY_TYPES.register("iron_crushing_crate",() -> new BlockEntityType<>(IronCrushingCrateBlockEntity::new,false,IRON_CRUSHING_CRATE_BLOCK.get()));

    public static void init() {
    }
}
