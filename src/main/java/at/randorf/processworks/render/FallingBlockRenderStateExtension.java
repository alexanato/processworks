package at.randorf.processworks.render;

import net.minecraft.client.renderer.item.ItemStackRenderState;

public interface FallingBlockRenderStateExtension {

    ItemStackRenderState processworks$getItemStackRenderState();

    int processworks$getLightCoords();

    void processworks$setLightCoords(int lightCoords);
}