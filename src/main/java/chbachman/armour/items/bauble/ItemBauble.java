package chbachman.armour.items.bauble;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import thaumcraft.api.IVisDiscountGear;
import thaumcraft.api.aspects.Aspect;
import vazkii.botania.api.item.IPixieSpawner;
import baubles.api.BaubleType;
import baubles.api.IBauble;
import chbachman.api.item.IModularItem;
import chbachman.armour.ModularArmour;
import chbachman.armour.items.armour.logic.UpgradeLogicAdv;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.Optional.Interface;

@Optional.InterfaceList(value = { @Interface(iface = "thaumcraft.api.IVisDiscountGear", modid = "Thaumcraft"), @Interface(iface = "vazkii.botania.api.item.IPixieSpawner", modid = "Botania"), })
public abstract class ItemBauble extends Item implements IBauble, IModularItem, IVisDiscountGear, IPixieSpawner{

	protected UpgradeLogicAdv holder;

	BaubleType type;

	public ItemBauble() {
		this.setCreativeTab(ModularArmour.creativeTab);
	}

	public ItemBauble setBaubleType(BaubleType type){
		this.type = type;
		return this;
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack){
		return this.type;
	}

	// Item
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean check){
		this.holder.addInformation(list, stack);
	}

	@Override
	public int getDisplayDamage(ItemStack stack){
		return this.holder.getDisplayDamage(stack);
	}

	@Override
	public boolean getIsRepairable(ItemStack itemToRepair, ItemStack stack){
		return this.holder.getIsRepairable(itemToRepair, stack);
	}

	@Override
	public int getMaxDamage(ItemStack stack){
		return this.holder.getMaxDamage(stack);
	}

	@Override
	public EnumRarity getRarity(ItemStack stack){
		return EnumRarity.uncommon;
	}

	@Override
	public boolean isDamaged(ItemStack stack){
		return this.holder.isDamaged(stack);
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack){
		this.holder.onArmourTick(world, player, stack);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player){
		return this.holder.onItemRightClick(stack, world, player);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type){
		return this.holder.getArmourTexture(stack, entity, slot, type);
	}

	// IModularItem
	@Override
	public int getSlot(){
		switch (this.type) {

		case BELT:
			return 4;
		case RING:
			return 5;
		case AMULET:
			return 6;
		default:
			return 7;

		}
	}

	@Override
	public boolean isArmour(){
		return false;
	}

	@Override
	public void onArmourDequip(World world, EntityPlayer player, ItemStack stack){}

	@Override
	public void onArmourEquip(World world, EntityPlayer player, ItemStack stack){}

	@Override
	public void damageArmour(ItemStack stack, int damage){
		this.holder.damageArmour(stack, damage);
	}

	@Override
	public void healArmour(ItemStack stack, int damage){
		this.holder.healArmour(stack, damage);
	}

	@Override
	public UpgradeLogicAdv getLogic(){
		return this.holder;
	}

	// IBauble

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player){
		this.holder.onArmourTick(player.worldObj, (EntityPlayer) player, itemstack);
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player){
		this.holder.onArmourEquip(player.worldObj, (EntityPlayer) player, itemstack);
	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player){
		this.holder.onArmourDequip(player.worldObj, (EntityPlayer) player, itemstack);
	}

	@Override
	public boolean canEquip(ItemStack itemstack, EntityLivingBase player){
		return true;
	}

	@Override
	public boolean canUnequip(ItemStack itemstack, EntityLivingBase player){
		return true;
	}

	// IVisDiscountGear
	@Override
	@Optional.Method(modid = "Thaumcraft")
	public int getVisDiscount(ItemStack stack, EntityPlayer player, Aspect aspect){
		return this.holder.getVisDiscount(stack, player, aspect);
	}

	// IPixieSpawner
	@Override
	public float getPixieChance(ItemStack stack){
		return this.holder.getPixieChance(stack);
	}

}
