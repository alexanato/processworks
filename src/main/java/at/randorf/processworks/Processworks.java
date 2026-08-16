package at.randorf.processworks;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(Processworks.MOD_ID)
public class Processworks {
    public static final String MOD_ID = "processworks";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Processworks(IEventBus modEventBus, ModContainer modContainer){
        Recipes.RECIPE_TYPES.register(modEventBus);
        Recipes.RECIPE_SERIALIZERS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModBlocks.CODECS.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModBlockEntity.BLOCK_ENTITY_TYPES.register(modEventBus);
        ModEntities.ENTITIES.register(modEventBus);
        modEventBus.addListener(ModCapabilities::register);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }
}
