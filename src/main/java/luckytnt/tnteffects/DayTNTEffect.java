package luckytnt.tnteffects;

import org.joml.Vector3f;

import luckytnt.registry.BlockRegistry;
import luckytntlib.util.IExplosiveEntity;
import luckytntlib.util.explosions.PrimedTNTEffect;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;

public class DayTNTEffect extends PrimedTNTEffect{

	@Override
	public void serverExplosion(IExplosiveEntity entity) {
		if(entity.level() instanceof ServerLevel sLevel) {
			sLevel.setDayTime(6000);
		}
	}
	
	@Override
	public void spawnParticles(IExplosiveEntity entity) {
		entity.level().addParticle(new DustParticleOptions(new Vector3f(1f, 1f, 1f), 1f), entity.x(), entity.y() + 1f, entity.z(), 0, -0.1f, 0);
	}
	
	@Override
	public Block getBlock() {
		return BlockRegistry.DAY_TNT.get();
	}
}
