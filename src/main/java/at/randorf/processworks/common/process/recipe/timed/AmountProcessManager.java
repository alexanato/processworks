package at.randorf.processworks.common.process.recipe.timed;

import at.randorf.processworks.common.inventory.ProcessInventory;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AmountProcessManager {
    public static <R extends AmountRecipe> List<ItemStack> process(ProcessInventory inventory, int ticks, ServerLevel level, Vec3 position, RecipeType<R> recipeType) {
        if(!inventory.isProcessable()) return null;
        AmountRecipeInput input = new AmountRecipeInput(inventory.getCurrentItem(), ticks);
        Optional<RecipeHolder<R>> recipe = level.recipeAccess().getRecipeFor(recipeType, input, level);
        List<ItemStack> drops = new ArrayList<>();
        recipe.ifPresent(holder -> {
            R timedRecipe = holder.value();
            LootParams params = new LootParams.Builder(level).withParameter(LootContextParams.ORIGIN, position).create(LootContextParamSets.EMPTY);

            LootTable lootTable = level.getServer().reloadableRegistries().getLootTable(timedRecipe.getLootTable());
            for (int i = 0; i < inventory.getItemCount(); i++) {
                drops.addAll(lootTable.getRandomItems(params)) ;
            }
            inventory.clear();
        });
        return ((drops.isEmpty())?null:drops);
    }
}
