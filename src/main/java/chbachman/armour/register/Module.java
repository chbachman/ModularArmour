package chbachman.armour.register;


public interface Module {

	public void preInit();

	public void init();

	public void postInit();

	public void registerUpgrades();

	public void registerUpgradeRecipes();

}