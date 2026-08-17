package at.randorf.processworks.blocks.basket;

import at.randorf.processworks.ModBlocks;
import at.randorf.processworks.block_entitys.basket.WoodenBasketBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

import java.util.function.Supplier;

public class WoodenBasket extends Basket implements EntityBlock, SimpleWaterloggedBlock {
    public static final Supplier<MapCodec<WoodenBasket>> SIMPLE_CODEC = ModBlocks.CODECS.register(
            "wooden_basket.json",
            () -> BlockBehaviour.simpleCodec(WoodenBasket::new)
    );
    public WoodenBasket(Properties properties) {
        super(properties);
    }
    @Override
    public MapCodec<WoodenBasket> codec() {
        return SIMPLE_CODEC.get();
    }
    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new WoodenBasketBlockEntity(blockPos, blockState);
    }
}
