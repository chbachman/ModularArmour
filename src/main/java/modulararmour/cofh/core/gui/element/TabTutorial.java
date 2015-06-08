package modulararmour.cofh.core.gui.element;

import modulararmour.cofh.lib.gui.GuiBase;
import modulararmour.cofh.lib.util.helpers.StringHelper;

public class TabTutorial extends TabScrolledText {

	public static boolean enable;
	public static int defaultSide = 0;
	public static int defaultHeaderColor = 0xe1c92f;
	public static int defaultSubHeaderColor = 0xaaafb8;
	public static int defaultTextColor = 0xffffff;
	public static int defaultBackgroundColor = 0x5a09bb;


	public TabTutorial(GuiBase gui, String infoString) {

		this(gui, defaultSide, infoString);
	}

	public TabTutorial(GuiBase gui, int side, String infoString) {

		super(gui, side, infoString);
		setVisible(enable);

		headerColor = defaultHeaderColor;
		subheaderColor = defaultSubHeaderColor;
		textColor = defaultTextColor;
		backgroundColor = defaultBackgroundColor;
	}

	@Override
	public String getIcon() {

		return "IconTutorial";
	}

	@Override
	public String getTitle() {

		return StringHelper.localize("info.cofh.tutorial");
	}

}
