package at.randorf.processworks.content.basket.base;

import at.randorf.processworks.common.inventory.ProcessInventory;
import at.randorf.processworks.common.process.recipe.timed.AmountProcessManager;
import at.randorf.processworks.mixin.entitys.FallingBlockEntityAccessor;
import at.randorf.processworks.registry.BasketRegister;
import at.randorf.processworks.registry.SulfurProcessingRegister;
import at.randorf.processworks.registry.WashingRegister;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ProblemReporter;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jspecify.annotations.Nullable;

import java.util.List;


public class BasketFallingEntity extends FallingBlockEntity {
    private ProcessInventory inventory = new ProcessInventory(128, 7);

    private static final EntityDataAccessor<ItemStack> DATA_STORED_STACK = SynchedEntityData.defineId(BasketFallingEntity.class, EntityDataSerializers.ITEM_STACK);

    private int bubbleTicks = 0;
    private int sulfurTicks = 0;

    public BasketFallingEntity(EntityType<? extends FallingBlockEntity> type, Level level) {
        super(type, level);
    }

    private BasketFallingEntity(ServerLevel level, double x, double y, double z, BlockState state, ProcessInventory inventory, @Nullable CompoundTag blockData) {
        this(BasketRegister.WOODEN_BASKET_ENTITY.get(), level);

        ((FallingBlockEntityAccessor) (Object) this).processworks$setBlockState(state);
        this.blocksBuilding = true;
        this.setPos(x, y, z);
        this.setDeltaMovement(Vec3.ZERO);
        this.xo = x;
        this.yo = y;
        this.zo = z;
        this.setStartPos(this.blockPosition());
        this.inventory = inventory;
        this.setStoredStack(inventory.getCurrentItem());

        this.blockData = blockData == null ? null : blockData.copy();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_STORED_STACK, ItemStack.EMPTY);
    }

    public ItemStack getStoredStack() {
        return this.entityData.get(DATA_STORED_STACK);
    }

    public void setStoredStack(ItemStack stack) {
        ItemStack copy = stack.copy();

        entityData.set(DATA_STORED_STACK, copy);
    }

    private void updateBlockData() {
        if (!(level() instanceof ServerLevel serverLevel)) {
            return;
        }
        TagValueOutput output = TagValueOutput.createWithContext(
                ProblemReporter.DISCARDING,
                serverLevel.registryAccess()
        );
        inventory.serialize(output.child("inventory"));

        this.blockData = output.buildResult();

        setStoredStack(inventory.getCurrentItem());
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.store("BubbleTicks", Codec.INT, bubbleTicks);
        output.store("SulfurTicks", Codec.INT, sulfurTicks);
        inventory.serialize(output.child("ProcessInventory"));

        ItemStack stack = getStoredStack();
        if (!stack.isEmpty()) {
            output.store("StoredStack", ItemStack.CODEC, stack);
        }
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        inventory.deserialize(input.childOrEmpty("ProcessInventory"));
        bubbleTicks = input.read("BubbleTicks", Codec.INT).orElse(0);
        sulfurTicks = input.read("SulfurTicks", Codec.INT).orElse(0);
        setStoredStack(inventory.getCurrentItem());
    }

    public static BasketFallingEntity fall(ServerLevel level, BlockPos pos, BlockState state, ProcessInventory inventory, @Nullable CompoundTag blockData) {
        BlockState fallingState = state;

        if (state.hasProperty(BlockStateProperties.WATERLOGGED)) {
            fallingState = state.setValue(BlockStateProperties.WATERLOGGED, false);
        }

        BasketFallingEntity entity = new BasketFallingEntity(level, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, fallingState, inventory, blockData);

        level.setBlock(pos, state.getFluidState().createLegacyBlock(), 3);
        level.addFreshEntity(entity);

        return entity;
    }

    @Override
    public void tick() {
        super.tick();
        if (!(level() instanceof ServerLevel serverLevel)) {
            return;
        }
        time = 0;
        BlockPos pos = blockPosition();
        if (level().getBlockState(pos).is(Blocks.BUBBLE_COLUMN)) {
            bubbleTicks++;
            handleWashing(serverLevel);
        } else {
            bubbleTicks = 0;
        }
        if(!wasLastSulfur) sulfurTicks = 0;
        else  wasLastSulfur = false;
    }

    private boolean wasLastSulfur = false;

    public void handleSulfurProcessing() {
        if (!(level() instanceof ServerLevel serverLevel)) {
            return;
        }
        sulfurTicks++;
        wasLastSulfur = true;
        List<ItemStack> drops = AmountProcessManager.process(inventory, sulfurTicks, serverLevel, position(), SulfurProcessingRegister.SULFUR_PROCESSING_TYPE.get());
        if (drops == null) return;
        try (Transaction transaction = Transaction.openRoot()) {
            List<ItemStack> overflow = inventory.unsafeInsert(drops, transaction);
            for (int i = 0; i < overflow.size(); i++) {
                spawnAtLocation(serverLevel, overflow.get(i));
            }
            transaction.commit();
        }
        sulfurTicks = 0;
        updateBlockData();
    }

    private void handleWashing(ServerLevel serverLevel) {
        List<ItemStack> drops = AmountProcessManager.process(inventory, bubbleTicks, serverLevel, position(), WashingRegister.WASHING_TYPE.get());
        if (drops == null) return;
        try (Transaction transaction = Transaction.openRoot()) {
            List<ItemStack> overflow = inventory.unsafeInsert(drops, transaction);
            for (int i = 0; i < overflow.size(); i++) {
                spawnAtLocation(serverLevel, overflow.get(i));
            }
            transaction.commit();
        }
        bubbleTicks = 0;
        updateBlockData();
    }
}