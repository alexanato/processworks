package at.randorf.processworks.block_entitys;

import at.randorf.processworks.ModBlockEntity;
import at.randorf.processworks.ModEntities;
import at.randorf.processworks.Processworks;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.FallingBlockRenderer;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.Display;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;

import javax.annotation.Nullable;
@EventBusSubscriber(modid = Processworks.MOD_ID, value = Dist.CLIENT)
public class WoodenBasketBlockEntityRenderer implements BlockEntityRenderer<WoodenBasketBlockEntity, WoodenBasketBlockEntityRenderState> {

    private final ItemModelResolver itemModelResolver;

    public WoodenBasketBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public WoodenBasketBlockEntityRenderState createRenderState() {
        return new WoodenBasketBlockEntityRenderState();
    }

    @Override
    public void extractRenderState(WoodenBasketBlockEntity blockEntity, WoodenBasketBlockEntityRenderState renderState, float partialTick, Vec3 cameraPos, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity,renderState, partialTick, cameraPos, crumblingOverlay);
        ItemStack stack = blockEntity.getInventory().getCurrentItem();
        renderState.item.clear();

        if (!stack.isEmpty()) {
            this.itemModelResolver.updateForTopItem(
                    renderState.item,
                    stack,
                    ItemDisplayContext.FIXED,
                    blockEntity.getLevel(),
                    null,
                    blockEntity.getBlockPos().hashCode()
            );
        }
    }

    @Override
    public void submit(WoodenBasketBlockEntityRenderState woodenBasketBlockEntityRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        if (woodenBasketBlockEntityRenderState.item.isEmpty()) {
            return;
        }
        poseStack.pushPose();
        poseStack.translate(
                0.5F,
                0.5F,
                0.5F
        );
        poseStack.scale(
                0.9F,
                0.9F,
                0.9F
        );
        poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
        woodenBasketBlockEntityRenderState.item.submit(
                poseStack,
                submitNodeCollector,
                woodenBasketBlockEntityRenderState.lightCoords,
                OverlayTexture.NO_OVERLAY,
                0
        );

        poseStack.popPose();
    }
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(
                ModBlockEntity.WOODEN_BASKET.get(),
                WoodenBasketBlockEntityRenderer::new
        );
    }
}
