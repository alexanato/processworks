package at.randorf.processworks;

import at.randorf.processworks.registry.*;
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
      ModRegister.register(modEventBus);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }
}
