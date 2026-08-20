package at.randorf.processworks.common.process.recipe.timed;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.storage.loot.LootTable;

@FunctionalInterface
public interface AmountRecipeFactory<T extends AmountRecipe> {

    T create( Ingredient ingredient,int time,ResourceKey<LootTable> lootTable);
}