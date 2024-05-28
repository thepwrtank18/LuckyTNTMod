package luckytnt.util;

import java.util.function.Supplier;

import net.minecraft.network.chat.Component;

public enum CustomTNTConfig {	

	NO_EXPLOSION(() -> Component.translatable("luckytntmod.config.no_tnt")),
	NORMAL_EXPLOSION(() -> Component.translatable("luckytntmod.config.normal_tnt")),
	SPHERICAL_EXPLOSION(() -> Component.translatable("luckytntmod.config.spherical_tnt")),
	CUBICAL_EXPLOSION(() -> Component.translatable("luckytntmod.config.cubical_tnt")),
	EASTER_EGG(() -> Component.translatable("luckytntmod.config.easter_egg_tnt")),
	FIREWORK(() -> Component.translatable("luckytntmod.config.firework_tnt"));
	
	private final Supplier<Component> name;
	
	private CustomTNTConfig(Supplier<Component> name) {
		this.name = name;
	}
	
	public String getName() {
		return name.get().getString();
	}
	
	public Component getComponent() {
		return name.get();
	}
}
