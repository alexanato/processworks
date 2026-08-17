package at.randorf.processworks.content.basket.base;

import at.randorf.processworks.Processworks;
import at.randorf.processworks.registry.BasketRegister;
import at.randorf.client.renderstate.FallingBlockRenderStateExtension;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.FallingBlockRenderer;
import net.minecraft.client.renderer.entity.state.FallingBlockRenderState;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = Processworks.MOD_ID, value = Dist.CLIENT)
public class BasketFallingEntityRenderer extends FallingBlockRenderer {
    private final ItemModelResolver itemModelResolver;

    public BasketFallingEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        itemModelResolver =context.getItemModelResolver();
    }

    @Override
    public void extractRenderState(FallingBlockEntity entity, FallingBlockRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        if( entity instanceof BasketFallingEntity basketFallingEntity){
            ItemStack stack = basketFallingEntity.getStoredStack();
            ItemStackRenderState itemState = ((FallingBlockRenderStateExtension) (Object) state).processworks$getItemStackRenderState();
            itemState.clear();
            ((FallingBlockRenderStateExtension) (Object) state).processworks$setLightCoords(
                    LightCoordsUtil.getLightCoords(
                            entity.level(),
                            entity.blockPosition()
                    )
            );
            if (!stack.isEmpty()) {
                this.itemModelResolver.updateForTopItem(
                        itemState,
                        stack,
                        ItemDisplayContext.FIXED,
                        entity.level(),
                        null,
                        entity.getStartPos().hashCode()
                );
            }
        }
    }

    @Override
    public void submit(FallingBlockRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        super.submit(state, poseStack, submitNodeCollector, camera);
        ItemStackRenderState itemState = ((FallingBlockRenderStateExtension) (Object) state).processworks$getItemStackRenderState();
        if (itemState.isEmpty()) {
            return;
        }
        poseStack.pushPose();
        poseStack.translate(
                0.0F,
                0.5F,
                0.0F
        );
        poseStack.scale(
                0.9F,
                0.9F,
                0.9F
        );
        poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
        itemState.submit(
                poseStack,
                submitNodeCollector,
                ((FallingBlockRenderStateExtension) (Object) state).processworks$getLightCoords(),
                OverlayTexture.NO_OVERLAY,
                0
        );

        poseStack.popPose();
    }
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(
                BasketRegister.WOODEN_BASKET_ENTITY.get(),
                BasketFallingEntityRenderer::new
        );
    }
}
