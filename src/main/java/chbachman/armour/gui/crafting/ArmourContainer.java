package chbachman.armour.gui.crafting;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import chbachman.api.configurability.Percentage;
import chbachman.api.item.IModularItem;
import chbachman.api.nbt.helper.NBTHelper;
import chbachman.api.nbt.helper.NBTStorage;
import chbachman.api.nbt.serializers.PercentageNBT;
import chbachman.api.registry.UpgradeRegistry;
import chbachman.api.upgrade.IUpgrade;
import chbachman.armour.ModularArmour;
import chbachman.armour.gui.GuiHandler;
import chbachman.armour.handler.UpgradeHandler;
import chbachman.armour.network.ArmourPacket;
import chbachman.armour.network.ArmourPacket.PacketTypes;
import chbachman.armour.network.IContainerSyncable;
import chbachman.armour.network.IInputHandler;
import chbachman.armour.upgrade.UpgradeException;
import chbachman.armour.util.MiscUtil;
import chbachman.armour.util.UpgradeUtil;
import cofh.core.network.PacketHandler;
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
    
    @Override
	public void onSlotChanged() {
    	super.onSlotChanged();
        this.upgrade = UpgradeHandler.getResult(this.containerWrapper);
    }
    
    NBTStorage<Percentage> storage = new NBTStorage<Percentage>(new PercentageNBT());
    
    @Override
    public void onButtonClick(ArmourPacket packet, String name) {
    	
    	boolean shouldSync = false;
    	
        try {
            
            if (name.equals("UpgradeAddition")) {
                if (UpgradeHandler.addUpgrade(this.getContainerStack(), this.upgrade)) {
                    
                	this.upgrade = UpgradeHandler.getResult(this.containerWrapper);
                	
                	for (int i = 0; i < this.containerWrapper.getSizeInventory(); ++i)
                    {
                        ItemStack itemstack1 = this.containerWrapper.getStackInSlot(i);

                        if (itemstack1 != null)
                        {
                            this.containerWrapper.decrStackSize(i, 1);

                            if (itemstack1.getItem().hasContainerItem(itemstack1))
                            {
                                ItemStack itemstack2 = itemstack1.getItem().getContainerItem(itemstack1);

                                if (itemstack2 != null && itemstack2.isItemStackDamageable() && itemstack2.getItemDamage() > itemstack2.getMaxDamage())
                                {
                                    MinecraftForge.EVENT_BUS.post(new PlayerDestroyItemEvent(this.player, itemstack2));
                                    continue;
                                }

                                if (!itemstack1.getItem().doesContainerItemLeaveCraftingGrid(itemstack1) || !this.player.inventory.addItemStackToInventory(itemstack2))
                                {
                                    if (this.containerWrapper.getStackInSlot(i) == null)
                                    {
                                        this.containerWrapper.setInventorySlotContents(i, itemstack2);
                                    }
                                    else
                                    {
                                        this.player.dropPlayerItemWithRandomChoice(itemstack2, false);
                                    }
                                }
                            }
                        }
                    }
                    
                    
                    
                    shouldSync = true;
                }
                
            } else if (name.equals("RemoveItems")) {
            	shouldSync = true;
            	
                for(int i = 0; i < 9; i++){
                	this.transferStackInSlot(this.player, i);
                }
                
            } else if (name.equals("RemoveUpgrade")) {
                UpgradeUtil.removeUpgrade(this.getContainerStack(), UpgradeRegistry.getUpgrade(packet.getString()));
                
                shouldSync = true;
            } else if(name.equals("Recipe")){
            	
                if (MiscUtil.isServer(this.player.worldObj)) {
                    this.player.openGui(ModularArmour.instance, GuiHandler.RECIPE_ID, this.player.worldObj, 0, 0, 0);
                }
                
            }else if(name.equals("ValueChanged")){
            	NBTHelper.createDefaultStackTag(getContainerStack());
            	
            	storage.setKey(packet.getString());
            	
            	storage.set(getContainerStack(), new Percentage(packet.getInt()));
            }
            
            
        } catch (UpgradeException e) {
            
        } finally {
            this.player.inventory.mainInventory[this.containerIndex] = this.getContainerStack();
        }
        
        if(shouldSync){
        	this.detectAndSendChanges();
        	PacketHandler.sendTo(ArmourPacket.getPacket(PacketTypes.CONTAINERSYNC).addItemStack(this.getContainerStack()), player);
        }
        
    }

    @Override
    public void onKeyTyped(ArmourPacket packet, char key, int keyCode) {
        
    }

	@Override
	public void clientLoad(ArmourPacket p){
		ItemStack container = p.getItemStack();
		
		this.getContainerStack().stackTagCompound = container.stackTagCompound;
	}

	@Override
	public void serverLoad(ArmourPacket p){
		
	}
    
}
