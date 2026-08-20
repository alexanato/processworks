package at.randorf.processworks.common.inventory;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.transfer.TransferPreconditions;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemUtil;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

import java.util.ArrayList;
import java.util.List;

public class MultiProcessInventory extends ItemStacksResourceHandler {

    private int capacity;
    private final Runnable onChanged;
    private final int BUFFER_SLOTS;

    private final int maxDifferentItems;

    public List<ItemStack> getCurrentItems() {
        List<ItemStack> items = new ArrayList<>();

        for (int i = 0; i < size(); i++) {
            if (getResource(i).isEmpty()) continue;

            ItemStack stack = ItemUtil.getStack(this, i);

            boolean alreadyExists = false;

            for (ItemStack item : items) {
                if (ItemResource.of(item).equals(ItemResource.of(stack))) {
                    alreadyExists = true;
                    break;
                }
            }

            if (!alreadyExists) {
                items.add(stack);
            }
        }

        return items;
    }

    public MultiProcessInventory(int slots, int capacity, int maxDifferentItems, int buffer, Runnable onChanged ) {
        super(slots + buffer);

        BUFFER_SLOTS = buffer;
        this.capacity = capacity;
        this.maxDifferentItems = maxDifferentItems;
        this.onChanged = onChanged;
    }

    public MultiProcessInventory(int slots, int capacity, int maxDifferentItems, int buffer) {
        super(slots + buffer);

        BUFFER_SLOTS = buffer;
        this.capacity = capacity;
        this.maxDifferentItems = maxDifferentItems;
        this.onChanged = () -> {};
    }

    @Override
    protected void onContentsChanged(int index, ItemStack previousContents) {
        super.onContentsChanged(index, previousContents);
        onChanged.run();
    }

    public int insertItemStack(ItemStack itemStack) {
        if (itemStack.isEmpty()) return 0;

        try (Transaction transaction = Transaction.openRoot()) {
            int inserted = insert(ItemResource.of(itemStack),itemStack.getCount(), transaction);

            transaction.commit();

            return inserted;
        }
    }

    public List<ItemStack> getItems() {
        List<ItemStack> itemStacks = new ArrayList<ItemStack>();

        for (int i = 0; i < size(); i++) {
            if (getResource(i).equals(ItemResource.of(ItemStack.EMPTY))) continue;

            itemStacks.add(ItemUtil.getStack(this, i));
        }

        return itemStacks;
    }

    @Override
    public int insert(int index,ItemResource resource,int amount,TransactionContext transaction) {
        if (amount <= 0 || resource.isEmpty()) return 0;

        if (!containsResource(resource)&& getCurrentItems().size() >= maxDifferentItems)return 0;

        int freeSpace = getFreeSpace(resource);

        if (freeSpace <= 0) return 0;

        int allowedAmount = Math.min(amount, freeSpace);

        return super.insert(index,resource,allowedAmount, transaction);
    }

    private boolean containsResource(ItemResource resource) {
        for (ItemStack item : getCurrentItems()) {
            if (ItemResource.of(item).equals(resource)) {
                return true;
            }
        }
        return false;
    }

    private int getFreeSpace(ItemResource resource) {
        if (resource.isEmpty()) return 0;

        int physicalSpace = 0;

        for (int i = 0; i < size(); i++) {
            ItemResource current = getResource(i);

            if (!current.isEmpty() && !current.equals(resource)) continue;
            if (!isValid(i, resource)) continue;

            int slotCapacity = getCapacityAsInt(i, resource);
            int currentAmount = getAmountAsInt(i);

            physicalSpace += Math.max( 0,slotCapacity - currentAmount );
        }

        int logicalSpace = getRemainingCapacity();

        return Math.min(physicalSpace,logicalSpace);
    }

    private int insertOld( ItemResource resource,int amount, TransactionContext transaction ) {
        TransferPreconditions.checkNonEmptyNonNegative(resource, amount);

        int inserted = 0;

        for (int index = 0; index < size(); index++) {
            inserted += super.insert( index,resource,amount - inserted, transaction);

            if (inserted == amount) break;
        }

        return inserted;
    }

    public List<ItemStack> unsafeInsert( List<ItemStack> items, TransactionContext transaction) {
        int amount = 0;
        List<ItemStack> toReturn = new ArrayList<>();

        for (ItemStack item : items) {
            amount = insertOld(ItemResource.of(item),item.count(),transaction);

            if (amount != item.count()) {
                ItemStack itemStack = item.copy();
                itemStack.setCount(item.count() - amount);
                toReturn.add(itemStack);
            }
        }

        return toReturn;
    }

    public boolean isCurrentItem(ItemStack itemStack) {
        if (getCurrentItems().isEmpty()) return true;

        ItemResource resource = ItemResource.of(itemStack);

        for (ItemStack currentItem : getCurrentItems()) {
            if (ItemResource.of(currentItem).equals(resource)) {
                return true;
            }
        }

        return false;
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
        return getCurrentItems().isEmpty();
    }

    public void clear() {
        int a = size();

        for (int i = 0; i < a; i++) {
            set(i, ItemResource.EMPTY, 0);
        }
    }

    public boolean isProcessable() {
        if (isEmpty()) return false;

        return getCurrentItems().size() <= maxDifferentItems;
    }
}