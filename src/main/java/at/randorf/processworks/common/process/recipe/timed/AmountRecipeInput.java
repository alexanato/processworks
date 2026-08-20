package at.randorf.processworks.common.process.recipe.timed;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record AmountRecipeInput(ItemStack item, int time) implements RecipeInput {
    @Override
    public int size() {
        return 1;
    }
    @Override
    public ItemStack getItem(int slot) {
        if (slot != 0) {
            return ItemStack.EMPTY;
        }
        return item;
    }
}