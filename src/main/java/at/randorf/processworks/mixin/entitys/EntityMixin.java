package at.randorf.processworks.mixin.entitys;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(
            method = "canCollideWith",
            at = @At("HEAD"),
            cancellable = true
    )
    private void processworks$fallingBlocksCanBeCollidedWith(
            @Nullable Entity other,
            CallbackInfoReturnable<Boolean> cir
    ) {
        if ((Object) this instanceof FallingBlockEntity) {
            cir.setReturnValue(true);
        }
    }
}