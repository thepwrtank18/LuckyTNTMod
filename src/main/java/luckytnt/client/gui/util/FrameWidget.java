package luckytnt.client.gui.util;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FrameWidget extends AbstractContainerWidget {
	private final List<FrameWidget.ChildContainer> children = new ArrayList<>();
	private final List<AbstractWidget> containedChildrenView = Collections
			.unmodifiableList(Lists.transform(children, (child) -> {
				return child.child;
			}));
	private int minWidth;
	private int minHeight;
	private final LayoutSettings defaultChildLayoutSettings = LayoutSettings.defaults().align(0.5F, 0.5F);

	public static FrameWidget withMinDimensions(int w, int h) {
		return new FrameWidget(0, 0, 0, 0).setMinDimensions(w, h);
	}

	public FrameWidget() {
		this(0, 0, 0, 0);
	}

	public FrameWidget(int x, int y, int w, int h) {
		super(x, y, w, h, Component.empty());
	}

	public FrameWidget setMinDimensions(int x, int y) {
		return setMinWidth(x).setMinHeight(y);
	}

	public FrameWidget setMinHeight(int h) {
		minHeight = h;
		return this;
	}

	public FrameWidget setMinWidth(int w) {
		minWidth = w;
		return this;
	}

	public LayoutSettings newChildLayoutSettings() {
		return defaultChildLayoutSettings.copy();
	}

	public LayoutSettings defaultChildLayoutSetting() {
		return defaultChildLayoutSettings;
	}

	public void pack() {
		int i = minWidth;
		int j = minHeight;

		for (FrameWidget.ChildContainer framewidget$childcontainer : children) {
			i = Math.max(i, framewidget$childcontainer.getWidth());
			j = Math.max(j, framewidget$childcontainer.getHeight());
		}

		for (FrameWidget.ChildContainer framewidget$childcontainer1 : children) {
			framewidget$childcontainer1.setX(x, i);
			framewidget$childcontainer1.setY(y, j);
		}

		width = i;
		height = j;
	}

	public <T extends AbstractWidget> T addChild(T child) {
		return addChild(child, newChildLayoutSettings());
	}

	public <T extends AbstractWidget> T addChild(T child, LayoutSettings settings) {
		children.add(new FrameWidget.ChildContainer(child, settings));
		return child;
	}

	protected List<AbstractWidget> getContainedChildren() {
		return containedChildrenView;
	}

	public static void centerInRectangle(AbstractWidget widget, int startL, int startT, int endR, int endB) {
		alignInRectangle(widget, startL, startT, endR, endB, 0.5F, 0.5F);
	}

	public static void alignInRectangle(AbstractWidget widget, int startL, int startT, int endR, int endB, float f1, float f2) {
		alignInDimension(startL, endR, widget.getWidth(), i -> {widget.x = i;}, f1);
		alignInDimension(startT, endB, widget.getHeight(), i -> {widget.y = i;}, f2);
	}

	public static void alignInDimension(int start, int end, int length, Consumer<Integer> consumer, float f) {
		int i = (int) Mth.lerp(f, 0.0F, (float) (end - length));
		consumer.accept(start + i);
	}

	@OnlyIn(Dist.CLIENT)
	static class ChildContainer extends AbstractContainerWidget.AbstractChildWrapper {
		protected ChildContainer(AbstractWidget widget, LayoutSettings settings) {
			super(widget, settings);
		}
	}

	@Override
	public void updateNarration(NarrationElementOutput output) {
		defaultButtonNarrationText(output);
	}
}