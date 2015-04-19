package chbachman.armour.register;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chbachman.api.registry.UpgradeRegistry;
import chbachman.api.upgrade.IUpgrade;
import chbachman.armour.ModularArmour;
import cpw.mods.fml.common.Loader;

public class ItemRegister {
	
	public static final ItemRegister INSTANCE = new ItemRegister();
	
	private final List<Module> list;
	
	private final Map<String, Vanilla> vanillaList;
	
	public Vanilla base;
	
	public ItemRegister(){
		list = new ArrayList<Module>();
		vanillaList = new HashMap<String, Vanilla>();
		vanillaList.put("Vanilla", new Vanilla());
		
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
		
		if(m instanceof Vanilla){
			vanillaList.put(modid, (Vanilla) m);
		}else{
			list.add(m);
		}
		
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
		
		if(m instanceof Vanilla){
			vanillaList.put(displayName, (Vanilla) m);
		}else{
			list.add(m);
		}
		
	}
	
	/**
	 * Called during preInit;
	 */
	public void preInit(){
		
		Vanilla vanilla = vanillaList.get(ModularArmour.config.get("Change to change the recipes", "Recipes:", "Vanilla"));
		
		if(vanilla == null){
			vanilla = vanillaList.get("Vanilla");
		}
		
		this.base = vanilla;
		
		base.registerUpgrades();
		base.preInit();
		
		for(Module module : list){
			module.registerUpgrades();
		}
		
		for(Module module : list){
			module.preInit();
		}
		
		this.createRecipeList();
		
		
	}
	
	private void createRecipeList(){

		StringBuilder builder = new StringBuilder();

		for(String string : vanillaList.keySet()){
			builder.append(string + ",");
		}

		ModularArmour.output.write("Recipe Types", builder.toString());

		for(IUpgrade upgrade : UpgradeRegistry.getUpgradeList()){

			ModularArmour.output.write("upgrade names", new StringBuilder().append(upgrade.getBaseName()).append(" = ").append(upgrade.getName()).toString());
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
