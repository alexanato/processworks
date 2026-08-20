package at.randorf.processworks.processes;

import at.randorf.processworks.common.process.recipe.timed.AmountRecipe;
import at.randorf.processworks.common.process.recipe.timed.AmountRecipeCodecs;
import at.randorf.processworks.registry.SulfurProcessingRegister;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.storage.loot.LootTable;

public class SulfurProcess extends AmountRecipe {
    public static final MapCodec<SulfurProcess> CODEC = AmountRecipeCodecs.codec(SulfurProcess::new);

    public static final StreamCodec<RegistryFriendlyByteBuf, SulfurProcess> STREAM_CODEC = AmountRecipeCodecs.streamCodec(SulfurProcess::new);

    public SulfurProcess(Ingredient ingredient,int time, ResourceKey<LootTable> lootTable ) {
        super(ingredient, time, lootTable);
    }

    @Override
    public RecipeSerializer<SulfurProcess> getSerializer() {
        return SulfurProcessingRegister.SULFUR_PROCESSING_SERIALIZER.get();
    }

    @Override
    public RecipeType<SulfurProcess> getType() {
        return SulfurProcessingRegister.SULFUR_PROCESSING_TYPE.get();
    }
}
