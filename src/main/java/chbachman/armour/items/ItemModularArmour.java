package chbachman.armour.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import thaumcraft.api.IGoggles;
import thaumcraft.api.IVisDiscountGear;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.nodes.IRevealer;
import vazkii.botania.api.item.IPixieSpawner;
import vazkii.botania.api.mana.IManaItem;
import chbachman.api.IArmourUpgrade;
import chbachman.api.IModularItem;
import chbachman.api.IUpgrade;
import chbachman.armour.ModularArmour;
import chbachman.armour.gui.GuiHandler;
import chbachman.armour.reference.Reference;
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
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.Optional.Interface;

@Optional.InterfaceList(value = { 
		@Interface(iface = "thaumcraft.api.IGoggles", modid = "Thaumcraft"), 
		@Interface(iface = "thaumcraft.api.IVisDiscountGear", modid = "Thaumcraft"), 
		@Interface(iface = "thaumcraft.api.nodes.IRevealer", modid = "Thaumcraft"), 
		@Interface(iface = "vazkii.botania.api.mana.IManaItem", modid = "Botania"),
		@Interface(iface = "vazkii.botania.api.item.IPixieSpawner", modid = "Botania"),
		})
public class ItemModularArmour extends ItemArmor implements ISpecialArmor, IModularItem, IGoggles, IVisDiscountGear, IRevealer, IManaItem, IPixieSpawner{

	private VariableInt capacity = new VariableInt("capacity", 100);
	private VariableInt maxTransfer = new VariableInt("maxTransfer", 100);
	
	public VariableInt level = new VariableInt("level", 0);
	
	public ItemModularArmour(ArmorMaterial material, int type) {
		super(material, 0, type);
		this.setCreativeTab(CreativeTabs.tabCombat);
	}


	//Item
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean check) {
		NBTHelper.createDefaultStackTag(stack);
		
		list.add(StringHelper.localize("info.chbachman.level") + " " + level.get(stack));
		
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
	public int getDisplayDamage(ItemStack stack) {
		NBTHelper.createDefaultStackTag(stack);
		return 1 + this.capacity.get(stack) - this.getEnergyStored(stack);
	}
	
	@Override
	public boolean getIsRepairable(ItemStack itemToRepair, ItemStack stack) {
		return false;
	}

	@Override
	public int getMaxDamage(ItemStack stack) {

		return 1 + this.capacity.get(stack);
	}
	
	@Override
	public EnumRarity getRarity(ItemStack stack) {

		return EnumRarity.uncommon;
	}

	@Override
	public boolean isDamaged(ItemStack stack) {

		return stack.getItemDamage() != Short.MAX_VALUE;
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {

		int energy = 0;

		for (IUpgrade upgrade : NBTHelper.getNBTUpgradeList(stack.stackTagCompound)) {

			if (upgrade != null) {
				energy += upgrade.onTick(world, player, stack, ArmourSlot.getArmourSlot(this.armorType), level.get(stack));
			}

		}

		if(energy < 0){
			this.receiveEnergy(stack, energy * -1, false);
		}else{
			this.extractEnergy(stack, energy, false);
		}

	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {

		if (CoreUtils.isFakePlayer(player)) {
			return stack;
		}

		if (player.isSneaking()) {
			return super.onItemRightClick(stack, world, player);
		}

		if (world.isRemote == false) {
			player.openGui(ModularArmour.instance, GuiHandler.ARMOUR_ID, world, 0, 0, 0);
		}
		return stack;
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		
		String texture = "Modular";
		
		for(IUpgrade upgrade : NBTHelper.getNBTUpgradeList(stack.stackTagCompound)){
			
			if(!(upgrade instanceof IArmourUpgrade)){
				continue;
			}
			
			String textureLocation = ((IArmourUpgrade) upgrade).getArmourTexture(stack, slot);
			
			if(textureLocation == null){
				continue;
			}
			
			texture = textureLocation;
		}
		
		return Reference.ARMOUR_LOATION + texture + (slot == 2 ? "_2" : "_1") + ".png";
	}
	
	//ISpecialArmor
	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
		this.extractEnergy(stack, damage, false);
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack stack, int slot) {
		int sum = 0;
		for (IUpgrade upgrade : NBTHelper.getNBTUpgradeList(stack.stackTagCompound)) {
			sum += upgrade instanceof IArmourUpgrade ? ((IArmourUpgrade) upgrade).getArmourDisplay() : 0;
		}
		return sum;
	}

	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack stack, DamageSource source, double damage, int slot) {

		ArmorProperties output = new ArmorProperties(0, 0, 0);

		for (IUpgrade upgrade : NBTHelper.getNBTUpgradeList(stack.stackTagCompound)) {
			ArmorProperties prop = null;
			
			if(upgrade instanceof IArmourUpgrade){
				prop = ((IArmourUpgrade) upgrade).getProperties(player, stack, source, damage, ArmourSlot.getArmourSlot(slot));
			}

			if (prop == null) {
				continue;
			}

			if (prop.Priority > output.Priority) {
				output = prop;
			} else if (prop.Priority == output.Priority && prop.AbsorbRatio > output.AbsorbRatio) {
				output = prop;
			}

		}

		return new ArmorProperties(output.Priority, output.AbsorbRatio, Integer.MAX_VALUE);
	}
	
	//IModularItem
	@Override
	public int getSlot() {
		return this.armorType;
	}
	
	public boolean isArmour(){
		return true;
	}
	
	public void onArmourDequip(World world, EntityPlayer player, ItemStack stack) {
		for (IUpgrade upgrade : NBTHelper.getNBTUpgradeList(stack.stackTagCompound)) {
			upgrade.onDequip(world, player, stack, ArmourSlot.getArmourSlot(this.armorType), level.get(stack));
		}
	}
	
	public void onArmourEquip(World world, EntityPlayer player, ItemStack stack){
		for (IUpgrade upgrade : NBTHelper.getNBTUpgradeList(stack.stackTagCompound)) {
			upgrade.onEquip(world, player, stack, ArmourSlot.getArmourSlot(this.armorType), level.get(stack));
		}
	}
	
	@Override
	public ItemStack setLevel(ItemStack stack, int level) {
		this.level.set(stack, level);
		return stack;
	}
	
	//IRevealer
	@Override
	@Optional.Method(modid = "Thaumcraft")
	public boolean showNodes(ItemStack itemstack, EntityLivingBase player) {
		NBTList<IUpgrade> list = NBTHelper.getNBTUpgradeList(itemstack);

		return list.contains(Thaumcraft.gogglesOfRevealing);
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

	//IGoggles
	@Override
	@Optional.Method(modid = "Thaumcraft")
	public boolean showIngamePopups(ItemStack itemstack, EntityLivingBase player) {
		NBTList<?> list = NBTHelper.getNBTUpgradeList(itemstack);

		return list.contains(Thaumcraft.gogglesOfRevealing);
	}

	
	//IEnergyContainerItem
	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
		NBTHelper.createDefaultStackTag(container);
		
		int energy = container.stackTagCompound.getInteger("Energy");
		int energyReceived = Math.min(capacity.get(container) - energy, Math.min(this.maxTransfer.get(container), maxReceive));

		if (!simulate) {
			energy += energyReceived;
			container.stackTagCompound.setInteger("Energy", energy);
		}
		return energyReceived;
	}

	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {
		NBTHelper.createDefaultStackTag(container);
		int energy = container.stackTagCompound.getInteger("Energy");
		int energyExtracted = Math.min(energy, Math.min(this.maxTransfer.get(container) , maxExtract));

		if (!simulate) {
			energy -= energyExtracted;
			container.stackTagCompound.setInteger("Energy", energy);
		}
		return energyExtracted;
	}

	@Override
	public int getEnergyStored(ItemStack container) {
		NBTHelper.createDefaultStackTag(container);
		return container.stackTagCompound.getInteger("Energy");
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {
		return this.capacity.get(container) ;
	}
	
	//IConfigurableElectric

	@Override
	public int getCapacity(ItemStack stack) {
		return this.capacity.get(stack);
	}

	@Override
	public void setCapacity(ItemStack stack, int amount) {
		this.capacity.set(stack, amount);
	}

	@Override
	public int getMaxTransfer(ItemStack stack) {
		return this.maxTransfer.get(stack);
	}

	@Override
	public void setMaxTransfer(ItemStack stack, int amount) {
		this.maxTransfer.set(stack, amount);
	}


	@Override
	public int getLevel(ItemStack stack) {
		return level.get(stack);
	}
	
	static int rfToMana = Loader.isModLoaded("Botania") ? ConfigHelper.get(ConfigHelper.SPEED,Botania.manaConverter, "RF to Mana Conversion Factor", 10) : 0;

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

}
