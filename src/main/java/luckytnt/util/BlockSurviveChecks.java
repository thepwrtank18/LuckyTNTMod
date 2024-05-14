package luckytnt.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;

public class BlockSurviveChecks {
	
	public static boolean canSnowSurvive(BlockState state, LevelReader reader, BlockPos pos) {
		BlockState blockstate = reader.getBlockState(pos.below());
		if (blockstate.is(BlockTags.SNOW_LAYER_CANNOT_SURVIVE_ON)) {
			return false;
		} else {
			return blockstate.is(BlockTags.SNOW_LAYER_CAN_SURVIVE_ON) ? true : Block.isFaceFull(blockstate.getCollisionShape(reader, pos.below()), Direction.UP) || blockstate.is(Blocks.SNOW) && blockstate.getValue(SnowLayerBlock.LAYERS) == 8;
		}
	}
	
	public static boolean canFireSurvive(BlockState state, LevelReader reader, BlockPos pos) {
        BlockPos blockpos = pos.below();
        return reader.getBlockState(blockpos).isFaceSturdy(reader, blockpos, Direction.UP) || isValidFireLocation(reader, pos);
    }
	
	private static boolean isValidFireLocation(BlockGetter getter, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            if (((FireBlock)Blocks.FIRE).canCatchFire(getter, pos.relative(direction), direction.getOpposite())) {
                return true;
            }
        }

        return false;
    }
}
