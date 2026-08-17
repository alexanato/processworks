package at.randorf.processworks.registry;

import at.randorf.processworks.processes.washing.recipe.WashingRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.function.Supplier;

public class WashingRegister {
    public static final Supplier<RecipeSerializer<WashingRecipe>> WASHING_SERIALIZER =ModRegister.RECIPE_SERIALIZERS.register("washing", ()-> new RecipeSerializer<>(WashingRecipe.CODEC, WashingRecipe.STREAM_CODEC));
    public static final Supplier<RecipeType<WashingRecipe>> WASHING_TYPE =ModRegister.RECIPE_TYPES.register("washing",RecipeType::simple);
    public static void init() {
    }

    private WashingRegister() {
    }
}
