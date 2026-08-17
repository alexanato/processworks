package at.randorf.processworks.processes.washing;

import at.randorf.processworks.common.inventory.ProcessInventory;
import at.randorf.processworks.processes.washing.recipe.WashingRecipe;
import at.randorf.processworks.processes.washing.recipe.WashingRecipeInput;
import at.randorf.processworks.registry.BasketRegister;
import at.randorf.processworks.registry.WashingRegister;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.transfer.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WashingProcess {
    public static List<ItemStack> getRecipeResult(ProcessInventory inventory, int bubbleTicks, ServerLevel serverLevel, Vec3 position){
        if(!inventory.isProcessable()) return null;
        WashingRecipeInput input = new WashingRecipeInput(inventory.getCurrentItem().getItem(), bubbleTicks);
        Optional<RecipeHolder<WashingRecipe>> recipe = serverLevel.recipeAccess().getRecipeFor(WashingRegister.WASHING_TYPE.get(), input, serverLevel);
        int count = inventory.getItemCount();
        List<ItemStack> drops = new ArrayList<>();
        recipe.ifPresent(holder -> {
            WashingRecipe washingRecipe = holder.value();
            LootParams params = new LootParams.Builder(serverLevel).withParameter(LootContextParams.ORIGIN, position).create(LootContextParamSets.EMPTY);

            LootTable lootTable = serverLevel.getServer().reloadableRegistries().getLootTable(washingRecipe.getLootTable());
            for (int i = 0; i < inventory.getItemCount(); i++) {
                drops.addAll(lootTable.getRandomItems(params)) ;
            }
            inventory.clear();
        });
        return ((drops.isEmpty())?null:drops);
    }
}
