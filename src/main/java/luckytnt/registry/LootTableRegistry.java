package luckytnt.registry;

import luckytnt.feature.Grave;
import luckytnt.feature.loottable.GraveLootTables;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class LootTableRegistry {
	
	public static ResourceKey<LootTable> GRAVE_LOOT_1 = ResourceKey.create(Registries.LOOT_TABLE, Grave.GRAVE_LOOT_1);
	public static ResourceKey<LootTable> GRAVE_LOOT_2 = ResourceKey.create(Registries.LOOT_TABLE, Grave.GRAVE_LOOT_2);
	public static ResourceKey<LootTable> GRAVE_LOOT_RARE = ResourceKey.create(Registries.LOOT_TABLE, Grave.GRAVE_LOOT_RARE);
	
	@SubscribeEvent
	public static void registerLootTables(RegisterEvent event) {
		event.register(Registries.LOOT_TABLE, GRAVE_LOOT_1.location(), () -> GraveLootTables.graveLoot1LootTable().build());
		event.register(Registries.LOOT_TABLE, GRAVE_LOOT_2.location(), () -> GraveLootTables.graveLoot2LootTable().build());
		event.register(Registries.LOOT_TABLE, GRAVE_LOOT_RARE.location(), () -> GraveLootTables.graveLootRareLootTable().build());
	}
}
