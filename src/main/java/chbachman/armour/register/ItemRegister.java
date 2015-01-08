package chbachman.armour.register;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
		vanillaList.put("Vanilla", new Vanilla());
		
		register(Baubles.class, "Baubles");
		register(Thaumcraft.class, "Thaumcraft");
		register(Enviromine.class, "enviromine");
		register(MineTweaker.class, "MineTweaker3");
		register(BloodMagic.class, "AWWayofTime");
		//register(new ThermalExpansion());
	}
	
	public void register(Class<? extends Module> clazz, String name){
		
		if(!Loader.isModLoaded(name)){
			return;
		}
		
		Module m = null;
		try {
			m = clazz.newInstance();
		} catch (Exception e) {
			return;
		}
		
		if(m instanceof Vanilla){
			vanillaList.put(name, (Vanilla) m);
		}else{
			list.add(m);
		}
		
	}
	
	public void preInit(File file){
		
		createRecipeList(file);
		
		Vanilla vanilla = vanillaList.get(ModularArmour.config.get("Change to change the recipes", "Recipes:", "Vanilla"));
		
		if(vanilla == null){
			vanilla = vanillaList.get("Vanilla");
		}
		
		this.base = vanilla;
		
		for(Module module : list){
			module.registerUpgrades();
		}
		
		for(Module module : list){
			module.preInit();
		}
		
		base.registerUpgrades();
		base.preInit();
		
		
		
		
	}
	
	private void createRecipeList(File file){
		File out = new File(file.getAbsoluteFile(), "ModularRecipes.txt");
		try {
			if(out.exists()){
				out.delete();
			}
			
			out.createNewFile();


			BufferedWriter output = new BufferedWriter(new FileWriter(out));
			
			StringBuilder builder = new StringBuilder();
			
			for(String string : vanillaList.keySet()){
				builder.append(string + ",");
			}
			
			output.append(builder.toString());
			
			output.close();
			
		} catch (IOException e) {
			e.printStackTrace();
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
			module.registerUpgradeRecipes();
		}
		
		for(Module module : list){
			module.postInit();
		}
		
		
	}

}
