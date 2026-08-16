package at.randorf.processworks.entitys;

import at.randorf.processworks.ModBlockEntity;
import at.randorf.processworks.ModEntities;
import at.randorf.processworks.block_entitys.WoodenBasketBlockEntityRenderer;
import at.randorf.processworks.mixin.entitys.FallingBlockEntityAccessor;
import net.minecraft.client.renderer.entity.FallingBlockRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import org.jspecify.annotations.Nullable;

public class BasketFallingEntity extends FallingBlockEntity {

    private static final EntityDataAccessor<ItemStack> DATA_STORED_STACK = SynchedEntityData.defineId(BasketFallingEntity.class,EntityDataSerializers.ITEM_STACK);

    public BasketFallingEntity( EntityType<? extends FallingBlockEntity> type,Level level) {
        super(type, level);
    }
    private BasketFallingEntity(
            ServerLevel level,
            double x,
            double y,
            double z,
            BlockState state,
            ItemStack storedStack,
            @Nullable CompoundTag blockData
    ) {
        this(ModEntities.BASKET_FALLING.get(), level);

        ((FallingBlockEntityAccessor) (Object) this).processworks$setBlockState(state);
        this.blocksBuilding = true;
        this.setPos(x, y, z);
        this.setDeltaMovement(Vec3.ZERO);
        this.xo = x;
        this.yo = y;
        this.zo = z;
        this.setStartPos(this.blockPosition());
        this.setStoredStack(storedStack);

        this.blockData =blockData == null? null : blockData.copy();
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
        this.entityData.set(DATA_STORED_STACK,stack.copy());
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);

        ItemStack stack = getStoredStack();

        if (!stack.isEmpty()) {
            output.store("StoredStack",ItemStack.CODEC,stack);
        }
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        setStoredStack(input.read("StoredStack",ItemStack.CODEC).orElse(ItemStack.EMPTY));
    }
    public static BasketFallingEntity fall(ServerLevel level,BlockPos pos, BlockState state,ItemStack storedStack, @Nullable CompoundTag blockData) {
        BlockState fallingState = state;

        if (state.hasProperty(BlockStateProperties.WATERLOGGED)) {
            fallingState = state.setValue(BlockStateProperties.WATERLOGGED,false);
        }

        BasketFallingEntity entity =new BasketFallingEntity(level,pos.getX() + 0.5D,pos.getY(),pos.getZ() + 0.5D,fallingState,storedStack, blockData);

        level.setBlock(pos,state.getFluidState().createLegacyBlock(),3);
        level.addFreshEntity(entity);

        return entity;
    }

}