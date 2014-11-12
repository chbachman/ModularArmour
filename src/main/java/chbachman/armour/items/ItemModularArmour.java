package chbachman.armour.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import thaumcraft.api.IGoggles;
import thaumcraft.api.IVisDiscountGear;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.nodes.IRevealer;
import chbachman.api.IArmourUpgrade;
import chbachman.api.IModularItem;
import chbachman.api.IUpgrade;
import chbachman.armour.ModularArmour;
import chbachman.armour.gui.GuiHandler;
import chbachman.armour.reference.Reference;
import chbachman.armour.register.Thaumcraft;
import chbachman.armour.util.ArmourSlot;
import chbachman.armour.util.NBTHelper;
import chbachman.armour.util.NBTUpgradeList;
import chbachman.armour.util.VariableInt;
import cofh.api.item.IInventoryContainerItem;
import cofh.core.util.CoreUtils;
import cofh.lib.util.helpers.StringHelper;

public class ItemModularArmour extends ItemArmor implements ISpecialArmor, IInventoryContainerItem, IModularItem, IGoggles, IVisDiscountGear, IRevealer{

	private VariableInt capacity = new VariableInt("capacity", 100);
	private VariableInt maxTransfer = new VariableInt("maxTransfer", 100);
	private VariableInt energyPerDamage = new VariableInt("energyPerDamage", 100);
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
		
		list.add(StringHelper.localize("info.chbachman.level") + level.get(stack));
		
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

		if (!stack.stackTagCompound.getBoolean("HasPutOn")) {
			stack.stackTagCompound.setBoolean("HasPutOn", true);

			for (IUpgrade upgrade : NBTHelper.getNBTUpgradeList(stack.stackTagCompound)) {

				if (upgrade != null) {
					upgrade.onEquip(world, player, stack, ArmourSlot.getArmourSlot(this.armorType), level.get(stack));
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
		this.extractEnergy(stack, this.energyPerDamage.get(stack) * damage, false);
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
	
	//IInventoryContainerItem
	@Override
	public int getSizeInventory(ItemStack container) {
		return 9;
	}
	
	//IModularItem
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
			//if(upgrade == null){
			//	continue;
			//}
			
			upgrade.onDequip(world, player, stack, ArmourSlot.getArmourSlot(this.armorType), level.get(stack));
		}
	}
	
	@Override
	public ItemStack setLevel(ItemStack stack, int level) {
		this.level.set(stack, level);
		return stack;
	}
	
	//IRevealer
	@Override
	public boolean showNodes(ItemStack itemstack, EntityLivingBase player) {
		NBTUpgradeList list = NBTHelper.getNBTUpgradeList(itemstack);

		return list.contains(Thaumcraft.gogglesOfRevealing);
	}

	//IVisDiscountGear
	@Override
	public int getVisDiscount(ItemStack stack, EntityPlayer player, Aspect aspect) {
		NBTUpgradeList list = NBTHelper.getNBTUpgradeList(stack);
		
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
	public boolean showIngamePopups(ItemStack itemstack, EntityLivingBase player) {
		NBTUpgradeList list = NBTHelper.getNBTUpgradeList(itemstack);

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
	public int getEnergyPerDamage(ItemStack stack) {
		return this.energyPerDamage.get(stack);
	}

	@Override
	public void setEnergyPerDamage(ItemStack stack, int amount) {
		this.energyPerDamage.set(stack, amount);
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

}
