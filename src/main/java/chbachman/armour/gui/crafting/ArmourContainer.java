package chbachman.armour.gui.crafting;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import chbachman.api.item.IModularItem;
import chbachman.api.nbt.NBTHelper;
import chbachman.api.registry.UpgradeList;
import chbachman.api.upgrade.IUpgrade;
import chbachman.armour.ModularArmour;
import chbachman.armour.gui.GuiHandler;
import chbachman.armour.handler.UpgradeHandler;
import chbachman.armour.network.ArmourPacket;
import chbachman.armour.network.IContainerSyncable;
import chbachman.armour.network.IInputHandler;
import chbachman.armour.upgrade.UpgradeException;
import chbachman.armour.util.MiscUtil;
import chbachman.armour.util.UpgradeUtil;
import cofh.lib.gui.container.ContainerInventoryItem;

public class ArmourContainer extends ContainerInventoryItem implements IInputHandler, IContainerSyncable{
    
    public IUpgrade upgrade = null;
    
    public final IModularItem item;
    
    public ArmourContainer(ItemStack stack, InventoryPlayer inventory) {
    	super(stack, inventory);
        this.item = (IModularItem) stack.getItem();
        
        this.bindCraftingGrid();
        this.bindPlayerInventory(inventory);
    }
    
    protected void bindCraftingGrid() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.addSlotToContainer(new Slot(this.containerWrapper, i * 3 + j, 183 + j * 18, 152 + i * 18));
            }
        }
    }
    
    protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 152 + i * 18));
            }
        }
        
        for (int i = 0; i < 9; i++) {
            this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 152 + 58));
        }
    }
    
    public void onSlotChanged() {
    	super.onSlotChanged();
        this.upgrade = UpgradeHandler.getResult(this.containerWrapper);
    }
    
    @Override
    public void onButtonClick(ArmourPacket packet, String name) {
    	
    	boolean shouldSync = false;
    	
        try {
            
            if (name.equals("UpgradeAddition")) {
                if (UpgradeHandler.addUpgrade(this.getContainerStack(), this.upgrade)) {
                    
                	for (int i = 0; i < this.containerWrapper.getSizeInventory(); i++) {
                        this.containerWrapper.decrStackSize(i, 1);
                    }
                    
                    this.upgrade = UpgradeHandler.getResult(this.containerWrapper);
                	
                    shouldSync = true;
                }
                
            } else if (name.equals("RemoveItems")) {
            	shouldSync = true;
            	
                this.onContainerClosed(this.player);
            } else if (name.equals("RemoveUpgrade")) {
                UpgradeUtil.removeUpgrade(this.getContainerStack(), UpgradeList.INSTANCE.get(packet.getString()));
                
                shouldSync = true;
            } else if(name.equals("Recipe")){
            	
                if (MiscUtil.isServer(this.player.worldObj)) {
                    this.player.openGui(ModularArmour.instance, GuiHandler.RECIPE_ID, this.player.worldObj, 0, 0, 0);
                }
                
            }else if(name.equals("ValueChanged")){
            	NBTHelper.createDefaultStackTag(getContainerStack());
            	
            	getContainerStack().stackTagCompound.setInteger(packet.getString(), packet.getInt());
            }
            
        } catch (UpgradeException e) {
            
        } finally {
            this.player.inventory.mainInventory[this.containerIndex] = this.getContainerStack();
        }
        
        if(shouldSync){
        	this.detectAndSendChanges();
        }
        
    }
    

    @Override
    public void onKeyTyped(ArmourPacket packet, char key, int keyCode) {
        
    }

	@Override
	public void clientLoad(ArmourPacket p){
		
	}

	@Override
	public void serverLoad(ArmourPacket p){
		
	}
    
}
