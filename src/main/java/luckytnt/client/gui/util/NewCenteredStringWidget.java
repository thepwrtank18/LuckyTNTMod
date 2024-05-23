package luckytnt.client.gui.util;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;

public class NewCenteredStringWidget extends CenteredStringWidget {

	public NewCenteredStringWidget(Component component, Font font) {
		super(component, font);
	}

	@Override
	public void render(PoseStack pose, int i1, int i2, float f) {
		pose.pushPose();
		pose.translate(0f, 6f, 0f);
		super.render(pose, i1, i2, f);
		pose.popPose();
	}
}