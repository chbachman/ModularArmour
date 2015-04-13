package chbachman.armour;

import java.io.File;

import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import chbachman.api.upgrade.IUpgrade;
import chbachman.api.upgrade.UpgradeList;
import chbachman.armour.gui.GuiHandler;
import chbachman.armour.handler.DamageEventHandler;
import chbachman.armour.handler.GenericEventHandler;
import chbachman.armour.handler.ModularArmourHandler;
import chbachman.armour.network.ArmourPacket;
import chbachman.armour.proxy.IProxy;
import chbachman.armour.reference.Reference;
import chbachman.armour.register.ItemRegister;
import chbachman.armour.util.ModularCreativeTab;
import chbachman.armour.util.OutputHandler;
import chbachman.armour.util.json.JsonRegister;
import cofh.core.util.ConfigHandler;
import cofh.mod.BaseMod;

import com.google.gson.GsonBuilder;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = Reference.MODID, name = Reference.MODNAME, version = Reference.VERSION, dependencies = Reference.DEPENDENCIES)
public class ModularArmour extends BaseMod {
    
    @Instance(Reference.MODID)
    public static ModularArmour instance;
    
    @SidedProxy(clientSide = "chbachman.armour.proxy.ClientProxy", serverSide = "chbachman.armour.proxy.ServerProxy")
    public static IProxy proxy;
    
    //Whether we are in a development method.
    public static final boolean developmentEnvironment = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
    
    //The log method
    public static Logger log = LogManager.getLogger(Reference.MODID);
    
    public static GuiHandler guiHandler = new GuiHandler();
    
    //The config, use this between preInit and postInit.
    public static ConfigHandler config = new ConfigHandler(Reference.VERSION);
    public static OutputHandler output;
    
    private static File configDir;
    
    //Register the different Modular Items here.
    public static ModularArmourHandler modularHandler;
    
    //Modular Armour Creative Tab
    public static ModularCreativeTab creativeTab;
    
    public static boolean debug = false;
    
    public ModularArmour(){
        super(log);
    }
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
    	configDir = new File(event.getModConfigurationDirectory(), "ModularArmour");
    	configDir.mkdir();
    	
        config.setConfiguration(new Configuration(new File(configDir, "Main.cfg")));
        output = new OutputHandler(new File(configDir, "ModularRecipes.txt"));
        
        modularHandler = new ModularArmourHandler();
        
        debug = config.get("advanced", "debug", false, "Do not change this unless I tell you.");
        
        creativeTab = new ModularCreativeTab();
        
        
        ItemRegister.INSTANCE.preInit();
        ArmourPacket.initialize();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event){
        
    	if(developmentEnvironment){
    		MinecraftForge.EVENT_BUS.register(new DamageEventHandler());
    	}
    	
        MinecraftForge.EVENT_BUS.register(new GenericEventHandler());
        MinecraftForge.EVENT_BUS.register(proxy);
        FMLCommonHandler.instance().bus().register(new GenericEventHandler());
        
        ItemRegister.INSTANCE.init();
        
		
		for(IUpgrade upgrade : UpgradeList.INSTANCE){
			upgrade.registerConfigOptions();
		}
        
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, guiHandler);
        proxy.registerKeyBinds();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event){
        config.cleanUp(false, true);
        ItemRegister.INSTANCE.postInit();
        output.save();
        
        GsonBuilder gsonBuilder = new GsonBuilder();
        JsonRegister.registerCustomSerializers(gsonBuilder);
        JsonRegister.createJsonRecipes(gsonBuilder);
        JsonRegister.registerJsonRecipes(gsonBuilder);
        
    }
    
    public static File getConfigDirectory(){
    	return configDir;
    }
    
    @Override
    public String getModId() {
        return Reference.MODID;
    }
    
    @Override
    public String getModName() {
        return Reference.MODNAME;
    }
    
    @Override
    public String getModVersion() {
        return Reference.VERSION;
    }
    
}
