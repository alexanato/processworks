package at.randorf.processworks.mixin.block_entity;

import at.randorf.processworks.Processworks;
import at.randorf.processworks.content.basket.base.BasketFallingEntity;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.PotentSulfurBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(PotentSulfurBlockEntity.class)
public abstract class PotentSulfurBlockEntityMixin {

    @Inject(
            method = "lambda$static$5",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/Entity;getDeltaMovement()Lnet/minecraft/world/phys/Vec3;",
                    shift = At.Shift.BEFORE
            )
    )
    private static void processworks$inGeyser(
            Level level,
            BlockPos pos,
            BlockState state,
            PotentSulfurBlockEntity blockEntity,
            CallbackInfo ci,
            @Local Entity entityToBeLaunched
    ) {
        if(entityToBeLaunched instanceof BasketFallingEntity basket){
            basket.handleSulfurProcessing();
        }
    }
}