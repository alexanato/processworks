package at.randorf.processworks.common.process.machine.simple;

import at.randorf.processworks.common.block.WaterloggableBlock;
import at.randorf.processworks.common.inventory.ProcessInventory;
import at.randorf.processworks.common.inventory.interaction.PlayerInventoryInteractable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

public abstract class SimpleMachineBlock extends Block implements WaterloggableBlock, PlayerInventoryInteractable, EntityBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public SimpleMachineBlock(Properties properties) {
        super(properties);
        registerDefaultState(getDefaultWaterloggedState(defaultBlockState()));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);

        if (state == null) return null;

        return applyWaterloggedState(state, context);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        addWaterloggedProperty(builder);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return getWaterloggedFluidState(state, super.getFluidState(state));
    }
    @Override
    public InteractionResult useItemOn(ItemStack itemStack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        return handleInventoryInteraction(itemStack,level,pos,player);
    }
}
