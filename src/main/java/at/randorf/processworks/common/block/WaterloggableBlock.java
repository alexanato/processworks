package at.randorf.processworks.common.block;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public interface WaterloggableBlock {
    BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    default BlockState getDefaultWaterloggedState(BlockState state) {
        return state.setValue(WATERLOGGED, false);
    }

    default BlockState applyWaterloggedState(BlockState state,BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());

        return state.setValue( WATERLOGGED,fluidState.getType() == Fluids.WATER);
    }

    default void addWaterloggedProperty( StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    default FluidState getWaterloggedFluidState(BlockState state,FluidState fallback) {
        if (state.getValue(WATERLOGGED)) {
            return Fluids.WATER.getSource(false);
        }
        return fallback;
    }
}
