package at.randorf.processworks;

import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class ModCapabilities {
    public static void register(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.Item.BLOCK,
                ModBlockEntity.WOODEN_BASKET.get(),
                (blockEntity, side) -> blockEntity.getItemHandler()
        );
    }
}
