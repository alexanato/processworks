package at.randorf.processworks.common.process.recipe.timed;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.List;

public abstract class AmountRecipe implements Recipe<AmountRecipeInput> {
    public Ingredient getIngredient() {
        return ingredient;
    }

    public int getTime() {
        return time;
    }

    public ResourceKey<LootTable> getLootTable() {
        return lootTable;
    }

    private final Ingredient ingredient;
    private final int time;
    private final ResourceKey<LootTable> lootTable;

    public AmountRecipe(Ingredient ingredient, int time, ResourceKey<LootTable> lootTable ){
        this.ingredient = ingredient;
        this.time = time;
        this.lootTable = lootTable;
    }

    @Override
    public boolean matches(AmountRecipeInput input, Level level) {
        return ingredient.test(input.item())&& input.time() >= time;
    }
    @Override
    public ItemStack assemble(AmountRecipeInput washerRecipeInput) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public String group() {
        return "";
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public List<RecipeDisplay> display() {
        return Recipe.super.display();
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }
}

