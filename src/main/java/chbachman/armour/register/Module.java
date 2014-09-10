package chbachman.armour.register;


public abstract class Module {

	public final String modid;

	public Module(String modid){
		this.modid = modid;
	}

	public abstract void preInit();

	public abstract void init();

	public abstract void postInit();

	public abstract void registerUpgrades();

	public abstract void registerUpgradeRecipes();

}