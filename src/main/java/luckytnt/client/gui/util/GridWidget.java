package luckytnt.client.gui.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GridWidget extends AbstractContainerWidget {
	private final List<AbstractWidget> children = new ArrayList<>();
	private final List<GridWidget.CellInhabitant> cellInhabitants = new ArrayList<>();
	private final LayoutSettings defaultCellSettings = LayoutSettings.defaults();
	private final int sw, sh;

	public GridWidget() {
		this(0, 0);
	}
	
	public GridWidget(int sw, int sh) {
		this(0, 0, sw, sh, Component.empty());
	}

	public GridWidget(int x, int y, int sw, int sh, Component title) {
		super(x, y, 0, 0, title);
		this.sw = sw;
		this.sh = sh;
	}

	public void pack() {
		int i = 0;
		int j = 0;

		for (GridWidget.CellInhabitant gridwidget$cellinhabitant : cellInhabitants) {
			i = Math.max(gridwidget$cellinhabitant.getLastOccupiedRow(), i);
			j = Math.max(gridwidget$cellinhabitant.getLastOccupiedColumn(), j);
		}

		int[] aint = new int[j + 1];
		int[] aint1 = new int[i + 1];

		for (GridWidget.CellInhabitant gridwidget$cellinhabitant1 : cellInhabitants) {
			Divisor divisor = new Divisor(gridwidget$cellinhabitant1.getHeight(),
					gridwidget$cellinhabitant1.occupiedRows);

			for (int k = gridwidget$cellinhabitant1.row; k <= gridwidget$cellinhabitant1.getLastOccupiedRow(); ++k) {
				aint1[k] = Math.max(aint1[k], divisor.nextInt());
			}

			Divisor divisor1 = new Divisor(gridwidget$cellinhabitant1.getWidth(),
					gridwidget$cellinhabitant1.occupiedColumns);

			for (int l = gridwidget$cellinhabitant1.column; l <= gridwidget$cellinhabitant1
					.getLastOccupiedColumn(); ++l) {
				aint[l] = Math.max(aint[l], divisor1.nextInt());
			}
		}

		int[] aint2 = new int[j + 1];
		int[] aint3 = new int[i + 1];
		aint2[0] = 0;

		for (int k1 = 1; k1 <= j; ++k1) {
			aint2[k1] = aint2[k1 - 1] + aint[k1 - 1];
		}

		aint3[0] = 0;

		for (int l1 = 1; l1 <= i; ++l1) {
			aint3[l1] = aint3[l1 - 1] + aint1[l1 - 1];
		}
		
		width = aint2[j] + aint[j];
		height = aint3[i] + aint1[i];
		
		x = (sw / 2) - (width / 2);
		y = (sh / 2) - (height / 2);

		for (GridWidget.CellInhabitant gridwidget$cellinhabitant2 : cellInhabitants) {
			int i2 = 0;

			for (int i1 = gridwidget$cellinhabitant2.column; i1 <= gridwidget$cellinhabitant2
					.getLastOccupiedColumn(); ++i1) {
				i2 += aint[i1];
			}

			gridwidget$cellinhabitant2.setX(x + aint2[gridwidget$cellinhabitant2.column], i2);
			int j2 = 0;

			for (int j1 = gridwidget$cellinhabitant2.row; j1 <= gridwidget$cellinhabitant2.getLastOccupiedRow(); ++j1) {
				j2 += aint1[j1];
			}

			gridwidget$cellinhabitant2.setY(y + aint3[gridwidget$cellinhabitant2.row], j2);
		}
	}

	public <T extends AbstractWidget> T addChild(T child, int x, int y) {
		return addChild(child, x, y, newCellSettings());
	}

	public <T extends AbstractWidget> T addChild(T child, int x, int y, LayoutSettings settings) {
		return addChild(child, x, y, 1, 1, settings);
	}

	public <T extends AbstractWidget> T addChild(T child, int x, int y, int r, int c) {
		return addChild(child, x, y, r, c, newCellSettings());
	}

	public <T extends AbstractWidget> T addChild(T child, int x, int y, int r, int c, LayoutSettings settings) {
		if (r < 1) {
			throw new IllegalArgumentException("Occupied rows must be at least 1");
		} else if (c < 1) {
			throw new IllegalArgumentException("Occupied columns must be at least 1");
		} else {
			cellInhabitants.add(new GridWidget.CellInhabitant(child, x, y, r, c, settings));
			children.add(child);
			return child;
		}
	}

	protected List<? extends AbstractWidget> getContainedChildren() {
		return children;
	}

	public LayoutSettings newCellSettings() {
		return defaultCellSettings.copy();
	}

	public LayoutSettings defaultCellSetting() {
		return defaultCellSettings;
	}

	public GridWidget.RowHelper createRowHelper(int r) {
		return new GridWidget.RowHelper(r);
	}

	@OnlyIn(Dist.CLIENT)
	static class CellInhabitant extends AbstractContainerWidget.AbstractChildWrapper {
		final int row;
		final int column;
		final int occupiedRows;
		final int occupiedColumns;

		CellInhabitant(AbstractWidget widget, int row, int column, int occupiedRows, int occupiedColumns, LayoutSettings settings) {
			super(widget, settings.getExposed());
			this.row = row;
			this.column = column;
			this.occupiedRows = occupiedRows;
			this.occupiedColumns = occupiedColumns;
		}

		public int getLastOccupiedRow() {
			return row + occupiedRows - 1;
		}

		public int getLastOccupiedColumn() {
			return column + occupiedColumns - 1;
		}
	}

	@OnlyIn(Dist.CLIENT)
	public final class RowHelper {
		private final int columns;
		private int index;

		RowHelper(int columns) {
			this.columns = columns;
		}

		public <T extends AbstractWidget> T addChild(T child) {
			return addChild(child, 1);
		}

		public <T extends AbstractWidget> T addChild(T child, int x) {
			return addChild(child, x, defaultCellSetting());
		}

		public <T extends AbstractWidget> T addChild(T child, LayoutSettings settings) {
			return addChild(child, 1, settings);
		}

		public <T extends AbstractWidget> T addChild(T child, int c, LayoutSettings settings) {
			int i = index / columns;
			int j = index % columns;
			if (j + c > columns) {
				++i;
				j = 0;
				index = Mth.roundToward(index, columns);
			}

			index += c;
			return GridWidget.this.addChild(child, i, j, 1, c, settings);
		}

		public LayoutSettings newCellSettings() {
			return GridWidget.this.newCellSettings();
		}

		public LayoutSettings defaultCellSetting() {
			return GridWidget.this.defaultCellSetting();
		}
	}

	@Override
	public void updateNarration(NarrationElementOutput output) {
		defaultButtonNarrationText(output);
	}
}