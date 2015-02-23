package chbachman.armour.upgrade.upgradeList;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor.ArmorProperties;
import chbachman.api.Configurable;
import chbachman.api.IModularItem;
import chbachman.armour.ModularArmour;
import chbachman.armour.util.ArmourSlot;
import chbachman.armour.util.EnergyUtil;
import cofh.api.energy.IEnergyContainerItem;


public abstract class UpgradeProtective extends UpgradeBasic{

	@Configurable(min = 0, max = 100)
	int protection;
	
	public UpgradeProtective(String name, int protection) {
		super(name);
		this.protection = protection;
		
	}

	@Override
	public void registerConfigOptions() {
		protection = ModularArmour.config.get("defensive values", this.getName(), protection);
	}

	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, ArmourSlot slot) {
		
		IEnergyContainerItem energy = EnergyUtil.getItem(armor);
		
		if(EnergyUtil.isEmpty(armor)){
			return new ArmorProperties(0,0,0);
		}
		
		if(this.shouldDefend(player, armor, source, damage, slot)){
			energy.extractEnergy(armor, (int) (this.getEnergyPerDamage(armor) * damage), false);
			return new ArmorProperties(0, protection / 100, Integer.MAX_VALUE);
		}
		
		return new ArmorProperties(0,0,0);
	}
	
	@Override
	public boolean isCompatible(IModularItem item, ItemStack stack, int armorType) {
		return item.isArmour();
	}
	
	
	@Override
	public int getArmourDisplay() {
		return (int) (protection / .25);
	}

	/**
	 * Return whether the upgrade should defend against the current situation.
	 * @param player
	 * @param armor
	 * @param source
	 * @param damage
	 * @param slot
	 * @return
	 */
	public abstract boolean shouldDefend(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, ArmourSlot slot);
	
	/**
	 * Return the energy drained per damage.
	 * @param stack
	 * @return
	 */
	public abstract int getEnergyPerDamage(ItemStack stack);
	
	//Simple Subclasses Follow
	public static class UpgradeProjectile extends UpgradeProtective {

		public UpgradeProjectile() {
			super("projectileProtector", 75);
		}

		@Override
		public boolean shouldDefend(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, ArmourSlot slot) {
			return source.isProjectile();
		}
		
		public int getEnergyPerDamage(ItemStack stack){
			return 100;
		}

	}
	
	public static class UpgradeFire extends UpgradeProtective {

		public UpgradeFire() {
			super("fireProtector", 75);
		}

		@Override
		public boolean shouldDefend(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, ArmourSlot slot) {
			return source.isFireDamage();
		}
		
		public int getEnergyPerDamage(ItemStack stack){
			return 100;
		}

	}
	
	public static class UpgradeExplosion extends UpgradeProtective {

		public UpgradeExplosion() {
			super("explosionProtector", 75);
		}

		@Override
		public boolean shouldDefend(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, ArmourSlot slot) {
			return source.isExplosion();
		}
		
		public int getEnergyPerDamage(ItemStack stack){
			return 100;
		}

	}
	
	public static class UpgradeUnblockable extends UpgradeProtective {

		public UpgradeUnblockable() {
			super("unblockableProtector", 10);
		}

		@Override
		public boolean shouldDefend(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, ArmourSlot slot) {
			return source.isUnblockable();
		}
		
		public int getEnergyPerDamage(ItemStack stack){
			return 100;
		}

	}
	
	public static class UpgradeMagic extends UpgradeProtective {

		public UpgradeMagic() {
			super("magicProtector", 20);
		}

		@Override
		public boolean shouldDefend(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, ArmourSlot slot) {
			return source.isMagicDamage();
		}

		public int getEnergyPerDamage(ItemStack stack){
			return 100;
		}
	}
	
	public static class UpgradeWither extends UpgradeProtective {

		public UpgradeWither() {
			super("witherProtector", 20);
		}

		@Override
		public boolean shouldDefend(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, ArmourSlot slot) {
			return source == DamageSource.wither;
		}
		
		public int getEnergyPerDamage(ItemStack stack){
			return 100;
		}

	}
	
	public static class UpgradeLava extends UpgradeProtective {

		public UpgradeLava() {
			super("lavaProtector", 75);
		}

		@Override
		public boolean shouldDefend(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, ArmourSlot slot) {
			return source == DamageSource.lava;
		}
		
		public int getEnergyPerDamage(ItemStack stack){
			return 100;
		}

	}
	
}
