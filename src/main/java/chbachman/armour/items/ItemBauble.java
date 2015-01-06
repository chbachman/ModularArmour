package chbachman.armour.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import baubles.api.BaubleType;
import baubles.api.IBauble;
import chbachman.api.IModularItem;
import chbachman.api.IUpgrade;
import chbachman.armour.ModularArmour;
import chbachman.armour.gui.GuiHandler;
import chbachman.armour.util.ArmourSlot;
import chbachman.armour.util.NBTHelper;
import chbachman.armour.util.VariableInt;
import cofh.core.util.CoreUtils;
import cofh.lib.util.helpers.StringHelper;
import cpw.mods.fml.common.Optional;

@Optional.Interface(iface = "baubles.api.IBauble", modid = "Baubles")
public class ItemBauble extends Item implements IBauble, IModularItem{

	private BaubleType type;

	private VariableInt capacity = new VariableInt("capacity", 100);
	private VariableInt maxTransfer = new VariableInt("maxTransfer", 100);
	private VariableInt energyPerDamage = new VariableInt("energyPerDamage", 100);
	public VariableInt level = new VariableInt("level", 0);

	public ItemBauble(){
		setCreativeTab(CreativeTabs.tabTools);
	}

	public ItemBauble setBaubleType(BaubleType type){
		this.type = type;
		return this;
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return this.type;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

		if (CoreUtils.isFakePlayer(player)) {
			return stack;
		}
		
		if (world.isRemote == false) {
			player.openGui(ModularArmour.instance, GuiHandler.ARMOUR_ID, world, 0, 0, 0);
		}
		return stack;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		int energy = 0;
		for(IUpgrade upgrade : NBTHelper.getNBTUpgradeList(itemstack)){
			energy += upgrade.onTick(player.worldObj, (EntityPlayer) player, itemstack, ArmourSlot.getArmourSlot(this.getSlot()), level.get(itemstack));
		}
		this.extractEnergy(itemstack, energy, false);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean check) {
		NBTHelper.createDefaultStackTag(stack);
		if (!StringHelper.isShiftKeyDown()) {
			list.add(StringHelper.shiftForDetails());
		} else {

			list.add(StringHelper.localize("info.cofh.charge") + ": " + stack.stackTagCompound.getInteger("Energy") + " / " + this.capacity.get(stack) + " RF");
		}

		if (!StringHelper.isControlKeyDown() && NBTHelper.getNBTUpgradeList(stack.stackTagCompound).size() != 0) {
			list.add(StringHelper.LIGHT_GRAY + StringHelper.localize("info.cofh.hold") + " " + StringHelper.YELLOW + StringHelper.ITALIC + StringHelper.localize("info.chbachman.control") + " " + StringHelper.END + StringHelper.LIGHT_GRAY + StringHelper.localize("info.chbachman.upgradeList") + StringHelper.END);
		} else if (NBTHelper.getNBTUpgradeList(stack.stackTagCompound).size() != 0) {
			for (IUpgrade upgrade : NBTHelper.getNBTUpgradeList(stack.stackTagCompound)) {

				list.add(upgrade.getName());

			}
		}
	}

	@Override
	public EnumRarity getRarity(ItemStack par1ItemStack) {
		return EnumRarity.rare;
	}

	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack)
	{
		return super.getUnlocalizedName() + "." + par1ItemStack.getItemDamage();
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
		if (!player.worldObj.isRemote) {
			player.worldObj.playSoundAtEntity(player, "random.orb", 0.1F, 1.3f);
		}

		for(IUpgrade upgrade : NBTHelper.getNBTUpgradeList(itemstack)){
			upgrade.onEquip(player.worldObj, (EntityPlayer) player, itemstack, ArmourSlot.getArmourSlot(this.getSlot()), level.get(itemstack));
		}
	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
		for(IUpgrade upgrade : NBTHelper.getNBTUpgradeList(itemstack)){
			upgrade.onDequip(player.worldObj, (EntityPlayer) player, itemstack, ArmourSlot.getArmourSlot(this.getSlot()), level.get(itemstack));
		}
	}

	@Override
	public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}

	@Override
	public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}

	@Override
	public int getSlot() {
		return ArmourSlot.getArmourSlot(this.type).id;
	}

	@Override
	public void onArmorDequip(World worldObj, EntityPlayer player, ItemStack stack) {

	}
	
	@Override
	public int getDisplayDamage(ItemStack stack) {
		NBTHelper.createDefaultStackTag(stack);
		return 1 + this.capacity.get(stack) - this.getEnergyStored(stack);
	}
	
	@Override
	public int getMaxDamage(ItemStack stack) {

		return 1 + this.capacity.get(stack);
	}

	@Override
	public boolean isDamaged(ItemStack stack) {

		return stack.getItemDamage() != Short.MAX_VALUE;
	}
	
	//IConfigurableElectric
	@Override
	public int getCapacity(ItemStack stack) {
		return capacity.get(stack);
	}

	@Override
	public void setCapacity(ItemStack stack, int amount) {
		capacity.set(stack, amount);
	}

	@Override
	public int getEnergyPerDamage(ItemStack stack) {
		return energyPerDamage.get(stack);
	}

	@Override
	public void setEnergyPerDamage(ItemStack stack, int amount) {
		energyPerDamage.set(stack, amount);

	}

	@Override
	public int getMaxTransfer(ItemStack stack) {
		return maxTransfer.get(stack);
	}

	@Override
	public void setMaxTransfer(ItemStack stack, int amount) {
		maxTransfer.set(stack, amount);

	}

	//IEnergyContainerItem



	public int receiveEnergy(ItemStack stack, int amount, boolean simulate)
	{
		if (stack.stackTagCompound == null) {
			stack.stackTagCompound = new NBTTagCompound();
		}
		int i = stack.stackTagCompound.getInteger("Energy");
		int j = Math.min(this.capacity.get(stack) - i, Math.min(this.maxTransfer.get(stack), amount));

		if (!(simulate)) {
			i += j;
			stack.stackTagCompound.setInteger("Energy", i);
		}
		return j;
	}


	public int extractEnergy(ItemStack stack, int amount, boolean simulate)
	{
		if ((stack.stackTagCompound == null) || (!(stack.stackTagCompound.hasKey("Energy")))) {
			return 0;
		}
		int i = stack.stackTagCompound.getInteger("Energy");
		int j = Math.min(i, Math.min(this.maxTransfer.get(stack), amount));

		if (!(simulate)) {
			i -= j;
			stack.stackTagCompound.setInteger("Energy", i);
		}
		return j;
	}


	public int getEnergyStored(ItemStack stack)
	{
		if ((stack.stackTagCompound == null) || (!(stack.stackTagCompound.hasKey("Energy")))) {
			return 0;
		}
		return stack.stackTagCompound.getInteger("Energy");
	}


	public int getMaxEnergyStored(ItemStack stack)
	{
		return this.capacity.get(stack);
	}

	@Override
	public int getLevel(ItemStack stack) {
		return level.get(stack);
	}

	@Override
	public ItemStack setLevel(ItemStack stack, int level) {
		this.level.set(stack, level);
		return stack;
	}


}
