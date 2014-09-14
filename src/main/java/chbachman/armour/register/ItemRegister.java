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
		
		register(new Baubles());
		register(new Thaumcraft());
		//register(new ThermalExpansion());
	}
	
	public void register(Module m){
		
		if(!Loader.isModLoaded(m.modid)){
			return;
		}
		
		if(m instanceof Vanilla){
			vanillaList.put(m.modid, (Vanilla) m);
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
		
		
		base.preInit();
		base.registerUpgrades();
		
		for(Module module : list){
			module.preInit();
		}
		
		for(Module module : list){
			module.registerUpgrades();
		}
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
			module.postInit();
		}
		
		for(Module module : list){
			module.registerUpgradeRecipes();
		}
	}

}
