package luckytnt.item;

import java.util.List;

import luckytnt.registry.EntityRegistry;
import luckytnt.registry.SoundRegistry;
import luckytntlib.entity.LExplosiveProjectile;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;

public class VacuumCleaner extends Item{

	public int soundCooldown = 0;
	
	public VacuumCleaner() {
		super(new Item.Properties().stacksTo(1).durability(1000));
	}
	
	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.NONE;
	}
	
	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext ctx, List<Component> components, TooltipFlag flag) {
		super.appendHoverText(stack, ctx, components, flag);
		components.add(Component.translatable("item.vacuum_cleaner.info"));
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		if(!player.getItemInHand(hand).has(DataComponents.CUSTOM_DATA)) {
			player.getItemInHand(hand).set(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
		}
		
		onUseTick(level, player, player.getItemInHand(hand), player.getItemInHand(hand).getCount());
		if(!player.getItemInHand(hand).get(DataComponents.CUSTOM_DATA).getUnsafe().getBoolean("using")) {
			soundCooldown = 42;
			player.getItemInHand(hand).get(DataComponents.CUSTOM_DATA).getUnsafe().putBoolean("using", true);
		}
		else if(player.getItemInHand(hand).get(DataComponents.CUSTOM_DATA).getUnsafe().getBoolean("using")) {
			player.getItemInHand(hand).get(DataComponents.CUSTOM_DATA).getUnsafe().putBoolean("using", false);
		}
		if(player.getItemInHand(hand).get(DataComponents.CUSTOM_DATA).getUnsafe().getBoolean("using"))
			level.playSound(null, player, SoundRegistry.VACUUM_CLEANER_START.get(), SoundSource.MASTER, 2, 1);
		return new InteractionResultHolder<ItemStack>(InteractionResult.SUCCESS, player.getItemInHand(hand));
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void inventoryTick(ItemStack stack, Level level, Entity entity, int count, boolean inHand) {
		if(!stack.has(DataComponents.CUSTOM_DATA)) {
			stack.set(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
		}
		
		if(stack.get(DataComponents.CUSTOM_DATA).getUnsafe().getBoolean("using") && inHand) {
			if(!level.isClientSide)
				soundCooldown--;
			if(soundCooldown == 0) {
				level.playSound(null, entity, SoundRegistry.VACUUM_CLEANER.get(), SoundSource.MASTER, 2, 1);
				if(!level.isClientSide)
					soundCooldown = 22;
			}
			if(entity instanceof Player player) {
				if(!player.isCreative()) {
					stack.setDamageValue(stack.getDamageValue() + 1);
					if(stack.getDamageValue() > 1960)
						stack.shrink(1);
				}
				LExplosiveProjectile shot = EntityRegistry.VACUUM_SHOT.get().create(level);
				shot.setPos(player.getPosition(1f).add(0, player.getEyeHeight(), 0));
				shot.shoot(player.getViewVector(1).x, player.getViewVector(1).y, player.getViewVector(1).z, 4, 0);
				shot.pickup = Pickup.DISALLOWED;
				level.addFreshEntity(shot);
			}
		}
		else
			stack.get(DataComponents.CUSTOM_DATA).getUnsafe().putBoolean("using", false);
	}
}
