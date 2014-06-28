package chbachman.armour.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import util.ItemStackHelper;
import chbachman.armour.ModularArmour;
import chbachman.armour.gui.GuiHandler;
import chbachman.armour.upgrade.Upgrade;
import cofh.api.energy.IEnergyContainerItem;
import cofh.api.item.IInventoryContainerItem;
import cofh.item.ItemArmorAdv;
import cofh.util.CoreUtils;
import cofh.util.EnergyHelper;


//Credit for most of this goes to King Lemming. 
public class ItemModularArmour extends ItemArmorAdv implements IInventoryContainerItem, ISpecialArmor, IEnergyContainerItem{
	
	public static final ArmorProperties USELESS = new ArmorProperties(0, 0.0D, 0);
	
	protected int ratio = 0;
	protected int max = 0;
	
	
	protected int capacity = 100;
	protected int maxTransfer = 10;
	
	protected int energyPerDamage = 0;
	
	public ItemModularArmour(ArmorMaterial material, int type) {
		super(material,type);
		this.setCreativeTab(CreativeTabs.tabCombat);
	}
	
	public void addUpgrade(ItemStack stack, Upgrade upgrade){
		if(stack.stackTagCompound == null){
			stack.stackTagCompound =  ItemStackHelper.createStackTagCompound();
		}
		
		ItemStackHelper.getNBTTagList(stack.stackTagCompound).appendTag(upgrade.getNBT());
	}
	
	public NBTTagList getNBTTagList(ItemStack stack){
		if(stack.stackTagCompound == null){
			stack.stackTagCompound =  ItemStackHelper.createStackTagCompound();
		}
		
		return ItemStackHelper.getNBTTagList(stack.stackTagCompound);
	}
	
	public List<Upgrade> getUpgradeList(ItemStack stack){
		
		if(stack.stackTagCompound == null){
			stack.stackTagCompound =  ItemStackHelper.createStackTagCompound();
		}
		
		return ItemStackHelper.getUpgradeListFromNBT(stack.stackTagCompound);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack){
		
		NBTTagList list = this.getNBTTagList(stack);
		
		for(int i = 0; i < list.tagCount(); i++){
			Upgrade upgrade = Upgrade.getUpgradeFromNBT(list.getCompoundTagAt(i));
			
			upgrade.onArmourTick(world, player, stack);
		}
	}
	
	@Override
	public boolean getIsRepairable(ItemStack itemToRepair, ItemStack stack){
		return false;
	}
	
	@Override
	public int getDisplayDamage(ItemStack stack) {

		if (stack.stackTagCompound == null) {
			EnergyHelper.setDefaultEnergyTag(stack, 0);
		}
		return 1 + capacity - stack.stackTagCompound.getInteger("Energy");
	}
	
	@Override
	public int getMaxDamage(ItemStack stack) {

		return 1 + capacity;
	}
	
	@Override
	public boolean isDamaged(ItemStack stack) {

		return stack.getItemDamage() != Short.MAX_VALUE;
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
	
	@Override
	public EnumRarity getRarity(ItemStack stack) {

		return EnumRarity.uncommon;
	}
	
	// IEnergyContainerItem 
	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {

		if (container.stackTagCompound == null) {
			EnergyHelper.setDefaultEnergyTag(container, 0);
		}
		int stored = container.stackTagCompound.getInteger("Energy");
		int receive = Math.min(maxReceive, Math.min(capacity - stored, maxTransfer));

		if (!simulate) {
			stored += receive;
			container.stackTagCompound.setInteger("Energy", stored);
		}
		return receive;
	}

	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {

		if (container.stackTagCompound == null) {
			EnergyHelper.setDefaultEnergyTag(container, 0);
		}
		int stored = container.stackTagCompound.getInteger("Energy");
		int extract = Math.min(maxExtract, stored);

		if (!simulate) {
			stored -= extract;
			container.stackTagCompound.setInteger("Energy", stored);
		}
		return extract;
	}

	@Override
	public int getEnergyStored(ItemStack container) {

		if (container.stackTagCompound == null) {
			EnergyHelper.setDefaultEnergyTag(container, 0);
		}
		return container.stackTagCompound.getInteger("Energy");
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {

		return capacity;
	}
	
	//ISpecialArmor
	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
		if(source.isUnblockable()){
			return USELESS;
		}
		
		return new ArmorProperties(0,ratio,max);
	}


	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		if (getEnergyStored(armor) >= energyPerDamage) {
			int sum = 0;
			
			NBTTagList nbt = this.getNBTTagList(armor);
			
			for(int i = 0; i < nbt.tagCount(); i++){
				sum += Upgrade.getUpgradeFromNBT(nbt.getCompoundTagAt(i)).getArmourDisplay();
			}
			return sum;
		}
		return 0;
	}


	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
		extractEnergy(stack, damage * energyPerDamage, false);
	}

	//IInventoryContainerItem
	@Override
	public int getSizeInventory(ItemStack container) {
		return 9;
	}

}
