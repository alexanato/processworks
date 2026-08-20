package at.randorf.processworks.registry;

import at.randorf.processworks.processes.CrushingProcess;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.function.Supplier;

public class CrushingRegister {
    public static final Supplier<RecipeSerializer<CrushingProcess>> CRUSHING_SERIALIZER =ModRegister.RECIPE_SERIALIZERS.register("crushing", ()-> new RecipeSerializer<>(CrushingProcess.CODEC, CrushingProcess.STREAM_CODEC));
    public static final Supplier<RecipeType<CrushingProcess>> CRUSHING_TYPE =ModRegister.RECIPE_TYPES.register("crushing",RecipeType::simple);
    public static void init() {
    }
}
