package at.randorf.processworks.content.crushing_crate.base;

import at.randorf.processworks.Processworks;
import at.randorf.processworks.common.process.machine.simple.SimpleMachineBlockEntityRenderer;
import at.randorf.processworks.content.basket.base.BasketBlockEntityRenderState;
import at.randorf.processworks.content.basket.base.BasketBlockEntityRenderer;
import at.randorf.processworks.content.basket.wooden.WoodenBasketBlockEntity;
import at.randorf.processworks.registry.BasketRegister;
import at.randorf.processworks.registry.CrushingCrateRegister;
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
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class CrushingCrateBlockEntityRenderer extends SimpleMachineBlockEntityRenderer {

    public CrushingCrateBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }
    @Override
    public void poseStack(PoseStack stack){
        stack.pushPose();
        stack.translate(0.5F,0.5F,0.5F);
        stack.scale(0.9F,0.9F,0.9F );
    }
}
