package chbachman.armour.gui.recipe;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import chbachman.api.IModularItem;
import chbachman.armour.ModularArmour;
import chbachman.armour.crafting.Recipe;
import chbachman.armour.gui.GuiHandler;
import chbachman.armour.gui.IInputHandler;
import chbachman.armour.network.ArmourPacket;
import cofh.lib.gui.slot.SlotViewOnly;

public class ArmourContainerRecipe extends Container implements IInputHandler{

    public final IModularItem item;
    public final EntityPlayer player;
    public final ItemStack stack;
    public Inventory inventory;
    public Recipe recipe;
    public int index = 0;
    
    public ArmourContainerRecipe(ItemStack stack, InventoryPlayer inventory, World world) {
        this.item = (IModularItem) stack.getItem();
        this.stack = stack;
        this.player = inventory.player;
        this.recipe = Recipe.craftingList.get(0);
        this.inventory = new Inventory();
        
        
        
        this.bindCraftingGrid();
    }
    
    protected void bindCraftingGrid() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.addSlotToContainer(new SlotViewOnly(inventory, i * 3 + j, 9 + j * 18, 17 + i * 18));
            }
        }
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

    @Override
    public void onButtonClick(ArmourPacket packet, String name) {
        this.index = packet.getInt();
        this.recipe = Recipe.craftingList.get(index);
    }

    @Override
    public void onKeyTyped(ArmourPacket packet, char key, int keyCode) {
        if (this.player.worldObj.isRemote == false) {
            this.player.openGui(ModularArmour.instance, GuiHandler.ARMOUR_ID, this.player.worldObj, 0, 0, 0);
        }
    }
    
    private class Inventory implements IInventory{
        
        private int counter = 0;
        private int index = 0;
        
        @Override
        public int getSizeInventory() {
            return 9;
        }

        @Override
        public ItemStack getStackInSlot(int slot) {
        	
        	Object obj = recipe.getInput()[slot];
        	
        	counter++;
        	
        	if(counter == 500){
        		counter = 0;
        		index++;
        	}
        	
        	if(obj instanceof ItemStack){
        		ItemStack stack =  (ItemStack) obj;
        		stack.stackSize = 1;
        		return stack;
        	}
        	
        	if(obj instanceof ArrayList){
        		@SuppressWarnings("unchecked")
				ArrayList<ItemStack> list = (ArrayList<ItemStack>) obj;
        		ItemStack stack = list.get(index % list.size());
        		stack.stackSize = 1;
        		return stack;
        		
        	}
        	
        	return null;
        	
        }

        @Override
        public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
            return null;
        }

        @Override
        public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
            return null;
        }

        @Override
        public void setInventorySlotContents(int slot, ItemStack stack) {}

        @Override
        public String getInventoryName() {
            return "inventory";
        }

        @Override
        public boolean hasCustomInventoryName() {
            return false;
        }

        @Override
        public int getInventoryStackLimit() {
            return 1;
        }

        @Override
        public void markDirty() {}

        @Override
        public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
            return true;
        }

        @Override
        public void openInventory() {}

        @Override
        public void closeInventory() {}

        @Override
        public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
            return true;
        }

    }
    
}
