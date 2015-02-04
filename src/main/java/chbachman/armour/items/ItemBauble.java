package chbachman.armour.items;

import java.util.List;

import static chbachman.armour.items.ItemModularArmour.rfToMana;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import thaumcraft.api.IVisDiscountGear;
import thaumcraft.api.aspects.Aspect;
import vazkii.botania.api.item.IPixieSpawner;
import vazkii.botania.api.mana.IManaItem;
import baubles.api.BaubleType;
import baubles.api.IBauble;
import chbachman.api.IModularItem;
import chbachman.api.IUpgrade;
import chbachman.armour.ModularArmour;
import chbachman.armour.gui.GuiHandler;
import chbachman.armour.register.Botania;
import chbachman.armour.register.Thaumcraft;
import chbachman.armour.util.ArmourSlot;
import chbachman.armour.util.ConfigHelper;
import chbachman.armour.util.NBTHelper;
import chbachman.armour.util.NBTList;
import chbachman.armour.util.UpgradeUtil;
import chbachman.armour.util.VariableInt;
import cofh.core.util.CoreUtils;
import cofh.lib.util.helpers.StringHelper;
import cpw.mods.fml.common.Optional;

@Optional.Interface(iface = "baubles.api.IBauble", modid = "Baubles")
public class ItemBauble extends Item implements IBauble, IModularItem, IVisDiscountGear, IManaItem, IPixieSpawner{

	private BaubleType type;

	private VariableInt capacity = new VariableInt("capacity", 100);
	private VariableInt maxTransfer = new VariableInt("maxTransfer", 100);
	public VariableInt level = new VariableInt("level", 0);

	public ItemBauble(){
		setCreativeTab(CreativeTabs.tabTools);
	}

	/**
	 * Sets the type of the bauble. 
	 * @param type
	 * @return The bauble, for chaining.
	 */
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

		switch(type){

		case BELT: return 4;
		case RING: return 5;
		case AMULET: return 6;
		default: return 7;

		}
	}

	@Override
	public void onArmourDequip(World worldObj, EntityPlayer player, ItemStack stack) {

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
	
	@Override
	public int getCapacity(ItemStack stack) {
		return capacity.get(stack);
	}

	@Override
	public void setCapacity(ItemStack stack, int amount) {
		capacity.set(stack, amount);
	}

	@Override
	public int getMaxTransfer(ItemStack stack) {
		return maxTransfer.get(stack);
	}

	@Override
	public void setMaxTransfer(ItemStack stack, int amount) {
		maxTransfer.set(stack, amount);

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

	@Override
	public void onArmourEquip(World worldObj, EntityPlayer player, ItemStack stack) {
		
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
	public boolean isArmour() {
		return false;
	}


	//IManaItem
	@Override
	@Optional.Method(modid = "Botania")
	public int getMana(ItemStack stack) {
		if(!UpgradeUtil.doesItemStackContainUpgrade(stack, Botania.manaConverter)){
			return 0;
		}
		return this.getEnergyStored(stack) / rfToMana;
	}


	@Override
	@Optional.Method(modid = "Botania")
	public int getMaxMana(ItemStack stack) {
		if(!UpgradeUtil.doesItemStackContainUpgrade(stack, Botania.manaConverter)){
			return 0;
		}
		return this.getCapacity(stack) / rfToMana;
	}


	@Override
	@Optional.Method(modid = "Botania")
	public void addMana(ItemStack stack, int mana) {
		if(!UpgradeUtil.doesItemStackContainUpgrade(stack, Botania.manaConverter)){
			return;
		}
		this.receiveEnergy(stack, mana * rfToMana, false);
	}


	@Override
	@Optional.Method(modid = "Botania")
	public boolean canReceiveManaFromPool(ItemStack stack, TileEntity pool) {
		if(!UpgradeUtil.doesItemStackContainUpgrade(stack, Botania.manaConverter)){
			return false;
		}
		return this.getMaxEnergyStored(stack) != this.getEnergyStored(stack);
	}


	@Override
	@Optional.Method(modid = "Botania")
	public boolean canReceiveManaFromItem(ItemStack stack, ItemStack otherStack) {
		if(!UpgradeUtil.doesItemStackContainUpgrade(stack, Botania.manaConverter)){
			return false;
		}
		return this.getCapacity(stack) != this.getEnergyStored(stack);
	}


	@Override
	@Optional.Method(modid = "Botania")
	public boolean canExportManaToPool(ItemStack stack, TileEntity pool) {
		return false;
	}


	@Override
	@Optional.Method(modid = "Botania")
	public boolean canExportManaToItem(ItemStack stack, ItemStack otherStack) {
		return false;
	}


	@Override
	@Optional.Method(modid = "Botania")
	public boolean isNoExport(ItemStack stack) {
		return true;
	}
	
	
	//IPixieSpawner
	@Override
	@Optional.Method(modid = "Botania")
	public float getPixieChance(ItemStack stack) {
		return ConfigHelper.get(ConfigHelper.SPEED,Botania.pixie, "Pixie Chance", .05F);
	}

	//IVisDiscountGear
		@Override
		@Optional.Method(modid = "Thaumcraft")
		public int getVisDiscount(ItemStack stack, EntityPlayer player, Aspect aspect) {
			NBTList<IUpgrade> list = NBTHelper.getNBTUpgradeList(stack);
			
			if(list.contains(Thaumcraft.visDiscount)){
				if(stack.getItem() instanceof IModularItem){
					IModularItem armour = (IModularItem) stack.getItem();
					
					switch(ArmourSlot.getArmourSlot(armour.getSlot())){
					case BELT: return 4;
					case BOOTS: return 2;
					case CHESTPLATE: return 5;
					case HELMET: return 3;
					case LEGS: return 4;
					case PENDANT: return 3;
					case RING: return 1;
					default: return 0;
					}
				}
			}
			
			return 0;
		}



}
