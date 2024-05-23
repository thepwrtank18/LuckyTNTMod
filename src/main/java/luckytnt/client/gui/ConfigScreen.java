package luckytnt.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;

import luckytnt.client.gui.util.FrameWidget;
import luckytnt.client.gui.util.GridWidget;
import luckytnt.client.gui.util.GridWidget.RowHelper;
import luckytnt.client.gui.util.NewCenteredStringWidget;
import luckytnt.config.LuckyTNTConfigValues;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraftforge.client.gui.widget.ForgeSlider;
import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigScreen extends Screen {

	ForgeSlider island_slider = null;
	ForgeSlider dropped_slider = null;
	ForgeSlider average_disaster_time_silder = null;
	ForgeSlider average_disaster_strength_slider = null;
	
	Button season_events_always_active = null;
	Button render_contaminated_overlay = null;	
	Button present_drop_destroy = null;
	
	ForgeSlider light_engine_speed_slider = null;
	
	public ConfigScreen() {
		super(MutableComponent.create(new TranslatableContents("luckytntmod.config.title")));
	}
	
	@Override
	public void init() {
		GridWidget grid = new GridWidget(width, height);
		grid.defaultCellSetting().paddingHorizontal(4).paddingBottom(4).alignHorizontallyCenter();
		
		RowHelper rows = grid.createRowHelper(3);
		
		rows.addChild(island_slider = new ForgeSlider(0, 0, 100, 20, Component.empty(), Component.empty(), 20, 160, LuckyTNTConfigValues.ISLAND_HEIGHT.get(), true));
		rows.addChild(new NewCenteredStringWidget(Component.translatable("luckytntmod.config.island_offset"), font));
		rows.addChild(new Button(0, 0, 100, 20, Component.translatable("luckytntmod.config.reset"), button -> resetIntValue(LuckyTNTConfigValues.ISLAND_HEIGHT, 50, island_slider)));
		
		rows.addChild(dropped_slider = new ForgeSlider(0, 0, 100, 20, Component.empty(), Component.empty(), 60, 400, LuckyTNTConfigValues.DROP_HEIGHT.get(), true));
		rows.addChild(new NewCenteredStringWidget(Component.translatable("luckytntmod.config.drop_offset"), font));
		rows.addChild(new Button(0, 0, 100, 20, Component.translatable("luckytntmod.config.reset"), button -> resetIntValue(LuckyTNTConfigValues.DROP_HEIGHT, 200, dropped_slider)));
		
		rows.addChild(average_disaster_time_silder = new ForgeSlider(0, 0, 100, 20, Component.empty(), Component.empty(), 2, 24, LuckyTNTConfigValues.MAXIMUM_DISASTER_TIME.get().doubleValue(), true));
		rows.addChild(new NewCenteredStringWidget(Component.translatable("luckytntmod.config.maximum_time"), font));
		rows.addChild(new Button(0, 0, 100, 20, Component.translatable("luckytntmod.config.reset"), button -> resetIntValue(LuckyTNTConfigValues.MAXIMUM_DISASTER_TIME, 12, average_disaster_time_silder)));
		
		rows.addChild(average_disaster_strength_slider = new ForgeSlider(0, 0, 100, 20, Component.empty(), Component.empty(), 1d, 10d, LuckyTNTConfigValues.AVERAGE_DIASTER_INTENSITY.get().doubleValue(), true));
		rows.addChild(new NewCenteredStringWidget(Component.translatable("luckytntmod.config.average_intensity"), font));
		rows.addChild(new Button(0, 0, 100, 20, Component.translatable("luckytntmod.config.reset"), button -> resetDoubleValue(LuckyTNTConfigValues.AVERAGE_DIASTER_INTENSITY, 1d, average_disaster_strength_slider)));
		
		rows.addChild(season_events_always_active = new Button(0, 0, 100, 20, LuckyTNTConfigValues.SEASON_EVENTS_ALWAYS_ACTIVE.get().booleanValue() ? CommonComponents.OPTION_ON : CommonComponents.OPTION_OFF, button -> nextBooleanValue(LuckyTNTConfigValues.SEASON_EVENTS_ALWAYS_ACTIVE, season_events_always_active)));
		rows.addChild(new NewCenteredStringWidget(Component.translatable("luckytntmod.config.event_always_active"), font));
		rows.addChild(new Button(0, 0, 100, 20, Component.translatable("luckytntmod.config.reset"), button -> resetBooleanValue(LuckyTNTConfigValues.SEASON_EVENTS_ALWAYS_ACTIVE, false, season_events_always_active)));
		
		rows.addChild(render_contaminated_overlay = new Button(0, 0, 100, 20, LuckyTNTConfigValues.RENDER_CONTAMINATED_OVERLAY.get().booleanValue() ? CommonComponents.OPTION_ON : CommonComponents.OPTION_OFF, button -> nextBooleanValue(LuckyTNTConfigValues.RENDER_CONTAMINATED_OVERLAY, render_contaminated_overlay)));
		rows.addChild(new NewCenteredStringWidget(Component.translatable("luckytntmod.config.render_overlay"), font));
		rows.addChild(new Button(0, 0, 100, 20, Component.translatable("luckytntmod.config.reset"), button -> resetBooleanValue(LuckyTNTConfigValues.RENDER_CONTAMINATED_OVERLAY, true, render_contaminated_overlay)));
		
		rows.addChild(light_engine_speed_slider = new ForgeSlider(0, 0, 100, 20, Component.empty(), Component.empty(), 5, 5000, LuckyTNTConfigValues.LIGHT_ENGINE_SPEED.get(), true));
		rows.addChild(new NewCenteredStringWidget(Component.translatable("luckytntmod.config.light_engine"), font));
		rows.addChild(new Button(0, 0, 100, 20, Component.translatable("luckytntmod.config.reset"), button -> resetIntValue(LuckyTNTConfigValues.LIGHT_ENGINE_SPEED, 100, light_engine_speed_slider)));
		
		rows.addChild(present_drop_destroy = new Button(0, 0, 100, 20, LuckyTNTConfigValues.PRESENT_DROP_DESTROY_BLOCKS.get().booleanValue() ? CommonComponents.OPTION_ON : CommonComponents.OPTION_OFF, button -> nextBooleanValue(LuckyTNTConfigValues.PRESENT_DROP_DESTROY_BLOCKS, present_drop_destroy)));
		rows.addChild(new NewCenteredStringWidget(Component.translatable("luckytntmod.config.present_drop"), font));
		rows.addChild(new Button(0, 0, 100, 20, Component.translatable("luckytntmod.config.reset"), button -> resetBooleanValue(LuckyTNTConfigValues.PRESENT_DROP_DESTROY_BLOCKS, true, present_drop_destroy)));
		
		grid.pack();
		FrameWidget.alignInRectangle(grid, 0, 15, width, height, 0.5f, 0f);
		addRenderableWidget(grid);
		
		addRenderableWidget(new Button((width - 100) / 2, height - 25, 100, 20, CommonComponents.GUI_DONE, button -> onClose()));
		addRenderableWidget(new Button(((width - 100) / 8) * 7, height - 25, 100, 20, Component.translatable("luckytntmod.config.next"), button -> nextPage()));
	}
	
	@Override
	public void render(PoseStack stack, int mouseX, int mouseY, float partialTicks) {
		renderBackground(stack);
		drawCenteredString(stack, font, title, width / 2, 8, 0xFFFFFF);
		super.render(stack, mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void onClose() {
		if(island_slider != null) {
			LuckyTNTConfigValues.ISLAND_HEIGHT.set(island_slider.getValueInt());
		}
		if(dropped_slider != null) {
			LuckyTNTConfigValues.DROP_HEIGHT.set(dropped_slider.getValueInt());
		}
		if(average_disaster_time_silder != null) {
			LuckyTNTConfigValues.MAXIMUM_DISASTER_TIME.set(average_disaster_time_silder.getValueInt());
		}
		if(average_disaster_strength_slider != null) {
			LuckyTNTConfigValues.AVERAGE_DIASTER_INTENSITY.set(average_disaster_strength_slider.getValue());
		}
		if(light_engine_speed_slider != null) {
			LuckyTNTConfigValues.LIGHT_ENGINE_SPEED.set(light_engine_speed_slider.getValueInt());
		}
		super.onClose();
	}
	
	public void nextPage() {
		onClose();
		Minecraft.getInstance().setScreen(new ConfigScreen2());
	}
	
	public void resetIntValue(ForgeConfigSpec.IntValue config, int newValue, ForgeSlider slider) {
		config.set(newValue);
		slider.setValue(newValue);
	}
	
	public void resetDoubleValue(ForgeConfigSpec.DoubleValue config, double newValue, ForgeSlider slider) {
		config.set(newValue);
		slider.setValue(newValue);
	}
	
	public void nextBooleanValue(ForgeConfigSpec.BooleanValue config, Button button) {
		boolean value = config.get().booleanValue();
		if(value) {
			value = false;
		} else {
			value = true;
		}
		config.set(value);
		button.setMessage(value ? CommonComponents.OPTION_ON : CommonComponents.OPTION_OFF);
	}
	
	public void resetBooleanValue(ForgeConfigSpec.BooleanValue config, boolean defaultValue, Button button) {
		config.set(defaultValue);
		button.setMessage(defaultValue ? CommonComponents.OPTION_ON : CommonComponents.OPTION_OFF);
	}
}
