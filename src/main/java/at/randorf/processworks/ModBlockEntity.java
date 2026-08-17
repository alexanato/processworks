package at.randorf.processworks;

import at.randorf.processworks.block_entitys.basket.WoodenBasketBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntity {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(
                    Registries.BLOCK_ENTITY_TYPE,
                    Processworks.MOD_ID
            );

    public static final Supplier<BlockEntityType<WoodenBasketBlockEntity>> WOODEN_BASKET =
            BLOCK_ENTITY_TYPES.register(
                    "wooden_basket",
                    () -> new BlockEntityType<>(
                            WoodenBasketBlockEntity::new,
                            false,
                            ModBlocks.WOODEN_BASKET.get()
                    )
            );
}