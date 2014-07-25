package chbachman.armour.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import chbachman.armour.gui.container.ArmourContainer;
import cofh.network.PacketCoFHBase;
import cofh.network.PacketHandler;

public class ArmourPacket extends PacketCoFHBase{
	
	public static void initialize() {

		PacketHandler.instance.registerPacket(ArmourPacket.class);
	}

	public enum PacketTypes {
		
		ARMOURCRAFTING();
		
	}

	@Override
	public void handlePacket(EntityPlayer player, boolean isServer) {

		try {
			int type = getByte();

			switch (PacketTypes.values()[type]) {
			
			case ARMOURCRAFTING: handleCraftingPacket(player);
			
			
			default:
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static PacketCoFHBase getPacket(PacketTypes type) {

		return new ArmourPacket().addByte(type.ordinal());
	}
	
	public void handleCraftingPacket(EntityPlayer player){
		
		Container container = player.openContainer;
		
		if(container instanceof ArmourContainer){
			ArmourContainer armourContainer = (ArmourContainer) container;
			
			armourContainer.onButtonClick();
		}
	}

}
