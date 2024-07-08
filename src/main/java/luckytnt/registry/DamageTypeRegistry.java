package luckytnt.registry;

import luckytnt.LuckyTNTMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DeathMessageType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DamageTypeRegistry {
	
	public static ResourceKey<DamageType> SAY_GOODBYE = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(LuckyTNTMod.MODID, "say_goodbye"));
	public static ResourceKey<DamageType> HAILSTONE = ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(LuckyTNTMod.MODID, "hailstone"));
	
	@SubscribeEvent
	public static void registerDamageTypes(RegisterEvent event) {
		event.register(Registries.DAMAGE_TYPE, SAY_GOODBYE.location(), () -> new DamageType("say_goodbye", DamageScaling.NEVER, 0f, DamageEffects.HURT, DeathMessageType.DEFAULT));
		event.register(Registries.DAMAGE_TYPE, HAILSTONE.location(), () -> new DamageType("hailstone", DamageScaling.NEVER, 0f, DamageEffects.HURT, DeathMessageType.DEFAULT));
	}
}
