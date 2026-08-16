package at.randorf.processworks.inventory;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemUtil;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

import java.util.ArrayList;
import java.util.List;

public class ProcessInventory extends ItemStacksResourceHandler {
    private int capacity;

    public ItemStack getCurrentItem() {
        for (int i = 0; i < size(); i++) {
            if (!getResource(i).isEmpty()) {
                return ItemUtil.getStack(this, i);
            }
        }
        return ItemStack.EMPTY;
    }

    public ProcessInventory(int capacity) {
        super(capacity);
        this.capacity = capacity;
    }

    public ProcessInventory(int capacity, int maxSlots) {
        super(maxSlots);
        this.capacity = capacity;
    }

    public int insertItemStack(ItemStack itemStack) {
        if (itemStack.isEmpty() || isFull()) return 0;
        ItemResource resource = ItemResource.of(itemStack);
        if (!getCurrentItem().isEmpty() && !ItemResource.of(getCurrentItem()).equals(resource)) return 0;

        int amount = Math.min(itemStack.count(), capacity - getItemCount());

        if (amount <= 0) return 0;
        int inserted;
        try (Transaction transaction = Transaction.openRoot()) {
            inserted = insert(resource, amount, transaction);

            if (inserted <= 0) {
                return 0;
            }
            transaction.commit();
        }
        return inserted;
    }

    public List<ItemStack> getItems() {
        List<ItemStack> itemStacks = new ArrayList<ItemStack>();
        for (int i = 0; i < size(); i++) {
            if (getResource(i).equals(ItemResource.of(ItemStack.EMPTY))) continue;
            itemStacks.add(ItemUtil.getStack(this, i));
        }
        return itemStacks;
    }

    public List<ItemStack> unsafeInsert(List<ItemStack> items, TransactionContext transaction) {
        int amount = 0;
        List<ItemStack> toReturn = new ArrayList<>();
        for (ItemStack item : items) {
            amount = insert(ItemResource.of(item), item.count(), transaction);
            if (amount != item.count()) {
                ItemStack itemStack = item.copy();
                itemStack.setCount(item.count() - amount);
                toReturn.add(itemStack);
            }
        }
        return toReturn;
    }

    public boolean isCurrentItem(ItemStack itemStack) {
        if (getCurrentItem().isEmpty()) return true;
        return ItemResource.of(getCurrentItem()).equals(ItemResource.of(itemStack));
    }

    public int getItemCount() {
        int count = 0;
        for (int i = 0; i < size(); i++) {
            count += getAmountAsInt(i);
        }
        return count;
    }

    public int getRemainingCapacity() {
        return capacity - getItemCount();
    }

    public boolean isFull() {
        return getRemainingCapacity() <= 0;
    }

    public boolean isEmpty() {
        return getCurrentItem().isEmpty();
    }

    public void clear() {
        int a = size();
        for (int i = 0; i < a; i++) {
            set(i, ItemResource.EMPTY, 0);
        }
    }
}
