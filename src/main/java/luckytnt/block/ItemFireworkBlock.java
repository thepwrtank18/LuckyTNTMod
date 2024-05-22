package luckytnt.block;

import javax.annotation.Nullable;

import luckytnt.block.entity.ItemFireworkBlockEntity;
import luckytnt.entity.PrimedItemFirework;
import luckytnt.registry.EntityRegistry;
import luckytntlib.block.LTNTBlock;
import luckytntlib.entity.PrimedLTNT;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class ItemFireworkBlock extends LTNTBlock implements EntityBlock {

	public ItemFireworkBlock(Properties properties) {
		super(properties, EntityRegistry.ITEM_FIREWORK, false);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public PrimedLTNT explode(Level level, boolean exploded, double x, double y, double z, @Nullable LivingEntity igniter) throws NullPointerException {
		if(TNT != null) {
			BlockEntity blockEntity = level.getBlockEntity(new BlockPos(Mth.floor(x), Mth.floor(y), Mth.floor(z)));
			PrimedItemFirework tnt = new PrimedItemFirework(EntityRegistry.ITEM_FIREWORK.get(), level);
			tnt.setFuse(40);
			tnt.setPos(x + 0.5f, y, z + 0.5f);
			tnt.setOwner(igniter);
			if(blockEntity != null && blockEntity instanceof ItemFireworkBlockEntity block && block.components().has(DataComponents.BLOCK_ENTITY_DATA)) {
				CustomData data = blockEntity.components().get(DataComponents.BLOCK_ENTITY_DATA);
				
				tnt.item = block.item;
				tnt.stack = block.stack;
				tnt.getPersistentData().putInt("itemID", data.getUnsafe().getInt("itemID"));
			}
			level.addFreshEntity(tnt);
			level.playSound(null, new BlockPos(Mth.floor(x), Mth.floor(y), Mth.floor(z)), SoundEvents.TNT_PRIMED, SoundSource.MASTER, 1, 1);
			if(level.getBlockState(new BlockPos(Mth.floor(x), Mth.floor(y), Mth.floor(z))).getBlock() == this) {
				level.setBlock(new BlockPos(Mth.floor(x), Mth.floor(y), Mth.floor(z)), Blocks.AIR.defaultBlockState(), 3);
			}
			return tnt;
		}
		throw new NullPointerException("No TNT entity present. Make sure it is registered before the block is registered");
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return EntityRegistry.ITEM_FIREWORK_BLOCK_ENTITY.get().create(pos, state);
	}

	@SuppressWarnings("deprecation")
	@Override
	public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		Item item = stack == null || stack.isEmpty() ? null : stack.getItem();
		if(stack != null && !stack.isEmpty() && item != Items.FLINT_AND_STEEL && level.getBlockEntity(pos) != null && level.getBlockEntity(pos) instanceof ItemFireworkBlockEntity block) {
			CustomData data = CustomData.EMPTY;
	        
	        if(!block.components().has(DataComponents.BLOCK_ENTITY_DATA)) {
	        	DataComponentMap.Builder builder = DataComponentMap.builder();
	            builder.addAll(block.components());
	            builder.set(DataComponents.BLOCK_ENTITY_DATA, CustomData.EMPTY);
	            block.setComponents(builder.build());
	            data = block.components().get(DataComponents.BLOCK_ENTITY_DATA);
	        } else {
	        	data = block.components().get(DataComponents.BLOCK_ENTITY_DATA);
	        }
			
			block.item = item;
			block.stack = stack.copy();
			data.getUnsafe().putInt("itemID", Item.getId(item));
			if(!player.isCreative()) {
				stack.shrink(1);
			}
			player.awardStat(Stats.ITEM_USED.get(item));
			return ItemInteractionResult.SUCCESS;
		}
		return super.useItemOn(stack, state, level, pos, player, hand, result);
	}
}
