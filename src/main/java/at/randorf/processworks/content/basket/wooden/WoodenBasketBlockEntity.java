package at.randorf.processworks.content.basket.wooden;

import at.randorf.processworks.content.basket.base.BasketBlockEntity;
import at.randorf.processworks.registry.BasketRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;

public class WoodenBasketBlockEntity extends BasketBlockEntity {
    public ResourceHandler<ItemResource> getItemHandler() {
        return getInventory();
    }
    public WoodenBasketBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(BasketRegister.WOODEN_BASKET_BE.get(), worldPosition, blockState);
    }
}