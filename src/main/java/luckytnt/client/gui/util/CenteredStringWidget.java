package luckytnt.client.gui.util;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CenteredStringWidget extends AbstractWidget {
	private int color = 16777215;
	private final Font font;

	public CenteredStringWidget(Component text, Font font) {
		this(0, 0, font.width(text.getVisualOrderText()), 9, text, font);
	}

	public CenteredStringWidget(int c, int a, Component text, Font font) {
		this(0, 0, c, a, text, font);
	}

	public CenteredStringWidget(int i, int i1, int i2, int i3, Component text, Font font) {
		super(i, i1, i2, i3, text);
		this.font = font;
		active = false;
	}

	public CenteredStringWidget color(int color) {
		this.color = color;
		return this;
	}

	public void updateWidgetNarration(NarrationElementOutput output) {
	}

	public void renderButton(PoseStack stack, int i1, int i2, float f) {
		drawCenteredString(stack, font, getMessage(), x + getWidth() / 2, y + (getHeight() - 9) / 2, color);
	}

	@Override
	public void updateNarration(NarrationElementOutput output) {
		defaultButtonNarrationText(output);
	}
}