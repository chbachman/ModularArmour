package chbachman.armour.register;


public interface Module {

	/**
	 * Called during preinit. See {@link cpw.mods.fml.common.event.FMLPreInitializationEvent}
	 */
	public void preInit();

	/**
	 * Called during init. See {@link cpw.mods.fml.common.event.FMLInitializationEvent}
	 */
	public void init();

	/**
	 * Called during postInit. See {@link cpw.mods.fml.common.event.FMLPostInitializationEvent}
	 */
	public void postInit();

	/**
	 * Called when you should create and register your upgrades, or right after preInit.
	 */
	public void registerUpgrades();

	/**
	 * Called when you should register your recipes for your upgrades, or right after postInit;
	 */
	public void registerUpgradeRecipes();

}