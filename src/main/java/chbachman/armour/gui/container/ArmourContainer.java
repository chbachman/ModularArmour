package chbachman.armour.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import chbachman.api.IModularItem;
import chbachman.api.IUpgrade;
import chbachman.armour.ModularArmour;
import chbachman.armour.gui.ArmourContainerWrapper;
import chbachman.armour.gui.GuiHandler;
import chbachman.armour.gui.IInputHandler;
import chbachman.armour.handler.UpgradeHandler;
import chbachman.armour.network.ArmourPacket;
import chbachman.armour.upgrade.UpgradeException;
import chbachman.armour.upgrade.UpgradeList;
import chbachman.armour.util.UpgradeUtil;
import cofh.lib.util.helpers.ItemHelper;

public class ArmourContainer extends Container implements IInputHandler{
    
    public IUpgrade upgrade = null;
    public final IModularItem item;
    public final IInventory containerWrapper;
    public final EntityPlayer player;
    public final ItemStack stack;
    public final int containerIndex;
    
    public ArmourContainer(ItemStack stack, InventoryPlayer inventory, World world) {
        this.item = (IModularItem) stack.getItem();
        this.containerWrapper = new ArmourContainerWrapper(this);
        this.containerIndex = inventory.currentItem;
        this.stack = stack;
        this.player = inventory.player;
        
        this.bindCraftingGrid();
        this.bindPlayerInventory(inventory);
    }
    
    public IInventory getWrapper() {
        return this.containerWrapper;
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
    
    public ItemStack getContainerStack() {
        return this.stack;
    }
    
    public void onSlotChanged() {
        this.upgrade = UpgradeHandler.getResult(this.containerWrapper);
    }
    
    @Override
    public void onButtonClick(ArmourPacket packet, String name) {
        
        try {
            
            if (name.equals("UpgradeAddition")) {
                if (UpgradeHandler.addUpgrade(this.stack, this.upgrade)) {
                    
                    for (int i = 0; i < this.containerWrapper.getSizeInventory(); i++) {
                        
                        this.containerWrapper.decrStackSize(i, 1);
                    }
                    
                    this.upgrade = UpgradeHandler.getResult(this.containerWrapper);;
                }
                
            } else if (name.equals("RemoveItems")) {
                this.onContainerClosed(this.player);
            } else if (name.equals("RemoveUpgrade")) {
                UpgradeUtil.removeUpgrade(this.stack, UpgradeList.list.get(packet.getInt()));
            } else if(name.equals("Recipe")){
                if (this.player.worldObj.isRemote == false) {
                    this.player.openGui(ModularArmour.instance, GuiHandler.RECIPE_ID, this.player.worldObj, 0, 0, 0);
                }
            }
            
        } catch (UpgradeException e) {
            
        } finally {
            this.player.inventory.mainInventory[this.containerIndex] = this.stack;
        }
        
    }
    

    @Override
    public void onKeyTyped(ArmourPacket packet, char key, int keyCode) {
        
    }
    
    @Override
    public void onContainerClosed(EntityPlayer par1EntityPlayer) {
        super.onContainerClosed(par1EntityPlayer);
        
        for (int i = 0; i < 9; ++i) {
            this.transferStackInSlot(par1EntityPlayer, i);
        }
        
        if (!this.player.worldObj.isRemote) {
            
            for (int i = 0; i < 9; i++) {
                ItemStack itemstack = this.containerWrapper.getStackInSlotOnClosing(i);
                
                if (itemstack != null) {
                    par1EntityPlayer.dropPlayerItemWithRandomChoice(itemstack, false);
                }
            }
        }
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
        
        ItemStack stack = null;
        Slot slot = (Slot) this.inventorySlots.get(slotIndex);
        
        int invPlayer = 27;
        int invFull = invPlayer + 9;
        int invTile = invFull + this.containerWrapper.getSizeInventory();
        
        if (slot != null && slot.getHasStack()) {
            ItemStack stackInSlot = slot.getStack();
            stack = stackInSlot.copy();
            
            if (slotIndex >= invFull) {
                if (!this.mergeItemStack(stackInSlot, 0, invFull, true)) {
                    return null;
                }
            } else {
                if (!this.mergeItemStack(stackInSlot, invFull, invTile, true)) {
                    if (slotIndex >= invPlayer) {
                        if (!this.mergeItemStack(stackInSlot, 0, invPlayer, true)) {
                            return null;
                        }
                    } else {
                        if (!this.mergeItemStack(stackInSlot, invPlayer, invFull, false)) {
                            return null;
                        }
                    }
                }
            }
            if (stackInSlot.stackSize <= 0) {
                slot.putStack((ItemStack) null);
            } else {
                slot.onSlotChanged();
            }
            if (stackInSlot.stackSize == stack.stackSize) {
                return null;
            }
            slot.onPickupFromSlot(player, stackInSlot);
        }
        return stack;
    }
    
    @Override
    public ItemStack slotClick(int slot, int invIndex, int clickType, EntityPlayer player) {
        
        return clickType == 2 && invIndex == this.containerIndex ? null : super.slotClick(slot, invIndex, clickType, player);
    }
    
    @Override
    protected boolean mergeItemStack(ItemStack stack, int slotMin, int slotMax, boolean reverse) {
        
        boolean slotFound = false;
        int k = reverse ? slotMax - 1 : slotMin;
        
        Slot slot;
        ItemStack stackInSlot;
        
        if (stack.isStackable()) {
            while (stack.stackSize > 0 && (!reverse && k < slotMax || reverse && k >= slotMin)) {
                slot = (Slot) this.inventorySlots.get(k);
                stackInSlot = slot.getStack();
                
                if (slot.isItemValid(stack) && ItemHelper.itemsEqualWithMetadata(stack, stackInSlot, true)) {
                    int l = stackInSlot.stackSize + stack.stackSize;
                    int slotLimit = Math.min(stack.getMaxStackSize(), slot.getSlotStackLimit());
                    
                    if (l <= slotLimit) {
                        stack.stackSize = 0;
                        stackInSlot.stackSize = l;
                        slot.onSlotChanged();
                        slotFound = true;
                    } else if (stackInSlot.stackSize < slotLimit) {
                        stack.stackSize -= slotLimit - stackInSlot.stackSize;
                        stackInSlot.stackSize = slotLimit;
                        slot.onSlotChanged();
                        slotFound = true;
                    }
                }
                k += reverse ? -1 : 1;
            }
        }
        if (stack.stackSize > 0) {
            k = reverse ? slotMax - 1 : slotMin;
            
            while (!reverse && k < slotMax || reverse && k >= slotMin) {
                slot = (Slot) this.inventorySlots.get(k);
                stackInSlot = slot.getStack();
                
                if (slot.isItemValid(stack) && stackInSlot == null) {
                    slot.putStack(ItemHelper.cloneStack(stack, Math.min(stack.stackSize, slot.getSlotStackLimit())));
                    slot.onSlotChanged();
                    
                    if (slot.getStack() != null) {
                        stack.stackSize -= slot.getStack().stackSize;
                        slotFound = true;
                    }
                    break;
                }
                k += reverse ? -1 : 1;
            }
        }
        return slotFound;
    }
    
}
