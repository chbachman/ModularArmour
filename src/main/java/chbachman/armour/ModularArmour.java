package chbachman.armour;

import net.minecraftforge.common.MinecraftForge;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import chbachman.armour.gui.GuiHandler;
import chbachman.armour.handler.GenericEventHandler;
import chbachman.armour.items.ItemRegister;
import chbachman.armour.network.ArmourPacket;
import chbachman.armour.proxy.IProxy;
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

	@SidedProxy(clientSide = "chbachman.armour.proxy.ClientProxy", serverSide = "chbachman.armour.proxy.ServerProxy")
	public static IProxy proxy;
	
	public static Logger log = LogManager.getLogger(Reference.MODID);
	public static GuiHandler guiHandler = new GuiHandler();
	
	//TODO: Clean up ModularArmourItem
	//TODO: Fix Remove Upgrade
	//TODO: Add rest of Potion Upgrade
	
	public ModularArmour(){
		super(log);
	}
	
	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		ItemRegister.init();
		Upgrade.preInit();
		MinecraftForge.EVENT_BUS.register(new GenericEventHandler());
		ArmourPacket.initialize();
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent event) {
		
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, guiHandler);
		proxy.registerKeyBinds();
		

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
