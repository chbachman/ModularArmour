package chbachman.armour.gui.container;

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
    public final Inventory inventory;
    public Recipe recipe;
    public int index = 0;
    
    public ArmourContainerRecipe(ItemStack stack, InventoryPlayer inventory, World world) {
        this.item = (IModularItem) stack.getItem();
        this.stack = stack;
        this.player = inventory.player;
        this.recipe = Recipe.craftinglist.get(0);
        this.inventory = new Inventory(recipe);
        
        
        
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
        this.inventory.setRecipe(Recipe.craftinglist.get(index));
    }

    @Override
    public void onKeyTyped(ArmourPacket packet, char key, int keyCode) {
        if (this.player.worldObj.isRemote == false) {
            this.player.openGui(ModularArmour.instance, GuiHandler.ARMOUR_ID, this.player.worldObj, 0, 0, 0);
        }
    }
    
    private class Inventory implements IInventory{
        
        ItemStack[] inventory;
        
        public Inventory(Recipe recipe){
            this.inventory = new ItemStack[9];
            this.setRecipe(recipe);
        }

        @Override
        public int getSizeInventory() {
            return 9;
        }

        @Override
        public ItemStack getStackInSlot(int slot) {
            return recipe.getRecipe()[slot];
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
        public void setInventorySlotContents(int slot, ItemStack stack) {
            inventory[slot] = stack;
            this.markDirty();
        }

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
        
        public void setRecipe(Recipe recipe){
            ItemStack[] stack = recipe.getRecipe();
            for(int i = 0; i < 9; i++){
                this.setInventorySlotContents(i, stack[i]);
            }
        }
    }
    
}
