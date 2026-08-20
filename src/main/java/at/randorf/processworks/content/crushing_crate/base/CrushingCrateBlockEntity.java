package at.randorf.processworks.content.crushing_crate.base;

import at.randorf.processworks.common.inventory.ProcessInventory;
import at.randorf.processworks.common.process.machine.simple.SimpleMachineBlockEntity;
import at.randorf.processworks.common.process.recipe.timed.AmountProcessManager;
import at.randorf.processworks.registry.CrushingRegister;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.transfer.transaction.Transaction;

import java.util.List;

public class CrushingCrateBlockEntity extends SimpleMachineBlockEntity {
    public CrushingCrateBlockEntity(BlockEntityType<?> type, BlockPos worldPosition, BlockState blockState) {
        super(type, worldPosition, blockState);
    }
    public void crush(int damage, Level level,CrushingCrateBlockEntity blockEntity){
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }
        ProcessInventory inventory = getInventory();
        List<ItemStack> drops = AmountProcessManager.process(inventory, damage, serverLevel, Vec3.atLowerCornerOf(blockEntity.getBlockPos()), CrushingRegister.CRUSHING_TYPE.get());
        if (drops == null) return;
        try (Transaction transaction = Transaction.openRoot()) {
            List<ItemStack> overflow = inventory.unsafeInsert(drops, transaction);
            transaction.commit();
        }
    }
}
