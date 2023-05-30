package luckytnt.tnteffects;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.ExplosionHelper;
import luckytntlib.util.explosions.IForEachBlockExplosionEffect;
import luckytntlib.util.explosions.ImprovedExplosion;
import luckytntlib.util.tnteffects.PrimedTNTEffect;
import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class DripstoneTNTEffect extends PrimedTNTEffect{

	private final int radius;
	
	public DripstoneTNTEffect(int radius) {
		this.radius = radius;
	}
	
	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		ExplosionHelper.doSphericalExplosion(entity.level(), entity.getPos(), radius, new IForEachBlockExplosionEffect() {
			
			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(entity.level())) < 100 && (!state.isCollisionShapeFullBlock(level, pos) || state.getMaterial() == Material.LEAVES || state.is(BlockTags.LOGS))) {
					state.onBlockExploded(level, pos, ImprovedExplosion.dummyExplosion(entity.level()));
				}
			}
		});
		ExplosionHelper.doSphericalExplosion(entity.level(), entity.getPos(), radius, new IForEachBlockExplosionEffect() {

			@Override
			public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
				if(level.getBlockState(pos.below()).isAir() && !state.isAir() && state.getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(entity.level())) < 100 && !state.is(BlockTags.DRIPSTONE_REPLACEABLE)) {
					state.onBlockExploded(level, pos, ImprovedExplosion.dummyExplosion(entity.level()));
					level.setBlockAndUpdate(pos, Blocks.STONE.defaultBlockState());
				}
				else if(level.getBlockState(pos.below()).getExplosionResistance(level, pos, ImprovedExplosion.dummyExplosion(entity.level())) < 100 && !level.getBlockState(pos.below()).isAir() && state.isAir() && !level.getBlockState(pos.below()).is(BlockTags.DRIPSTONE_REPLACEABLE)) {
					state.onBlockExploded(level, pos, ImprovedExplosion.dummyExplosion(entity.level()));
					level.setBlockAndUpdate(pos.below(), Blocks.STONE.defaultBlockState());
				}
			}
		});
		if(entity.level() instanceof ServerLevel sLevel) {
			ExplosionHelper.doSphericalExplosion(sLevel, entity.getPos(), Math.round(radius * 0.75f), new IForEachBlockExplosionEffect() {

				@Override
				public void doBlockExplosion(Level level, BlockPos pos, BlockState state, double distance) {
					if(((level.getBlockState(pos.below()).isAir() && !state.isAir()) || (!level.getBlockState(pos.below()).isAir() && state.isAir())) && Math.random() < 0.1f) {
						if(Math.random() < 0.9f) {
							CaveFeatures.DRIPSTONE_CLUSTER.get().place(sLevel, sLevel.getChunkSource().getGenerator(), sLevel.random, pos);
						}
						else {
							CaveFeatures.LARGE_DRIPSTONE.value().place(sLevel, sLevel.getChunkSource().getGenerator(), sLevel.random, pos);
						}
					}
				}
			});
		}
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.DRIPSTONE_TNT.get();
	}
}
