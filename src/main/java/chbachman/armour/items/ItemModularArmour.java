package chbachman.armour.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import chbachman.armour.ModularArmour;
import chbachman.armour.gui.GuiHandler;
import chbachman.armour.reference.ArmourSlot;
import chbachman.armour.upgrade.Upgrade;
import chbachman.armour.util.NBTHelper;
import cofh.api.item.IInventoryContainerItem;
import cofh.util.CoreUtils;


//Credit for most of this goes to King Lemming. 
public class ItemModularArmour extends ItemElectricArmour implements IInventoryContainerItem{
	
	protected int ratio = 0;
	protected int max = 0;
	
	public ItemModularArmour(ArmorMaterial material, int type) {
		super(material,type);
		this.setCreativeTab(CreativeTabs.tabCombat);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack){
		
		NBTTagList list = NBTHelper.getNBTTagList(stack.stackTagCompound);
		
		for(int i = 0; i < list.tagCount(); i++){
			Upgrade upgrade = Upgrade.getUpgradeFromNBT(list.getCompoundTagAt(i));
			
			upgrade.onArmourTick(world, player, stack, ArmourSlot.getArmourSlot(this.armorType));
		}
	}
	
	@Override
    public ItemStack onItemRightClick (ItemStack stack, World world, EntityPlayer player)
    {
		
		if(CoreUtils.isFakePlayer(player)){
			return stack;
		}
		
		if(player.isSneaking()){
			
			this.receiveEnergy(stack, 1000, false);
			return stack;
			//return super.onItemRightClick(stack, world, player);
		}
		
		if(world.isRemote == false){
			player.openGui(ModularArmour.instance, GuiHandler.ARMOUR_ID, world, 0, 0, 0);
		}
        return stack;
    }

	//IInventoryContainerItem
	@Override
	public int getSizeInventory(ItemStack container) {
		return 9;
	}

}
