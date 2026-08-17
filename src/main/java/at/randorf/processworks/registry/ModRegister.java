package at.randorf.processworks.registry;

import at.randorf.processworks.Processworks;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRegister {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE,Processworks.MOD_ID);

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Processworks.MOD_ID);
    public static final DeferredRegister<MapCodec<? extends Block>> CODECS = DeferredRegister.create(BuiltInRegistries.BLOCK_TYPE, Processworks.MOD_ID);

    public static final DeferredRegister.Entities ENTITIES =DeferredRegister.createEntities(Processworks.MOD_ID);

    public static final DeferredRegister.Items ITEMS =DeferredRegister.createItems(Processworks.MOD_ID);

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =DeferredRegister.create(Registries.RECIPE_TYPE, Processworks.MOD_ID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Registries.RECIPE_SERIALIZER, Processworks.MOD_ID);

    public static void register(IEventBus modEventBus){
        BasketRegister.init();
        WashingRegister.init();

        ModRegister.RECIPE_TYPES.register(modEventBus);
        ModRegister.RECIPE_SERIALIZERS.register(modEventBus);
        ModRegister.BLOCKS.register(modEventBus);
        ModRegister.CODECS.register(modEventBus);
        ModRegister.ITEMS.register(modEventBus);
        ModRegister.BLOCK_ENTITY_TYPES.register(modEventBus);
        ModRegister.ENTITIES.register(modEventBus);

        modEventBus.addListener(BasketRegister::register);
    }
}
