package at.randorf.processworks.processes.washing.recipe;

import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.block.state.BlockState;

public record WashingRecipeInput(Item item, int time) implements RecipeInput {
    @Override
    public int size() {
        return 1;
    }
    @Override
    public ItemStack getItem(int slot) {
        if (slot != 0) {
            return ItemStack.EMPTY;
        }
        return item.getDefaultInstance();
    }
}