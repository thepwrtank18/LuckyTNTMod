package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.level.block.Block;

public class TimerTNTEffect extends PrimedTNTEffect{
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		float r = entity.getTNTFuse() < 400 ? 1f : 2f - 0.0025f * (float)entity.getTNTFuse();
		float g = entity.getTNTFuse() >= 400 ? 1f : 0.0025f * (float)entity.getTNTFuse();
		entity.level().addParticle(new DustParticleOptions(new Vector3f(r, g, 0), 1f), entity.x(), entity.y() + 1f, entity.z(), 0, 0, 0);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.TIMER_TNT.get();
	}
	
	@Override
	public int getDefaultFuse(IExplosiveEntity entity) {
		return 800;
	}
}
