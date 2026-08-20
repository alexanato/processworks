package at.randorf.processworks.content.crushing_crate.iron;

import at.randorf.processworks.content.crushing_crate.base.CrushingCrateBlockEntity;
import at.randorf.processworks.registry.CrushingCrateRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class IronCrushingCrateBlockEntity extends CrushingCrateBlockEntity {
    public IronCrushingCrateBlockEntity(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState) {
        super(type, worldPosition, blockState);
    }
    public IronCrushingCrateBlockEntity(BlockPos worldPosition, BlockState blockState){
        super(CrushingCrateRegister.IRON_CRUSHING_CRATE_BE.get(), worldPosition,blockState);
    }
}
