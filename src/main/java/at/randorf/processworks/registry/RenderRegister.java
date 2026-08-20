package at.randorf.processworks.registry;

import at.randorf.processworks.Processworks;
import at.randorf.processworks.content.basket.base.BasketFallingEntityRenderer;
import at.randorf.processworks.content.crushing_crate.base.CrushingCrateBlockEntityRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = Processworks.MOD_ID, value = Dist.CLIENT)
public class RenderRegister {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(CrushingCrateRegister.IRON_CRUSHING_CRATE_BE.get(), CrushingCrateBlockEntityRenderer::new);
        event.registerEntityRenderer(
                BasketRegister.WOODEN_BASKET_ENTITY.get(),
                BasketFallingEntityRenderer::new
        );
    }
}
