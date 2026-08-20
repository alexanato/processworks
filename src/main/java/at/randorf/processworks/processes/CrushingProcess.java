package at.randorf.processworks.processes;

import at.randorf.processworks.common.process.recipe.timed.AmountRecipe;
import at.randorf.processworks.common.process.recipe.timed.AmountRecipeCodecs;
import at.randorf.processworks.common.process.recipe.timed.AmountRecipeInput;
import at.randorf.processworks.registry.CrushingRegister;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.storage.loot.LootTable;

public class CrushingProcess extends AmountRecipe {
    public static final MapCodec<CrushingProcess> CODEC = AmountRecipeCodecs.codec(CrushingProcess::new);

    public static final StreamCodec<RegistryFriendlyByteBuf, CrushingProcess> STREAM_CODEC = AmountRecipeCodecs.streamCodec(CrushingProcess::new);

    public CrushingProcess(Ingredient ingredient, int time, ResourceKey<LootTable> lootTable) {
        super(ingredient, time, lootTable);
    }

    @Override
    public RecipeSerializer<? extends Recipe<AmountRecipeInput>> getSerializer() {
        return CrushingRegister.CRUSHING_SERIALIZER.get();
    }

    @Override
    public RecipeType<? extends Recipe<AmountRecipeInput>> getType() {
        return CrushingRegister.CRUSHING_TYPE.get();
    }
}
