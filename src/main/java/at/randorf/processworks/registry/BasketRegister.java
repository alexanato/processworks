package at.randorf.processworks.registry;

import at.randorf.processworks.content.basket.wooden.WoodenBasket;
import at.randorf.processworks.content.basket.base.BasketFallingEntity;
import at.randorf.processworks.content.basket.wooden.WoodenBasketBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.function.Supplier;

public class BasketRegister {
    public static final DeferredBlock<Block> WOODEN_BASKET_BLOCK = ModRegister.BLOCKS.register("wooden_basket", registryName -> new WoodenBasket(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, registryName)).instabreak().sound(SoundType.CHERRY_WOOD).noOcclusion()));

    public static final Supplier<BlockEntityType<WoodenBasketBlockEntity>> WOODEN_BASKET_BE =ModRegister.BLOCK_ENTITY_TYPES.register("wooden_basket",() -> new BlockEntityType<>(WoodenBasketBlockEntity::new,false,WOODEN_BASKET_BLOCK.get()));

    public static final DeferredHolder<EntityType<?>,EntityType<BasketFallingEntity>> WOODEN_BASKET_ENTITY = ModRegister.ENTITIES.registerEntityType("basket_falling",BasketFallingEntity::new,MobCategory.MISC, builder -> builder.sized(0.98F, 0.98F));

    public static final DeferredItem<BlockItem> WOODEN_BASKET =ModRegister.ITEMS.registerSimpleBlockItem(WOODEN_BASKET_BLOCK);

    public static void init() {
    }

    public static void register(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.Item.BLOCK,WOODEN_BASKET_BE.get(), (blockEntity, side) -> blockEntity.getItemHandler());
    }

    private BasketRegister() {
    }
}
