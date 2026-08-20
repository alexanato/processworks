package at.randorf.processworks.mixin.entitys;

import at.randorf.processworks.Processworks;
import at.randorf.processworks.content.crushing_crate.base.CrushingCrateBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntityMixin {
    @Shadow
    private float fallDamagePerDistance;

    @Shadow
    public abstract BlockPos getStartPos();

    @Shadow
    private int fallDamageMax;

    @Inject(method = "causeFallDamage", at = @At("HEAD"))
    private void causeFallDamage(double fallDistance, float damageModifier, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        FallingBlockEntity self = (FallingBlockEntity) (Object) this;
        Level level = self.level();

        if(level.getBlockEntity(self.blockPosition().below()) instanceof CrushingCrateBlockEntity blockEntity){
            float damage = (float)Math.min(Mth.floor((float)fallDistance * this.fallDamagePerDistance), fallDamageMax);
            blockEntity.crush((int) damage,self.level(),blockEntity);
        }
    }
}
