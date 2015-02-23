package chbachman.armour.gui.crafting;

import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementSlider;

public class UpgradeSlider extends ElementSlider{

	protected UpgradeSlider(GuiBase containerScreen, int x, int y, int width, int height, int maxValue, int minValue) {
		super(containerScreen, x, y, width, height, maxValue, minValue);
	}

	@Override
	protected void dragSlider(int x, int y){
		System.out.println(x);
		System.out.println(y);
	}

}
