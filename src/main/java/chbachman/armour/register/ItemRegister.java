package chbachman.armour.register;

import chbachman.api.util.Array;
import cpw.mods.fml.common.Loader;

public class ItemRegister {
	
	public static final ItemRegister INSTANCE = new ItemRegister();
	
	private final Array<Module> list;
	
	public Vanilla base;
	
	public ItemRegister(){
		list = new Array<Module>();
		
		register(Baubles.class, "Baubles");
		register(Thaumcraft.class, "Thaumcraft");
		register(Enviromine.class, "enviromine");
		register(MineTweaker.class, "MineTweaker3");
		register(BloodMagic.class, "AWWayofTime");
		register(Botania.class, "Botania");
		register(IndustrialCraft2.class, "IC2");
	}
	
	/**
	 * Call to register a Module or Vanilla-Replacement, pass in the modid to be loaded on.
	 * @param clazz
	 * @param modid
	 */
	public void register(Class<? extends Module> clazz, String modid){
		
		if(!Loader.isModLoaded(modid)){
			return;
		}
		
		Module m = null;
		try {
			m = clazz.newInstance();
		} catch (Exception e) {
			return;
		}
		
		list.add(m);
		
	}
	
	public void register(Class<? extends Module> clazz, String displayName, String[] modid){
		
		for(String name : modid){
			if(!Loader.isModLoaded(name)){
				return;
			}
		}
		
		Module m = null;
		try {
			m = clazz.newInstance();
		} catch (Exception e) {
			return;
		}
		
		list.add(m);
		
	}
	
	/**
	 * Called during preInit;
	 */
	public void preInit(){
		
		this.base = new Vanilla();
		
		
		base.preInit();
		base.registerUpgrades();

		
		for(Module module : list){
			module.preInit();
		}
		
		for(Module module : list){
			module.registerUpgrades();
		}
		
		
	}

	public void init(){
		
		base.init();
		
		for(Module module : list){
			module.init();
		}

	}
	
	public void postInit(){
		
		base.postInit();
		base.registerUpgradeRecipes();

		for(Module module : list){
			module.postInit();
		}
		
		for(Module module : list){
			module.registerUpgradeRecipes();
		}
		
		
	}

}
