package luckytnt.client.overlay;

import java.lang.reflect.Field;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import luckytnt.config.LuckyTNTConfigValues;
import luckytnt.registry.EffectRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;

public class OverlayTick {
	
	private static float contaminatedAmount = 0;
	
	@SuppressWarnings("resource")
	public static void onOverlayRender(GuiGraphics graphics) {
		if(Minecraft.getInstance().player != null) {
			Player player = Minecraft.getInstance().player;
			int w = graphics.guiWidth();
			int h = graphics.guiHeight();
			RenderSystem.disableDepthTest();
			RenderSystem.depthMask(false);
			RenderSystem.enableBlend();
			RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			if(player.getPersistentData().getInt("freezeTime") > 0 && !player.hasEffect(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(EffectRegistry.CONTAMINATED_EFFECT.get()))) {
				RenderSystem.setShaderColor(1f, 1f, 1f, (float)(player.getPersistentData().getInt("freezeTime")) / 1200f);
				RenderSystem.setShaderTexture(0, new ResourceLocation("luckytntmod:textures/powder_snow_outline.png"));
				graphics.blit(new ResourceLocation("luckytntmod:textures/powder_snow_outline.png"), 0, 0, 0, 0, w, h, w, h);
			} else if(player.hasEffect(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(EffectRegistry.CONTAMINATED_EFFECT.get())) && LuckyTNTConfigValues.RENDER_CONTAMINATED_OVERLAY.get()) {
				RenderSystem.setShaderColor(1f, 1f, 1f, contaminatedAmount);
				RenderSystem.setShaderTexture(0, new ResourceLocation("luckytntmod:textures/contaminated_outline.png"));
				graphics.blit(new ResourceLocation("luckytntmod:textures/contaminated_outline.png"), 0, 0, 0, 0, w, h, w, h);
				contaminatedAmount = Mth.clamp(contaminatedAmount + 0.025f, 0f, 1f);
			} else if(contaminatedAmount > 0){
				RenderSystem.setShaderColor(1f, 1f, 1f, contaminatedAmount);
				RenderSystem.setShaderTexture(0, new ResourceLocation("luckytntmod:textures/contaminated_outline.png"));
				graphics.blit(new ResourceLocation("luckytntmod:textures/contaminated_outline.png"), 0, 0, 0, 0, w, h, w, h);
				contaminatedAmount = Mth.clamp(contaminatedAmount - 0.025f, 0f, 1f);
			}
			RenderSystem.depthMask(true);
			RenderSystem.defaultBlendFunc();
			RenderSystem.disableBlend();
			RenderSystem.enableDepthTest();
			RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
		}
	}
	
	public static void onGameStart() {
		Minecraft minecraft = Minecraft.getInstance();
		Gui gui = minecraft.gui;
		LayeredDraw.Layer layer = new LayeredDraw.Layer() {
			
			@Override
			public void render(GuiGraphics graphics, float partialTicks) {
				onOverlayRender(graphics);
			}
		};
		
		try {
			loop: for(Field field : Gui.class.getDeclaredFields()) {
				field.setAccessible(true);

				if(field.get(gui) instanceof LayeredDraw layers) {
					layers.add(layer);
					break loop;
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
