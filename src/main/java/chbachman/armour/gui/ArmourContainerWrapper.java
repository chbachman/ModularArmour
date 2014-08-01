package chbachman.armour.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import chbachman.armour.gui.container.ArmourContainer;

public class ArmourContainerWrapper implements IInventory {
    
    private ItemStack[] inventory;
    private ArmourContainer container;
    
    public ArmourContainerWrapper(ArmourContainer container) {
        this.inventory = new ItemStack[9];
        this.container = container;
    }
    
    @Override
    public int getSizeInventory() {
        return 9;
    }
    
    @Override
    public ItemStack getStackInSlot(int par1) {
        return this.inventory[par1];
    }
    
    @Override
    public ItemStack decrStackSize(int par1, int par2) {
        if (this.inventory[par1] != null) {
            ItemStack itemstack;
            
            if (this.inventory[par1].stackSize <= par2) {
                itemstack = this.inventory[par1];
                this.inventory[par1] = null;
                return itemstack;
            } else {
                itemstack = this.inventory[par1].splitStack(par2);
                
                if (this.inventory[par1].stackSize == 0) {
                    this.inventory[par1] = null;
                }
                
                return itemstack;
            }
        } else {
            return null;
        }
    }
    
    @Override
    public ItemStack getStackInSlotOnClosing(int par1) {
        if (this.inventory[par1] != null) {
            ItemStack itemstack = this.inventory[par1];
            this.inventory[par1] = null;
            return itemstack;
        } else {
            return null;
        }
    }
    
    @Override
    public void setInventorySlotContents(int var1, ItemStack var2) {
        this.inventory[var1] = var2;
    }
    
    @Override
    public String getInventoryName() {
        return "ArmourContainerWrapper";
    }
    
    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }
    
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }
    
    @Override
    public void markDirty() {
        this.container.onSlotChanged();
    }
    
    @Override
    public boolean isUseableByPlayer(EntityPlayer var1) {
        return true;
    }
    
    @Override
    public void openInventory() {
    }
    
    @Override
    public void closeInventory() {
    }
    
    @Override
    public boolean isItemValidForSlot(int var1, ItemStack var2) {
        return true;
    }
    
}
