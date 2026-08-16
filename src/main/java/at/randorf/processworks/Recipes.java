package at.randorf.processworks;

import at.randorf.processworks.processes.washing.recipe.WashingRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class Recipes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =DeferredRegister.create(Registries.RECIPE_TYPE, Processworks.MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, Processworks.MOD_ID);

    public static final Supplier<RecipeSerializer<WashingRecipe>> WASHING_SERIALIZER =RECIPE_SERIALIZERS.register("washing", ()-> new RecipeSerializer<>(WashingRecipe.CODEC, WashingRecipe.STREAM_CODEC));
    public static final Supplier<RecipeType<WashingRecipe>> WASHING_TYPE =RECIPE_TYPES.register("washing",RecipeType::simple);
}
