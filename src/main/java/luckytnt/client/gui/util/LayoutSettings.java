package luckytnt.client.gui.util;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface LayoutSettings {
	LayoutSettings padding(int padding);

	LayoutSettings padding(int paddingH, int paddingV);

	LayoutSettings padding(int paddingL, int paddingT, int paddingR, int paddingB);

	LayoutSettings paddingLeft(int padding);

	LayoutSettings paddingTop(int padding);

	LayoutSettings paddingRight(int padding);

	LayoutSettings paddingBottom(int padding);

	LayoutSettings paddingHorizontal(int padding);

	LayoutSettings paddingVertical(int padding);

	LayoutSettings align(float x, float y);

	LayoutSettings alignHorizontally(float x);

	LayoutSettings alignVertically(float y);

	default LayoutSettings alignHorizontallyLeft() {
		return alignHorizontally(0.0F);
	}

	default LayoutSettings alignHorizontallyCenter() {
		return alignHorizontally(0.5F);
	}

	default LayoutSettings alignHorizontallyRight() {
		return alignHorizontally(1.0F);
	}

	default LayoutSettings alignVerticallyTop() {
		return alignVertically(0.0F);
	}

	default LayoutSettings alignVerticallyMiddle() {
		return alignVertically(0.5F);
	}

	default LayoutSettings alignVerticallyBottom() {
		return alignVertically(1.0F);
	}

	LayoutSettings copy();

	LayoutSettings.LayoutSettingsImpl getExposed();

	static LayoutSettings defaults() {
		return new LayoutSettings.LayoutSettingsImpl();
	}

	@OnlyIn(Dist.CLIENT)
	public static class LayoutSettingsImpl implements LayoutSettings {
		public int paddingLeft;
		public int paddingTop;
		public int paddingRight;
		public int paddingBottom;
		public float xAlignment;
		public float yAlignment;

		public LayoutSettingsImpl() {
		}

		public LayoutSettingsImpl(LayoutSettings.LayoutSettingsImpl other) {
			paddingLeft = other.paddingLeft;
			paddingTop = other.paddingTop;
			paddingRight = other.paddingRight;
			paddingBottom = other.paddingBottom;
			xAlignment = other.xAlignment;
			yAlignment = other.yAlignment;
		}

		public LayoutSettings.LayoutSettingsImpl padding(int padding) {
			return padding(padding, padding);
		}

		public LayoutSettings.LayoutSettingsImpl padding(int paddingH, int paddingV) {
			return paddingHorizontal(paddingH).paddingVertical(paddingV);
		}

		public LayoutSettings.LayoutSettingsImpl padding(int paddingL, int paddingT, int paddingR, int paddingB) {
			return paddingLeft(paddingL).paddingRight(paddingR).paddingTop(paddingT).paddingBottom(paddingB);
		}

		public LayoutSettings.LayoutSettingsImpl paddingLeft(int padding) {
			paddingLeft = padding;
			return this;
		}

		public LayoutSettings.LayoutSettingsImpl paddingTop(int padding) {
			paddingTop = padding;
			return this;
		}

		public LayoutSettings.LayoutSettingsImpl paddingRight(int padding) {
			paddingRight = padding;
			return this;
		}

		public LayoutSettings.LayoutSettingsImpl paddingBottom(int padding) {
			paddingBottom = padding;
			return this;
		}

		public LayoutSettings.LayoutSettingsImpl paddingHorizontal(int padding) {
			return paddingLeft(padding).paddingRight(padding);
		}

		public LayoutSettings.LayoutSettingsImpl paddingVertical(int padding) {
			return paddingTop(padding).paddingBottom(padding);
		}

		public LayoutSettings.LayoutSettingsImpl align(float x, float y) {
			xAlignment = x;
			yAlignment = y;
			return this;
		}

		public LayoutSettings.LayoutSettingsImpl alignHorizontally(float x) {
			xAlignment = x;
			return this;
		}

		public LayoutSettings.LayoutSettingsImpl alignVertically(float y) {
			yAlignment = y;
			return this;
		}

		public LayoutSettings.LayoutSettingsImpl copy() {
			return new LayoutSettings.LayoutSettingsImpl(this);
		}

		public LayoutSettings.LayoutSettingsImpl getExposed() {
			return this;
		}
	}
}