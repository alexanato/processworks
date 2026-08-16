package at.randorf.processworks.mixin.entitys;

import at.randorf.processworks.Processworks;
import at.randorf.processworks.Recipes;
import at.randorf.processworks.processes.washing.recipe.WashingRecipe;
import at.randorf.processworks.processes.washing.recipe.WashingRecipeInput;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SandBlock;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Optional;

@Mixin(FallingBlockEntity.class)
public abstract class FallingBlockEntityMixin implements FallingBlockEntityAccessor{
    @Unique
    private int processworks$waterTicks = 0;

    @Inject(method = "tick", at = @At("HEAD"))
    private void processworks$tick(CallbackInfo ci) {
        FallingBlockEntity entity = (FallingBlockEntity) (Object) this;

        if (!(entity.level() instanceof ServerLevel serverLevel)) {
            return;
        }

    }
}