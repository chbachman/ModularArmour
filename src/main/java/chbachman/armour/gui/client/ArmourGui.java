package chbachman.armour.gui.client;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import chbachman.armour.reference.ResourceLocationHelper;
import cofh.gui.GuiBaseAdv;

public class ArmourGui extends GuiBaseAdv{

	private static final ResourceLocation TEXTURE = ResourceLocationHelper.getResourceLocation("/gui/armourGui.png");
	
	public ArmourGui(Container container, InventoryPlayer inventory) {
		super(container);
		
		this.texture = TEXTURE;
		this.drawInventory = false;
		this.drawTitle = true;
		this.xSize = 176;
		this.ySize = 234;
		
	}

}
