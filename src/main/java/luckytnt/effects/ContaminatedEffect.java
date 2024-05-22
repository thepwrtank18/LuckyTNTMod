package luckytnt.effects;

import java.lang.reflect.Field;

import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;

public class ContaminatedEffect extends MobEffect{
	
	public ContaminatedEffect(MobEffectCategory category, int id) {
		super(category, id);
	}

	@Override
	public Component getDisplayName() {
		return Component.translatable("effect.contaminated_effect");
	}
	
	@Override
    public boolean applyEffectTick(LivingEntity ent, int amplifier) {
		if(ent instanceof Player player) {
			try {
                Field field = FoodData.class.getDeclaredField("tickTimer");
                field.setAccessible(true);
                field.setInt(player.getFoodData(), -100);
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
		}
		
		if(ent.getHealth() > 4.0F) {
			ent.hurt(ent.damageSources().magic(), 1.0F);
		}
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        int i = 40 >> amplifier;
        return i > 0 ? duration % i == 0 : true;
    }
}
