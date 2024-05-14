package luckytnt.feature.loottable;

import luckytnt.registry.BlockRegistry;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

public class GraveLootTables {

	public static LootTable.Builder graveLoot1LootTable() {
		return LootTable.lootTable()
			.withPool(
				LootPool.lootPool()
					.setRolls(ConstantValue.exactly(3f))
					.add(LootItem.lootTableItem(Items.BONE).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(6f, 8f))))
					.add(LootItem.lootTableItem(Items.BONE_MEAL).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(3f, 6f))))
					.add(LootItem.lootTableItem(Items.SKELETON_SKULL).setWeight(1))
					.add(LootItem.lootTableItem(BlockRegistry.PUMPKIN_BOMB.get()).setWeight(1))
					.add(LootItem.lootTableItem(Blocks.BONE_BLOCK).setWeight(5))
			).withPool(
				LootPool.lootPool()
					.setRolls(ConstantValue.exactly(1f))
					.add(LootItem.lootTableItem(Items.COBWEB).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(3f, 6f))))
			);
	}
	
	public static LootTable.Builder graveLoot2LootTable() {
		return LootTable.lootTable()
			.withPool(
				LootPool.lootPool()
					.setRolls(ConstantValue.exactly(3f))
					.add(LootItem.lootTableItem(Items.REDSTONE).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(4f, 8f))))
					.add(LootItem.lootTableItem(Items.ROTTEN_FLESH).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(4f, 8f))))
					.add(LootItem.lootTableItem(Items.LEATHER).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(3f, 5f))))
					.add(LootItem.lootTableItem(Items.ZOMBIE_HEAD).setWeight(1))
					.add(LootItem.lootTableItem(BlockRegistry.ZOMBIE_APOCALYPSE.get()).setWeight(1))
			).withPool(
				LootPool.lootPool()
					.setRolls(ConstantValue.exactly(1f))
					.add(LootItem.lootTableItem(Items.COBWEB).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(3f, 6f))))
			);
	}
	
	public static LootTable.Builder graveLootRareLootTable() {
		return LootTable.lootTable()
			.withPool(
				LootPool.lootPool()
					.setRolls(ConstantValue.exactly(1f))
					.setBonusRolls(ConstantValue.exactly(1f))
					.add(LootItem.lootTableItem(Blocks.DRIPSTONE_BLOCK).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(25f, 35f))))
					.add(LootItem.lootTableItem(Blocks.CALCITE).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(25f, 35f))))
					.add(LootItem.lootTableItem(Blocks.COBBLED_DEEPSLATE).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(25f, 35f))))
			).withPool(
				LootPool.lootPool()
					.setRolls(ConstantValue.exactly(1f))
					.setBonusRolls(ConstantValue.exactly(1f))
					.add(LootItem.lootTableItem(Items.GOLDEN_CARROT).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(15f, 25f))))
					.add(LootItem.lootTableItem(Items.WITHER_ROSE).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(15f, 25f))))
					.add(LootItem.lootTableItem(Items.TINTED_GLASS).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(15f, 25f))))
					.add(LootItem.lootTableItem(Items.GLOW_LICHEN).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(15f, 25f))))
			).withPool(
				LootPool.lootPool()
					.setRolls(ConstantValue.exactly(1f))
					.setBonusRolls(ConstantValue.exactly(1f))
					.add(LootItem.lootTableItem(Items.EMERALD).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(20f, 25f))))
					.add(LootItem.lootTableItem(Items.COPPER_BLOCK).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(7f, 10f))))
					.add(LootItem.lootTableItem(Items.OBSIDIAN).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(10f, 15f))))
			).withPool(
				LootPool.lootPool()
					.setRolls(ConstantValue.exactly(1f))
					.setBonusRolls(ConstantValue.exactly(1f))
					.add(LootItem.lootTableItem(Items.EXPERIENCE_BOTTLE).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(5f, 12f))))
					.add(LootItem.lootTableItem(Items.ENDER_PEARL).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(5f, 9f))))
					.add(LootItem.lootTableItem(Items.NAME_TAG).setWeight(10))
					.add(LootItem.lootTableItem(BlockRegistry.GRAVEYARD_TNT.get()).setWeight(1))
			);
	}
}
