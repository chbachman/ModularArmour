package chbachman.armour.gui.recipe;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import chbachman.api.item.IModularItem;
import chbachman.api.registry.ModularItemRegistry;
import chbachman.armour.ModularArmour;
import chbachman.armour.crafting.Recipe;
import chbachman.armour.gui.GuiHandler;
import chbachman.armour.gui.crafting.ArmourContainer;
import chbachman.armour.network.ArmourPacket;
import chbachman.armour.network.IInputHandler;
import chbachman.armour.util.InventoryUtil;
import cofh.lib.gui.slot.SlotViewOnly;

public class ArmourContainerRecipe extends Container implements IInputHandler{

	public final IModularItem item;
	public final EntityPlayer player;
	public final ItemStack stack;

	public Inventory inventory;
	public static Inventory2 inventory2;
	public Recipe recipe;
	public int index = 0;

	public static final List<IModularItem> modularItems = ModularItemRegistry.getUpgradeList();

	public ArmourContainerRecipe(ItemStack stack, InventoryPlayer inventory, World world) {
		this.item = (IModularItem) stack.getItem();
		this.stack = stack;
		this.player = inventory.player;
		this.recipe = Recipe.recipeList.get(0);
		this.inventory = new Inventory(recipe);
		inventory2 = new Inventory2(modularItems);

		this.bindSlots();
	}

	protected void bindSlots(){
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++){
				this.addSlotToContainer(new SlotViewOnly(inventory, i * 3 + j, 9 + j * 18, 17 + i * 18));
			}
		}

		for (int i = 9; i < 9 + modularItems.size(); i++){
			this.addSlotToContainer(new SlotViewOnly(inventory2, i, 9, 17 + i * 18));
		}

	}

	@Override
	public boolean canInteractWith(EntityPlayer player){
		return true;
	}

	@Override
	public void onButtonClick(ArmourPacket packet, String name){

		if (name.equals("Upgrade")){

			if (checkInventory()){
				ItemStack[] stacks = grabInventory();

				if (this.player.worldObj.isRemote == false){
					this.player.openGui(ModularArmour.instance, GuiHandler.ARMOUR_ID, this.player.worldObj, 0, 0, 0);
				}

				Container playerContainer = this.player.openContainer;

				if (playerContainer instanceof ArmourContainer){
					ArmourContainer crafting = (ArmourContainer) playerContainer;

					crafting.putStacksInSlots(stacks);
					crafting.onSlotChanged();
				}
			}

		}else{
			this.index = packet.getInt();
			this.recipe = Recipe.recipeList.get(index % Recipe.recipeList.size());
			this.inventory = new Inventory(this.recipe);
		}

	}

	private ItemStack[] grabInventory(){
		IInventory playerInventory = this.player.inventory;

		ItemStack[][] input = recipe.getItemStackInput();

		ItemStack[] toReturn = new ItemStack[input.length];

		for (int i = 0; i < input.length; i++){
			for (int g = 0; g < input[i].length; g++){

				for (int k = 0; k < playerInventory.getSizeInventory(); k++){
					ItemStack playerStack = playerInventory.getStackInSlot(k);

					if (InventoryUtil.itemMatches(input[i][g], playerStack, false)){

						toReturn[i] = playerInventory.decrStackSize(k, 1);

					}
				}
			}
		}

		return toReturn;
	}

	private boolean checkInventory(){
		IInventory playerInventory = this.player.inventory;

		ItemStack[][] input = recipe.getItemStackInput();
		
		for (int i = 0; i < input.length; i++){
			for (int g = 0; g < input[i].length; g++){
				if (!InventoryUtil.doesInventoryContainItemStack(playerInventory, input[i][g])){
					return false;
				}
			}
		}
		
		return true;
	}

	@Override
	public void onKeyTyped(ArmourPacket packet, char key, int keyCode){
		if (this.player.worldObj.isRemote == false){
			this.player.openGui(ModularArmour.instance, GuiHandler.ARMOUR_ID, this.player.worldObj, 0, 0, 0);
		}

	}

	private class Inventory implements IInventory{

		private int counter = 0;
		private int index = 0;

		private int amountOfItems;
		
		public Inventory(Recipe recipe) {
			this.amountOfItems = recipe.getItemStackInput().length;
		}

		@Override
		public int getSizeInventory(){
			return 9;
		}

		@Override
		public ItemStack getStackInSlot(int slot){

			ItemStack[][] items = recipe.getItemStackInput();
			
			ItemStack[] toCheck = items[slot];
			
			counter++;

			if (counter == 500 * amountOfItems){
				counter = 0;
				index++;
			}
			
			if(toCheck.length == 0){
				return null;
			}
			
			ItemStack stack = toCheck[index % toCheck.length];
			stack.stackSize = 1;
			return stack;

		}

		@Override
		public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_){
			return null;
		}

		@Override
		public ItemStack getStackInSlotOnClosing(int p_70304_1_){
			return null;
		}

		@Override
		public void setInventorySlotContents(int slot, ItemStack stack){}

		@Override
		public String getInventoryName(){
			return "inventory";
		}

		@Override
		public boolean hasCustomInventoryName(){
			return false;
		}

		@Override
		public int getInventoryStackLimit(){
			return 1;
		}

		@Override
		public void markDirty(){}

		@Override
		public boolean isUseableByPlayer(EntityPlayer p_70300_1_){
			return true;
		}

		@Override
		public void openInventory(){}

		@Override
		public void closeInventory(){}

		@Override
		public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_){
			return true;
		}

	}

	private class Inventory2 implements IInventory{

		List<ItemStack> stacks;

		public Inventory2(Iterable<IModularItem> modularitems) {
			for(IModularItem i : modularitems){
				stacks.add(new ItemStack(i.getItem()));
			}
		}

		@Override
		public int getSizeInventory(){
			return stacks.size();
		}

		@Override
		public ItemStack getStackInSlot(int slot){

			return stacks.get(slot - 9);

		}

		@Override
		public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_){
			return null;
		}

		@Override
		public ItemStack getStackInSlotOnClosing(int p_70304_1_){
			return null;
		}

		@Override
		public void setInventorySlotContents(int slot, ItemStack stack){}

		@Override
		public String getInventoryName(){
			return "inventory";
		}

		@Override
		public boolean hasCustomInventoryName(){
			return false;
		}

		@Override
		public int getInventoryStackLimit(){
			return 1;
		}

		@Override
		public void markDirty(){}

		@Override
		public boolean isUseableByPlayer(EntityPlayer p_70300_1_){
			return true;
		}

		@Override
		public void openInventory(){}

		@Override
		public void closeInventory(){}

		@Override
		public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_){
			return true;
		}

	}

}
