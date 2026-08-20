package at.randorf.processworks.content.crushing_crate.base;

import at.randorf.processworks.common.process.machine.simple.SimpleMachineBlock;
import at.randorf.processworks.content.crushing_crate.iron.IronCrushingCrateBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public class CrushingCrateBlock extends SimpleMachineBlock {
    public CrushingCrateBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new IronCrushingCrateBlockEntity(blockPos,blockState);
    }
}
