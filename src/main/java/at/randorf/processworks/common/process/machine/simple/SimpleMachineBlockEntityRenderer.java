package at.randorf.processworks.common.process.machine.simple;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class SimpleMachineBlockEntityRenderer implements BlockEntityRenderer<SimpleMachineBlockEntity, SimpleMachineBlockEntityRenderState> {
    private final ItemModelResolver itemModelResolver;

    public SimpleMachineBlockEntityRenderer(BlockEntityRendererProvider.Context context){
        this.itemModelResolver = context.itemModelResolver();
    }

    @Override
    public SimpleMachineBlockEntityRenderState createRenderState() {
        return new SimpleMachineBlockEntityRenderState();
    }
    @Override
    public void extractRenderState(SimpleMachineBlockEntity blockEntity, SimpleMachineBlockEntityRenderState renderState, float partialTick, Vec3 cameraPos, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
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
    public void submit(SimpleMachineBlockEntityRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        if (state.item.isEmpty()) {
            return;
        }
        poseStack(poseStack);

        submitModel(state,poseStack,submitNodeCollector,cameraRenderState);

    }
    public void poseStack( PoseStack poseStack){
        poseStack.pushPose();
        poseStack.translate(0.5F,0.5F,0.5F);
        poseStack.scale(0.9F,0.9F,0.9F );
        poseStack.mulPose(Axis.ZP.rotationDegrees(90.0F));
    }
    public void submitModel(SimpleMachineBlockEntityRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState){
        state.item.submit(
                poseStack,
                submitNodeCollector,
                state.lightCoords,
                OverlayTexture.NO_OVERLAY,
                0
        );
        poseStack.popPose();
    }
}
