package at.randorf.processworks.common.inventory.interaction;

import at.randorf.processworks.common.inventory.HasProcessInventory;
import at.randorf.processworks.common.inventory.ProcessInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.List;


public interface PlayerInventoryInteractable {
    default ProcessInventory getInventory(Level level, BlockPos pos){
        if (!(level.getBlockEntity(pos) instanceof HasProcessInventory blockEntity)){
            return null;
        }
        return blockEntity.getInventory();
    }

    default InteractionResult handleInventoryInteraction(ItemStack itemStack, Level level, BlockPos pos, Player player) {
        ProcessInventory inventory = getInventory(level, pos);

        if (inventory == null)return InteractionResult.PASS;

        if (level.isClientSide()) return InteractionResult.SUCCESS;

        if (itemStack.isEmpty()) {
            if (inventory.isEmpty()) {
                return InteractionResult.CONSUME;
            }

            List<ItemStack> extracted = inventory.getItems();
            inventory.clear();

            for (ItemStack item : extracted) {
                if (!player.getInventory().add(item)) {
                    player.drop(item, false);
                }
            }

            return InteractionResult.SUCCESS;
        }

        int inserted = inventory.insertItemStack(itemStack);

        if (inserted <= 0) {
            return InteractionResult.CONSUME;
        }

        if (!player.getAbilities().instabuild) {
            itemStack.shrink(inserted);
        }

        return InteractionResult.SUCCESS;
    }
}
