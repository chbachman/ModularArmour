package chbachman.armour.items;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import chbachman.api.IModularItem;
import chbachman.api.IUpgrade;
import chbachman.armour.ModularArmour;
import chbachman.armour.gui.GuiHandler;
import chbachman.armour.objects.VariableInt;
import chbachman.armour.reference.ArmourSlot;
import chbachman.armour.util.NBTHelper;
import cofh.api.energy.IEnergyContainerItem;
import cofh.api.item.IInventoryContainerItem;
import cofh.core.item.ItemArmorAdv;
import cofh.core.util.CoreUtils;
import cofh.lib.util.helpers.EnergyHelper;
import cofh.lib.util.helpers.StringHelper;

public class ItemModularArmour extends ItemArmorAdv implements IEnergyContainerItem, ISpecialArmor, IInventoryContainerItem, IModularItem{

	public Map<String, VariableInt> intMap = new HashMap<String, VariableInt>();

	public ItemModularArmour(ArmorMaterial material, int type) {
		super(material, type);
		intMap.put("Capacity", new VariableInt("Capacity", 100));
		intMap.put("MaxTransfer", new VariableInt("MaxTransfer", 10));
		intMap.put("EnergyPerDamage", new VariableInt("EnergyPerDamage", 10));
		this.setCreativeTab(CreativeTabs.tabCombat);
	}


	//Item
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean check) {
		NBTHelper.createDefaultStackTag(stack);
		if (!StringHelper.isShiftKeyDown()) {
			list.add(StringHelper.shiftForDetails());
		} else {

			list.add(StringHelper.localize("info.cofh.charge") + ": " + stack.stackTagCompound.getInteger("Energy") + " / " + this.intMap.get("Capacity").get(stack) + " RF");
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
		return 1 + this.intMap.get("Capacity").get(stack) - stack.stackTagCompound.getInteger("Energy");
	}
	
	@Override
	public boolean getIsRepairable(ItemStack itemToRepair, ItemStack stack) {
		return false;
	}

	@Override
	public int getMaxDamage(ItemStack stack) {

		return 1 + this.intMap.get("Capacity").get(stack);
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
				energy += upgrade.onArmourTick(world, player, stack, ArmourSlot.getArmourSlot(this.armorType));
			}

		}

		this.extractEnergy(stack, energy, false);

		if (!stack.stackTagCompound.getBoolean("HasPutOn")) {
			stack.stackTagCompound.setBoolean("HasPutOn", true);

			for (IUpgrade upgrade : NBTHelper.getNBTUpgradeList(stack.stackTagCompound)) {

				if (upgrade != null) {
					upgrade.onArmourEquip(world, player, stack, ArmourSlot.getArmourSlot(this.armorType));
				}
			}
		}

		if (stack.stackTagCompound.getInteger("Energy") == 0) {
			for (IUpgrade upgrade : NBTHelper.getNBTUpgradeList(stack.stackTagCompound)) {

				if (upgrade != null) {
					upgrade.onNoEnergy(world, player, stack, ArmourSlot.getArmourSlot(this.armorType));
				}
			}
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
	public void onUpdate(ItemStack stack, World world, Entity player, int x, boolean y) {
		stack.stackTagCompound.setBoolean("HasPutOn", false);
	}
	
	
	//ISpecialArmor
	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
		this.extractEnergy(stack, damage * this.intMap.get("EnergyPerDamage").get(stack), false);
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		if (this.getEnergyStored(armor) >= this.intMap.get("EnergyPerDamage").get(armor)) {
			int sum = 0;
			for (IUpgrade upgrade : NBTHelper.getNBTUpgradeList(armor.stackTagCompound)) {
				sum += upgrade.getArmourDisplay();
			}
			return sum;
		} else {
			return 0;
		}
	}

	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armour, DamageSource source, double damage, int slot) {

		ArmorProperties output = new ArmorProperties(0, 0, 0);

		for (IUpgrade upgrade : NBTHelper.getNBTUpgradeList(armour.stackTagCompound)) {
			ArmorProperties prop = upgrade.getProperties(player, armour, source, damage, ArmourSlot.getArmourSlot(slot));

			if (prop == null) {
				continue;
			}

			if (prop.Priority > output.Priority) {
				output = prop;
			} else if (prop.Priority == output.Priority && prop.AbsorbRatio > output.AbsorbRatio) {
				output = prop;
			}

		}

		return new ArmorProperties(output.Priority, output.AbsorbRatio, this.getEnergyStored(armour) / this.intMap.get("EnergyPerDamage").get(armour));
	}
	
	//IEnergyContainerItem
	@Override
	public int extractEnergy(ItemStack stack, int maxExtract, boolean simulate) {

		if (stack.stackTagCompound == null) {
			EnergyHelper.setDefaultEnergyTag(stack, 0);
		}
		int stored = stack.stackTagCompound.getInteger("Energy");
		int extract = Math.min(maxExtract, stored);

		if (!simulate) {
			stored -= extract;
			stack.stackTagCompound.setInteger("Energy", stored);
		}
		return extract;
	}
	
	@Override
	public int getEnergyStored(ItemStack stack) {

		if (stack.stackTagCompound == null) {
			EnergyHelper.setDefaultEnergyTag(stack, 0);
		}
		return stack.stackTagCompound.getInteger("Energy");
	}

	@Override
	public int getMaxEnergyStored(ItemStack stack) {
		return this.intMap.get("Capacity").get(stack);
	}

	@Override
	public int receiveEnergy(ItemStack stack, int maxReceive, boolean simulate) {

		if (stack.stackTagCompound == null) {
			EnergyHelper.setDefaultEnergyTag(stack, 0);
		}
		int stored = stack.stackTagCompound.getInteger("Energy");
		int receive = Math.min(maxReceive, Math.min(this.intMap.get("Capacity").get(stack) - stored, this.intMap.get("EnergyPerDamage").get(stack)));

		if (!simulate) {
			stored += receive;
			stack.stackTagCompound.setInteger("Energy", stored);
		}
		return receive;
	}
	
	//IInventoryContainerItem
	@Override
	public int getSizeInventory(ItemStack container) {
		return 9;
	}
	
	//IModularItem
	@Override
	public VariableInt getInt(String name) {
		return this.intMap.get(name);
	}

	@Override
	public int getSlot() {
		return this.armorType;
	}
	
	public void onArmorDequip(World world, EntityPlayer player, ItemStack stack) {

		if (stack == null) {
			return;
		}

		NBTHelper.createDefaultStackTag(stack);

		stack.stackTagCompound.setBoolean("HasPutOn", false);

		for (IUpgrade upgrade : NBTHelper.getNBTUpgradeList(stack.stackTagCompound)) {
			upgrade.onArmourDequip(world, player, stack, ArmourSlot.getArmourSlot(this.armorType));
		}
	}

}
