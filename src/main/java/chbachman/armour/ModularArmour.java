package chbachman.armour;

import net.minecraftforge.common.MinecraftForge;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import chbachman.armour.event.GenericEventHandler;
import chbachman.armour.gui.GuiHandler;
import chbachman.armour.items.ItemRegister;
import chbachman.armour.proxy.CommonProxy;
import chbachman.armour.reference.Reference;
import chbachman.armour.upgrade.Upgrade;
import cofh.mod.BaseMod;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = Reference.MODID, name = Reference.MODNAME, version = Reference.VERSION, dependencies = Reference.DEPENDENCIES)
public class ModularArmour extends BaseMod{
	
	@Instance(Reference.MODID)
	public static ModularArmour instance;

	@SidedProxy(clientSide = "chbachman.armour.proxy.ClientProxy", serverSide = "chbachman.armour.proxy.CommonProxy")
	public static CommonProxy proxy;
	
	public static Logger log = LogManager.getLogger(Reference.MODID);
	public static GuiHandler guiHandler = new GuiHandler();
	
	public ModularArmour(){
		super(log);
	}
	
	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		ItemRegister.init();
		Upgrade.init();
		MinecraftForge.EVENT_BUS.register(new GenericEventHandler());
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent event) {
		
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, guiHandler);

	}

	@EventHandler
	public static void postInit(FMLPostInitializationEvent event) {
		
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
