package chbachman.armour.gui.tablet;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class TabletContainer extends Container{

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

}
