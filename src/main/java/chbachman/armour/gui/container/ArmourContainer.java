package chbachman.armour.gui.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cofh.gui.container.ContainerInventoryItem;

public class ArmourContainer extends ContainerInventoryItem{
	
	World world;
	
	public ArmourContainer(ItemStack stack, InventoryPlayer inventory, World world) {
		super(stack, inventory);
		
		this.world = world;
		
		this.bindCraftingGrid();
		
		this.bindPlayerInventory(inventory);
	}
	
	protected void bindCraftingGrid(){
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 3; j++){
				addSlotToContainer(new Slot(this.containerWrapper, i * 3 + j, 116 + j *  18, 7 + i * 18));
			}
		}
	}
	
	protected void bindPlayerInventory(InventoryPlayer inventoryPlayer)
	{
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 152 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++)
		{
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 152 + 58));
		}
	}
	
	public void onSlotChanged(){
		super.onSlotChanged();
	}

}
