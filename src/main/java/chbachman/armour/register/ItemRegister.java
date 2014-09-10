package chbachman.armour.register;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
		
		register(new Baubles());
		register(new Thaumcraft());
		register(new Vanilla());
		register(new ThermalExpansion());
		
		this.cleanList();
	}
	
	public void register(Module m){
		
		if(m instanceof Vanilla){
			vanillaList.put(m.modid, (Vanilla) m);
		}else{
			list.add(m);
		}
		
	}
	
	private void cleanList(){
		Iterator<Module> iterator = list.iterator();
		while(iterator.hasNext()){
			Module module = iterator.next();
			
			if(!Loader.isModLoaded(module.modid)){
				iterator.remove();
			}
		}
	}
	
	private static final String categoryName = "Use to change the recipe for the Upgrades, based around a mod. The valid choices are: ";
	
	public void preInit(){
		
		StringBuilder builder = new StringBuilder();
		
		for(String string : vanillaList.keySet()){
			builder.append(string + ",");
		}
		
		Vanilla vanilla = vanillaList.get(ModularArmour.config.get(categoryName + builder, "Recipes:", "Vanilla"));
		
		if(vanilla == null){
			vanilla = vanillaList.get("Vanilla");
		}
		
		this.base = vanilla;
		
		
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
