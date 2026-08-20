package at.randorf.processworks.registry;

import at.randorf.processworks.processes.WashingProcess;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.function.Supplier;

public class WashingRegister {
    public static final Supplier<RecipeSerializer<WashingProcess>> WASHING_SERIALIZER =ModRegister.RECIPE_SERIALIZERS.register("washing", ()-> new RecipeSerializer<>(WashingProcess.CODEC, WashingProcess.STREAM_CODEC));
    public static final Supplier<RecipeType<WashingProcess>> WASHING_TYPE =ModRegister.RECIPE_TYPES.register("washing",RecipeType::simple);
    public static void init() {
    }

    private WashingRegister() {
    }
}
