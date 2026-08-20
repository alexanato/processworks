package at.randorf.processworks.registry;

import at.randorf.processworks.processes.SulfurProcess;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.function.Supplier;

public class SulfurProcessingRegister {
    public static final Supplier<RecipeSerializer<SulfurProcess>> SULFUR_PROCESSING_SERIALIZER =ModRegister.RECIPE_SERIALIZERS.register("sulfur_processing", ()-> new RecipeSerializer<>(SulfurProcess.CODEC, SulfurProcess.STREAM_CODEC));
    public static final Supplier<RecipeType<SulfurProcess>> SULFUR_PROCESSING_TYPE =ModRegister.RECIPE_TYPES.register("sulfur_processing",RecipeType::simple);
    public static void init() {
    }

    private SulfurProcessingRegister() {
    }
}
