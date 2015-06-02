package repack.cofh.core.gui.element;

import repack.cofh.lib.gui.GuiBase;
import repack.cofh.lib.util.helpers.StringHelper;

public class TabInfo extends TabScrolledText {

	public static boolean enable;
	public static int defaultSide = 0;
	public static int defaultHeaderColor = 0xe1c92f;
	public static int defaultSubHeaderColor = 0xaaafb8;
	public static int defaultTextColor = 0xffffff;
	public static int defaultBackgroundColor = 0x555555;

	public TabInfo(GuiBase gui, String infoString) {

		this(gui, defaultSide, infoString);
	}

	public TabInfo(GuiBase gui, int side, String infoString) {

		super(gui, side, infoString);
		setVisible(enable);

		headerColor = defaultHeaderColor;
		subheaderColor = defaultSubHeaderColor;
		textColor = defaultTextColor;
		backgroundColor = defaultBackgroundColor;
	}

	@Override
	public String getIcon() {

		return "IconInformation";
	}

	@Override
	public String getTitle() {

		return StringHelper.localize("info.cofh.information");
	}

}
