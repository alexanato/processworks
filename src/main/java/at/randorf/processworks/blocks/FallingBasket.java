package at.randorf.processworks.blocks;

import at.randorf.processworks.entitys.BasketFallingEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;

public abstract class FallingBasket extends FallingBlock{
    public FallingBasket(Properties properties) {
        super(properties);
    }


}
