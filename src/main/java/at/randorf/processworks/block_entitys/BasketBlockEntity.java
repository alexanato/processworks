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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.item.ItemStackResourceHandler;

public class BasketBlockEntity extends BlockEntity {
    private ItemStack storedStack = ItemStack.EMPTY;
    public final ItemStackResourceHandler inventory = new ItemStackResourceHandler() {
        @Override
        protected ItemStack getStack() {
            return storedStack;
        }

        @Override
        protected void setStack(ItemStack stack) {
            storedStack = stack;

            BasketBlockEntity.this.setChanged();

            if (level != null && !level.isClientSide()) {
                BlockState state = getBlockState();

                level.sendBlockUpdated(
                        getBlockPos(),
                        state,
                        state,
                        Block.UPDATE_CLIENTS
                );
            }
        }
        @Override
        protected void onRootCommit(ItemStack originalState) {
            BasketBlockEntity.this.setChanged();
        }
    };
    private boolean falling = false;

    public BasketBlockEntity(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState) {
        super(type, worldPosition, blockState);
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }
    public  ItemStack getStoredStack(){
        return storedStack;
    }
    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        super.preRemoveSideEffects(pos, state);
        if (level == null || level.isClientSide()) {
            return;
        }
        level.updateNeighbourForOutputSignal(pos, this.getBlockState().getBlock());
        if (falling) {
            return;
        }
        if (!storedStack.isEmpty()) {
            Block.popResource(level, pos,storedStack.copy() );
            storedStack = ItemStack.EMPTY;
        }
        Block.popResource(level,pos, new ItemStack(getBlockState().getBlock().asItem()));
    }
    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);

        inventory.serialize(output.child("inventory"));
    }

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        inventory.deserialize(input.childOrEmpty("inventory"));
    }
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveWithoutMetadata(registries);
    }
}
