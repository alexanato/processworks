package at.randorf.processworks.block_entitys;

import at.randorf.processworks.ModBlockEntity;
import at.randorf.processworks.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;

public class WoodenBasketBlockEntity extends BasketBlockEntity {
    public ResourceHandler<ItemResource> getItemHandler() {
        return inventory;
    }
    public WoodenBasketBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntity.WOODEN_BASKET.get(), worldPosition, blockState);
    }
}