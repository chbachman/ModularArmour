package chbachman.armour.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor;
import chbachman.armour.upgrade.Upgrade;
import chbachman.armour.util.NBTHelper;

public class ItemSpecialArmour extends ItemElectricArmour implements ISpecialArmor{

	
	
	private static final ArmorProperties USELESS = new ArmorProperties(0, 0, 0);


	public ItemSpecialArmour(ArmorMaterial material, int type) {
		super(material, type);
	}
	
	
	@Override
	public boolean getIsRepairable(ItemStack itemToRepair, ItemStack stack){
		return false;
	}
	
	
	@Override
	public EnumRarity getRarity(ItemStack stack) {

		return EnumRarity.uncommon;
	}
	
	//ISpecialArmor
	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
		if(source.isUnblockable()){
			return USELESS;
		}

		double ratio = 0;

		NBTTagList nbt = NBTHelper.getNBTTagList(armor.stackTagCompound);

		for(int i = 0; i < nbt.tagCount(); i++){
			ratio += Upgrade.getUpgradeFromNBT(nbt.getCompoundTagAt(i)).getDefenceRatio(slot);
		}

		return new ArmorProperties(0, ratio, Integer.MAX_VALUE);
	}


	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		int sum = 0;
		
		NBTTagList nbt = NBTHelper.getNBTTagList(armor.stackTagCompound);
		
		for(int i = 0; i < nbt.tagCount(); i++){
			sum += Upgrade.getUpgradeFromNBT(nbt.getCompoundTagAt(i)).getArmourDisplay();
		}
		return sum;
	}


	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
		extractEnergy(stack, damage * energyPerDamage, false);
	}

}
