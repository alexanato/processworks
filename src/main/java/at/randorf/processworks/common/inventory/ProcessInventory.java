package at.randorf.processworks.common.inventory;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.item.ItemResource;

public class ProcessInventory extends MultiProcessInventory {

    public ProcessInventory(int capacity, int buffer, Runnable onChanged) {
        super((capacity + 63) / 64,capacity,1,buffer,onChanged);
    }

    public ProcessInventory(int capacity, int buffer) {
        super((capacity + 63) / 64,capacity,1,buffer);
    }

    public ItemStack getCurrentItem() {
        if (getCurrentItems().isEmpty()) {
            return ItemStack.EMPTY;
        }
        return getCurrentItems().get(0);
    }

    @Override
    public boolean isCurrentItem(ItemStack itemStack) {
        if (getCurrentItem().isEmpty())return true;

        return ItemResource.of(getCurrentItem()).equals(ItemResource.of(itemStack));
    }
}