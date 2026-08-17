package at.randorf.processworks.blocks.basket;

import at.randorf.processworks.block_entitys.basket.BasketBlockEntity;
import at.randorf.processworks.block_entitys.basket.WoodenBasketBlockEntity;
import at.randorf.processworks.entitys.basket.BasketFallingEntity;
import at.randorf.processworks.inventory.ProcessInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;

public abstract class Basket extends FallingBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public Basket(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        if (state == null) {
            return null;
        }
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return state.setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public int getDustColor(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return 0;
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (FallingBlock.isFree(level.getBlockState(pos.below())) && pos.getY() >= level.getMinY()) {
            CompoundTag blockData = null;
            ProcessInventory inventory = null;
            if (level.getBlockEntity(pos) instanceof BasketBlockEntity basket) {
                blockData = basket.saveCustomOnly(level.registryAccess());
                inventory = basket.getInventory();
                basket.setFalling(true);
            }
            BasketFallingEntity.fall(level, pos, state,  inventory, blockData);
        }
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!(level.getBlockEntity(pos) instanceof BasketBlockEntity basket)) {
            return InteractionResult.PASS;
        }
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }
        if (itemStack.isEmpty()) {
            if (basket.getInventory().isEmpty()) {
                return InteractionResult.CONSUME;
            }
            List<ItemStack> extracted = basket.getInventory().getItems();

            basket.getInventory().clear();

            for (ItemStack item : extracted) {
                if (!player.getInventory().add(item)) {
                    player.drop(item, false);
                }
            }
            level.playSound(null,pos,SoundEvents.ITEM_PICKUP,SoundSource.BLOCKS,1.0F,1.0F);
            return InteractionResult.SUCCESS;
        }
        int inserted = basket.getInventory().insertItemStack(itemStack);

        if (inserted <= 0) {
            return InteractionResult.CONSUME;
        }

        if (!player.getAbilities().instabuild) {
            itemStack.shrink(inserted);
        }
        return InteractionResult.SUCCESS;
    }
}