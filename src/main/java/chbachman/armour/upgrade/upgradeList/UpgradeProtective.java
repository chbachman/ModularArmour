package chbachman.armour.upgrade.upgradeList;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;
import chbachman.api.IModularItem;
import chbachman.armour.ModularArmour;
import chbachman.armour.util.ArmourSlot;
import chbachman.armour.util.EnergyUtil;
import cofh.api.energy.IEnergyContainerItem;


public abstract class UpgradeProtective extends UpgradeBasic{

	float protection;
	
	public UpgradeProtective(String name, float protection) {
		super(name);
		this.protection = protection;
		
	}

	@Override
	public void registerConfigOptions() {
		protection = (float) ModularArmour.config.get("defensive values", this.getName(), (double) protection);
	}

	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, ArmourSlot slot) {
		
		IEnergyContainerItem energy = EnergyUtil.getItem(armor);
		IModularItem modularItem = (IModularItem) armor.getItem();
		
		if(EnergyUtil.isEmpty(armor)){
			return new ArmorProperties(0,0,0);
		}
		
		if(this.shouldDefend(player, armor, source, damage, slot)){
			energy.extractEnergy(armor, (int) (modularItem.getEnergyPerDamage(armor) * damage), false);
			return new ArmorProperties(0, protection, Integer.MAX_VALUE);
		}
		
		return new ArmorProperties(0,0,0);
	}
	
	@Override
	public boolean isCompatible(IModularItem item, ItemStack stack, int armorType) {
		return item.isArmour();
	}
	
	public abstract boolean shouldDefend(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, ArmourSlot slot);
	
	
	//Simple Subclasses Follow
	public static class UpgradeProjectile extends UpgradeProtective {

		public UpgradeProjectile() {
			super("projectileProtector", .75F);
		}

		@Override
		public boolean shouldDefend(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, ArmourSlot slot) {
			return source.isProjectile();
		}

	}
	
	public static class UpgradeFire extends UpgradeProtective {

		public UpgradeFire() {
			super("fireProtector", .75F);
		}

		@Override
		public boolean shouldDefend(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, ArmourSlot slot) {
			return source.isFireDamage();
		}

	}
	
	public static class UpgradeExplosion extends UpgradeProtective {

		public UpgradeExplosion() {
			super("explosionProtector", .75F);
		}

		@Override
		public boolean shouldDefend(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, ArmourSlot slot) {
			return source.isExplosion();
		}

	}
	
	public static class UpgradeUnblockable extends UpgradeProtective {

		public UpgradeUnblockable() {
			super("unblockableProtector", .1F);
		}

		@Override
		public boolean shouldDefend(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, ArmourSlot slot) {
			return source.isUnblockable();
		}

	}
	
	public static class UpgradeMagic extends UpgradeProtective {

		public UpgradeMagic() {
			super("magicProtector", .2F);
		}

		@Override
		public boolean shouldDefend(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, ArmourSlot slot) {
			return source.isMagicDamage();
		}

	}
	
	public static class UpgradeWither extends UpgradeProtective {

		public UpgradeWither() {
			super("witherProtector", .2F);
		}

		@Override
		public boolean shouldDefend(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, ArmourSlot slot) {
			return source == DamageSource.wither;
		}

	}
	
	public static class UpgradeLava extends UpgradeProtective {

		public UpgradeLava() {
			super("lavaProtector", .75F);
		}

		@Override
		public boolean shouldDefend(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, ArmourSlot slot) {
			return source == DamageSource.lava;
		}

	}
	
}
