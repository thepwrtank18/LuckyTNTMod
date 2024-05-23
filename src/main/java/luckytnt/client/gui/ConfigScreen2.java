package luckytnt.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;

import luckytnt.config.LuckyTNTConfigValues;
import luckytnt.util.CustomTNTConfig;
import luckytntlib.client.gui.CenteredStringWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.FrameWidget;
import net.minecraft.client.gui.components.GridWidget;
import net.minecraft.client.gui.components.GridWidget.RowHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraftforge.client.gui.widget.ForgeSlider;
import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigScreen2 extends Screen {
	
	Button custom_tnt_first_explosion = null;
	ForgeSlider custom_tnt_first_explosion_intensity = null;
	Button custom_tnt_second_explosion = null;
	ForgeSlider custom_tnt_second_explosion_intensity = null;
	Button custom_tnt_third_explosion = null;
	ForgeSlider custom_tnt_third_explosion_intensity = null;

	public ConfigScreen2() {
		super(MutableComponent.create(new TranslatableContents("luckytntmod.config.title")));
	}
	
	@Override
	public void init() {
		Button empty = new Button.Builder(Component.empty(), button -> deactivatedButtonAction()).size(100, 15).build();
		
		empty.active = false;
		empty.visible = false;
		empty.setAlpha(0f);
		
		GridWidget grid = new GridWidget();
		grid.defaultCellSetting().paddingHorizontal(4).paddingBottom(1).alignHorizontallyCenter();
		
		RowHelper rows = grid.createRowHelper(3);
		
		rows.addChild(new CenteredStringWidget(Component.translatable("luckytntmod.config.custom_tnt"), font));
		rows.addChild(empty);
		rows.addChild(new CenteredStringWidget(Component.literal(""), font));
		
		rows.addChild(new CenteredStringWidget(Component.translatable("luckytntmod.config.first_tnt"), font));
		rows.addChild(empty);
		rows.addChild(new CenteredStringWidget(Component.literal(""), font));
		
		rows.addChild(custom_tnt_first_explosion = new Button.Builder(LuckyTNTConfigValues.CUSTOM_TNT_FIRST_EXPLOSION.get().getComponent(), button -> nextExplosionValue(LuckyTNTConfigValues.CUSTOM_TNT_FIRST_EXPLOSION, custom_tnt_first_explosion)).width(100).build());
		rows.addChild(new CenteredStringWidget(Component.translatable("luckytntmod.config.first_type"), font));
		rows.addChild(new Button.Builder(Component.translatable("luckytntmod.config.reset"), button -> resetExplosion(LuckyTNTConfigValues.CUSTOM_TNT_FIRST_EXPLOSION, custom_tnt_first_explosion)).width(100).build());
		
		rows.addChild(custom_tnt_first_explosion_intensity = new ForgeSlider(0, 0, 100, 20, Component.empty(), Component.empty(), 1, 20, LuckyTNTConfigValues.CUSTOM_TNT_FIRST_EXPLOSION_INTENSITY.get().intValue(), true));
		rows.addChild(new CenteredStringWidget(Component.translatable("luckytntmod.config.first_intensity"), font));
		rows.addChild(new Button.Builder(Component.translatable("luckytntmod.config.reset"), button -> resetIntValue(LuckyTNTConfigValues.CUSTOM_TNT_FIRST_EXPLOSION_INTENSITY, 1, custom_tnt_first_explosion_intensity)).width(100).build());
		
		rows.addChild(new CenteredStringWidget(Component.translatable("luckytntmod.config.second_tnt"), font));
		rows.addChild(empty);
		rows.addChild(new CenteredStringWidget(Component.literal(""), font));
		
		rows.addChild(custom_tnt_second_explosion = new Button.Builder(LuckyTNTConfigValues.CUSTOM_TNT_SECOND_EXPLOSION.get().getComponent(), button -> nextExplosionValue(LuckyTNTConfigValues.CUSTOM_TNT_SECOND_EXPLOSION, custom_tnt_second_explosion)).width(100).build());
		rows.addChild(new CenteredStringWidget(Component.translatable("luckytntmod.config.second_type"), font));
		rows.addChild(new Button.Builder(Component.translatable("luckytntmod.config.reset"), button -> resetExplosion(LuckyTNTConfigValues.CUSTOM_TNT_SECOND_EXPLOSION, custom_tnt_second_explosion)).width(100).build());
		
		rows.addChild(custom_tnt_second_explosion_intensity = new ForgeSlider(0, 0, 100, 20, Component.empty(), Component.empty(), 1, 20, LuckyTNTConfigValues.CUSTOM_TNT_SECOND_EXPLOSION_INTENSITY.get().intValue(), true));
		rows.addChild(new CenteredStringWidget(Component.translatable("luckytntmod.config.second_intensity"), font));
		rows.addChild(new Button.Builder(Component.translatable("luckytntmod.config.reset"), button -> resetIntValue(LuckyTNTConfigValues.CUSTOM_TNT_SECOND_EXPLOSION_INTENSITY, 1, custom_tnt_second_explosion_intensity)).width(100).build());
		
		rows.addChild(new CenteredStringWidget(Component.translatable("luckytntmod.config.third_tnt"), font));
		rows.addChild(empty);
		rows.addChild(new CenteredStringWidget(Component.literal(""), font));
		
		rows.addChild(custom_tnt_third_explosion = new Button.Builder(LuckyTNTConfigValues.CUSTOM_TNT_THIRD_EXPLOSION.get().getComponent(), button -> nextExplosionValue(LuckyTNTConfigValues.CUSTOM_TNT_THIRD_EXPLOSION, custom_tnt_third_explosion)).width(100).build());
		rows.addChild(new CenteredStringWidget(Component.translatable("luckytntmod.config.third_type"), font));
		rows.addChild(new Button.Builder(Component.translatable("luckytntmod.config.reset"), button -> resetExplosion(LuckyTNTConfigValues.CUSTOM_TNT_THIRD_EXPLOSION, custom_tnt_third_explosion)).width(100).build());
		
		rows.addChild(custom_tnt_third_explosion_intensity = new ForgeSlider(0, 0, 100, 20, Component.empty(), Component.empty(), 1, 20, LuckyTNTConfigValues.CUSTOM_TNT_THIRD_EXPLOSION_INTENSITY.get().intValue(), true));
		rows.addChild(new CenteredStringWidget(Component.translatable("luckytntmod.config.third_intensity"), font));
		rows.addChild(new Button.Builder(Component.translatable("luckytntmod.config.reset"), button -> resetIntValue(LuckyTNTConfigValues.CUSTOM_TNT_THIRD_EXPLOSION_INTENSITY, 1, custom_tnt_third_explosion_intensity)).width(100).build());
		
		grid.pack();
		FrameWidget.alignInRectangle(grid, 0, 15, width, height, 0.5f, 0f);
		addRenderableWidget(grid);
		
		addRenderableWidget(new Button.Builder(CommonComponents.GUI_DONE, button -> onClose()).bounds((width - 100) / 2, height - 30, 100, 20).build());
		addRenderableWidget(new Button.Builder(Component.translatable("luckytntmod.config.back"), button -> lastPage()).bounds((width - 100) / 8, height - 30, 100, 20).build());
	}
	
	@Override
	public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
		renderBackground(stack);
		drawCenteredString(stack, font, title, width / 2, 8, 0xFFFFFF);
		super.render(stack, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void onClose() {
		if(custom_tnt_first_explosion_intensity != null) {
			LuckyTNTConfigValues.CUSTOM_TNT_FIRST_EXPLOSION_INTENSITY.set(custom_tnt_first_explosion_intensity.getValueInt());
		}
		if(custom_tnt_second_explosion_intensity != null) {
			LuckyTNTConfigValues.CUSTOM_TNT_SECOND_EXPLOSION_INTENSITY.set(custom_tnt_second_explosion_intensity.getValueInt());
		}
		if(custom_tnt_third_explosion_intensity != null) {
			LuckyTNTConfigValues.CUSTOM_TNT_THIRD_EXPLOSION_INTENSITY.set(custom_tnt_third_explosion_intensity.getValueInt());
		}
		super.onClose();
	}
	
	public static void deactivatedButtonAction() {
	}
	
	public void lastPage() {
		onClose();
		Minecraft.getInstance().setScreen(new ConfigScreen());
	}
	
	public void resetIntValue(ForgeConfigSpec.IntValue config, int newValue, ForgeSlider slider) {
		config.set(newValue);
		slider.setValue(newValue);
	}
	
	public void nextExplosionValue(ForgeConfigSpec.EnumValue<CustomTNTConfig> config, Button button) {
		CustomTNTConfig value = config.get();
		if(value == CustomTNTConfig.NO_EXPLOSION) {
			value = CustomTNTConfig.NORMAL_EXPLOSION;
		}
		else if(value == CustomTNTConfig.NORMAL_EXPLOSION) {
			value = CustomTNTConfig.SPHERICAL_EXPLOSION;
		}
		else if(value == CustomTNTConfig.SPHERICAL_EXPLOSION) {
			value = CustomTNTConfig.CUBICAL_EXPLOSION;
		}
		else if(value == CustomTNTConfig.CUBICAL_EXPLOSION) {
			value = CustomTNTConfig.EASTER_EGG;
		}
		else if(value == CustomTNTConfig.EASTER_EGG) {
			value = CustomTNTConfig.FIREWORK;
		}
		else if(value == CustomTNTConfig.FIREWORK) {
			value = CustomTNTConfig.NO_EXPLOSION;
		}
		config.set(value);
		button.setMessage(config.get().getComponent());
	}
	
	public void resetExplosion(ForgeConfigSpec.EnumValue<CustomTNTConfig> config, Button button) {
		config.set(CustomTNTConfig.NO_EXPLOSION);
		button.setMessage(config.get().getComponent());
	}
}
