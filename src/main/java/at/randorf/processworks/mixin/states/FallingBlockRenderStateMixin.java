package at.randorf.processworks.mixin.states;

import at.randorf.processworks.render.FallingBlockRenderStateExtension;
import net.minecraft.client.renderer.entity.state.FallingBlockRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(FallingBlockRenderState.class)
public abstract class FallingBlockRenderStateMixin implements FallingBlockRenderStateExtension {

    @Unique
    private final ItemStackRenderState processworks$itemStackRenderState =new ItemStackRenderState();
    @Unique
    private int processworks$lightCoords;
    @Override
    public ItemStackRenderState processworks$getItemStackRenderState() {
        return this.processworks$itemStackRenderState;
    }
    @Override
    public int processworks$getLightCoords() {
        return processworks$lightCoords;
    }

    @Override
    public void processworks$setLightCoords(int lightCoords) {
        this.processworks$lightCoords = lightCoords;
    }
}