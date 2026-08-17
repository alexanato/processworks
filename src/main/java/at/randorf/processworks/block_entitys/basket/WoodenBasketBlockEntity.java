package at.randorf.processworks.block_entitys.basket;

import at.randorf.processworks.ModBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;

public class WoodenBasketBlockEntity extends BasketBlockEntity {
    public ResourceHandler<ItemResource> getItemHandler() {
        return getInventory();
    }
    public WoodenBasketBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntity.WOODEN_BASKET.get(), worldPosition, blockState);
    }
}