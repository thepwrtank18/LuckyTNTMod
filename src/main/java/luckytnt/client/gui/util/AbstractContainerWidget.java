package luckytnt.client.gui.util;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.narration.NarrationSupplier;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractContainerWidget extends AbstractWidget implements ContainerEventHandler {
	@Nullable
	private GuiEventListener focused;
	private boolean dragging;

	public AbstractContainerWidget(int x, int y, int w, int h, Component title) {
		super(x, y, w, h, title);
	}

	public void renderButton(PoseStack stack, int i, int i2, float f) {
		for (AbstractWidget abstractwidget : getContainedChildren()) {
			abstractwidget.render(stack, i, i2, f);
		}

	}

	public boolean isMouseOver(double x, double y) {
		for (AbstractWidget abstractwidget : getContainedChildren()) {
			if (abstractwidget.isMouseOver(x, y)) {
				return true;
			}
		}

		return false;
	}

	public void mouseMoved(double x, double y) {
		getContainedChildren().forEach((widget) -> {
			widget.mouseMoved(x, y);
		});
	}

	public List<? extends GuiEventListener> children() {
		return getContainedChildren();
	}

	protected abstract List<? extends AbstractWidget> getContainedChildren();

	public boolean isDragging() {
		return dragging;
	}

	public void setDragging(boolean dragging) {
		this.dragging = dragging;
	}

	public boolean mouseScrolled(double x, double y, double d) {
		boolean flag = false;

		for (AbstractWidget abstractwidget : getContainedChildren()) {
			if (abstractwidget.isMouseOver(x, y) && abstractwidget.mouseScrolled(x, y, d)) {
				flag = true;
			}
		}

		return flag || super.mouseScrolled(x, y, d);
	}

	public boolean changeFocus(boolean changed) {
		return ContainerEventHandler.super.changeFocus(changed);
	}

	@Nullable
	protected GuiEventListener getHovered() {
		for (AbstractWidget abstractwidget : getContainedChildren()) {
			if (abstractwidget.isHoveredOrFocused()) {
				return abstractwidget;
			}
		}

		return null;
	}

	@Nullable
	public GuiEventListener getFocused() {
		return focused;
	}

	public void setFocused(@Nullable GuiEventListener listener) {
		focused = listener;
	}

	public void updateWidgetNarration(NarrationElementOutput output) {
		GuiEventListener guieventlistener = getHovered();
		if (guieventlistener != null) {
			if (guieventlistener instanceof NarrationSupplier) {
				NarrationSupplier narrationsupplier = (NarrationSupplier) guieventlistener;
				narrationsupplier.updateNarration(output.nest());
			}
		} else {
			GuiEventListener guieventlistener1 = getFocused();
			if (guieventlistener1 != null && guieventlistener1 instanceof NarrationSupplier) {
				NarrationSupplier narrationsupplier1 = (NarrationSupplier) guieventlistener1;
				narrationsupplier1.updateNarration(output.nest());
			}
		}

	}

	public NarratableEntry.NarrationPriority narrationPriority() {
		if (!isHovered && getHovered() == null) {
			return focused != null ? NarratableEntry.NarrationPriority.FOCUSED : super.narrationPriority();
		} else {
			return NarratableEntry.NarrationPriority.HOVERED;
		}
	}

	public void setX(int newX) {
		for (AbstractWidget abstractwidget : getContainedChildren()) {
			int i = abstractwidget.x + newX - x;
			abstractwidget.x = i;
		}

		x = (newX);
	}

	public void setY(int newY) {
		for (AbstractWidget abstractwidget : getContainedChildren()) {
			int i = abstractwidget.y + newY - y;
			abstractwidget.y = i;
		}

		y = newY;
	}

	public Optional<GuiEventListener> getChildAt(double x, double y) {
		return ContainerEventHandler.super.getChildAt(x, y);
	}

	public boolean mouseClicked(double x, double y, int i) {
		return ContainerEventHandler.super.mouseClicked(x, y, i);
	}

	public boolean mouseReleased(double x, double y, int i) {
		return ContainerEventHandler.super.mouseReleased(x, y, i);
	}

	public boolean mouseDragged(double x, double y, int i, double newX, double newY) {
		return ContainerEventHandler.super.mouseDragged(x, y, i, newX, newY);
	}

	@OnlyIn(Dist.CLIENT)
	protected abstract static class AbstractChildWrapper {
		public final AbstractWidget child;
		public final LayoutSettings.LayoutSettingsImpl layoutSettings;

		protected AbstractChildWrapper(AbstractWidget child, LayoutSettings layoutSettings) {
			this.child = child;
			this.layoutSettings = layoutSettings.getExposed();
		}

		public int getHeight() {
			return child.getHeight() + layoutSettings.paddingTop + layoutSettings.paddingBottom;
		}

		public int getWidth() {
			return child.getWidth() + layoutSettings.paddingLeft + layoutSettings.paddingRight;
		}

		public void setX(int newX, int l) {
			float f = layoutSettings.paddingLeft;
			float f1 = l - child.getWidth() - layoutSettings.paddingRight;
			int i = (int) Mth.lerp(layoutSettings.xAlignment, f, f1);
			child.x = i + newX;
		}

		public void setY(int newX, int l) {
			float f = layoutSettings.paddingTop;
			float f1 = l - child.getHeight() - layoutSettings.paddingBottom;
			int i = (int) Mth.lerp(layoutSettings.yAlignment, f, f1);
			child.y = i + newX;
		}
	}
}