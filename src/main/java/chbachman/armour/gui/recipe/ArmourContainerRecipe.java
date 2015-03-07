package chbachman.armour.gui.recipe;

import java.util.ArrayList;
import java.util.List;

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
import chbachman.armour.gui.crafting.ArmourContainer;
import chbachman.armour.network.ArmourPacket;
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

	public static ItemStack[] modularItems = ModularArmour.modularHandler.getListOfItems();

	public ArmourContainerRecipe(ItemStack stack, InventoryPlayer inventory, World world) {
		this.item = (IModularItem) stack.getItem();
		this.stack = stack;
		this.player = inventory.player;
		this.recipe = Recipe.recipeList.get(0);
		this.inventory = new Inventory();
		inventory2 = new Inventory2(modularItems);

		this.bindSlots();
	}

	protected void bindSlots(){
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 3; j++){
				this.addSlotToContainer(new SlotViewOnly(inventory, i * 3 + j, 9 + j * 18, 17 + i * 18));
			}
		}

		for (int i = 9; i < 9 + modularItems.length; i++){
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

					crafting.containerWrapper.setInventory(stacks);
					crafting.onSlotChanged();
				}
			}

		}else{
			this.index = packet.getInt();
			this.recipe = Recipe.recipeList.get(index % Recipe.recipeList.size());
		}

	}

	private ItemStack[] grabInventory(){
		ItemStack[] playerInventory = this.player.inventory.mainInventory;

		ItemStack[] toReturn = new ItemStack[recipe.getInput().length];
		int i = 0;

		for (Object obj : recipe.getInput()){

			if (obj == null){
				toReturn[i] = null;
			}

			if (obj instanceof ItemStack){
				ItemStack toCheck = (ItemStack) obj;

				ItemStack playerStack = InventoryUtil.getItemStackFromInventory(playerInventory, toCheck);

				ItemStack output = playerStack.copy();

				output.stackSize = 1;

				playerStack.stackSize--;

				if (playerStack.stackSize == 0){
					playerStack = null;
				}

				toReturn[i] = output;

			}

			if (obj instanceof List){
				@SuppressWarnings("unchecked")
				List<ItemStack> list = (List<ItemStack>) obj;

				for (ItemStack stack : list){

					ItemStack playerStack = InventoryUtil.getItemStackFromInventory(playerInventory, stack);

					if (playerStack == null){
						continue;
					}

					ItemStack output = playerStack.copy();

					output.stackSize = 1;

					playerStack.stackSize--;

					if (playerStack.stackSize == 0){
						playerStack = null;
					}

					toReturn[i] = output;

				}
			}

			i++;
		}

		return toReturn;
	}

	private boolean checkInventory(){
		ItemStack[] playerInventory = this.player.inventory.mainInventory;

		for (Object obj : recipe.getInput()){

			if (obj == null){
				continue;
			}

			if (obj instanceof ItemStack){
				ItemStack toCheck = (ItemStack) obj;

				if (!InventoryUtil.doesInventoryContainItemStack(playerInventory, toCheck)){
					return false;
				}

				continue;
			}

			if (obj instanceof List){
				@SuppressWarnings("unchecked")
				List<ItemStack> list = (List<ItemStack>) obj;

				boolean matched = false;

				for (ItemStack stack : list){

					if (InventoryUtil.doesInventoryContainItemStack(playerInventory, stack)){
						matched = true;
					}

				}

				if (!matched){
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

		public Inventory() {
			for (int i = 0; i < 9; i++){
				if (this.getStackInSlot(i) != null){
					amountOfItems++;
				}
			}
		}

		@Override
		public int getSizeInventory(){
			return 9;
		}

		@Override
		public ItemStack getStackInSlot(int slot){

			Object obj = recipe.getInput()[slot];

			counter++;

			if (counter == 500 * amountOfItems){
				counter = 0;
				index++;
			}

			if (obj instanceof ItemStack){
				ItemStack stack = (ItemStack) obj;
				stack.stackSize = 1;
				return stack;
			}

			if (obj instanceof ArrayList){
				@SuppressWarnings("unchecked")
				ArrayList<ItemStack> list = (ArrayList<ItemStack>) obj;
				ItemStack stack = list.get(index % list.size());
				stack.stackSize = 1;
				return stack;

			}

			return null;

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

		ItemStack[] stacks;

		public Inventory2(ItemStack[] stacks) {
			this.stacks = stacks;
		}

		@Override
		public int getSizeInventory(){
			return stacks.length;
		}

		@Override
		public ItemStack getStackInSlot(int slot){

			return stacks[slot - 9];

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
