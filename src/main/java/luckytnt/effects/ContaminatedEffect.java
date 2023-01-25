package luckytnt.effects;

import java.lang.reflect.Field;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;

public class ContaminatedEffect extends MobEffect{

	private int duration;
	
	public ContaminatedEffect(MobEffectCategory category, int id) {
		super(category, id);
	}

	@Override
	public Component getDisplayName() {
		return MutableComponent.create(new TranslatableContents("effect.contaminated_effect"));
	}
	
	@Override
	public boolean isDurationEffectTick(int duration, int amplifier) {
		this.duration = duration;
		return true;
	}

	@Override
	public void applyEffectTick(LivingEntity entity, int amplifier) {
		if(entity instanceof Player player) {
			try {
                Field field = FoodData.class.getDeclaredField("tickTimer");
                field.setAccessible(true);
                field.setInt(player.getFoodData(), 0);
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
		}
		int i = 40 >> duration;
		if (i > 0) {
			if(amplifier % i == 0) {
				if (entity.getHealth() > 4.0F) {
					entity.hurt(DamageSource.MAGIC, 1.0F);
				}
			}
		}
	}
}
