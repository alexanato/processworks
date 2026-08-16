package at.randorf.processworks;

import net.minecraft.world.item.BlockItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS =DeferredRegister.createItems(Processworks.MOD_ID);

    public static final DeferredItem<BlockItem> WOODEN_BASKET =ITEMS.registerSimpleBlockItem(ModBlocks.WOODEN_BASKET);
}
