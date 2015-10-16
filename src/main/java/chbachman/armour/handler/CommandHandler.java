package chbachman.armour.handler;

import java.util.ArrayList;
import java.util.List;

import chbachman.api.registry.UpgradeRegistry;
import chbachman.api.util.Array;
import chbachman.armour.ModularArmour;
import chbachman.armour.gui.tablet.TabletGui;
import chbachman.armour.register.ItemRegister;
import chbachman.armour.register.Module;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class CommandHandler {
	
	static MACommand command;
	
	public static void registerCommands(FMLServerStartingEvent event){
		
		command = new MACommand();
		
		event.registerServerCommand(command);
		
		initCommands();
		
	}
	
	public static void initCommands(){
		
		if(ModularArmour.developmentEnvironment){
			
			command.registerCommand(new SubCommand("refresh", "refresh"){

				@Override
				void processCommand(ICommandSender sender, String[] args) {
					
					UpgradeRegistry.INSTANCE.recipeList.clear();
					TabletGui.pages.clear();
					
					
					ItemRegister.INSTANCE.base.registerUpgradeRecipes();
					
					for(Module module : ItemRegister.INSTANCE.list){
						module.registerUpgradeRecipes();
					}
					
					
					
				}
				
			});
			
			command.registerCommand(new SubCommand("refreshCommands", "refreshCommands"){

				@Override
				void processCommand(ICommandSender sender, String[] args) {
					
					command.commandList.clear();
					
					
					initCommands();
					
		
					
				}
				
			});
			
			command.registerCommand(new SubCommand("help", "help"){

				@Override
				void processCommand(ICommandSender sender, String[] args) {
					
					for(SubCommand command : command.commandList){
						
						sender.addChatMessage(new ChatComponentText(command.name));
						
					}
		
					
				}
				
			});
			
		}
		
	}
	
	static class MACommand extends CommandBase{
		
		private Array<SubCommand> commandList = new Array<SubCommand>();
		
		@Override
		public String getCommandName() {
			return "ma";
		}

		@Override
		public String getCommandUsage(ICommandSender sender) {
			return "ma <subcommand>";
		}

		@Override
		public void processCommand(ICommandSender sender, String[] args) {
			
			if(args.length == 0){
				return;
			}
			
			for(SubCommand command : commandList){
				if(command.name.equals(args[0])){
					command.processCommand(sender, args);
					return;
				}
			}
			
			sender.addChatMessage(new ChatComponentText("That is not a valid command!"));
			
		}
		
		
		
		@Override
		public List<String> addTabCompletionOptions(ICommandSender sender, String[] args) {
			
			for(SubCommand command : commandList){
				if(command.name.equals(args[0])){
					return command.autoComplete(sender, args);
				}
			}
			
			List<String> list = new ArrayList<String>();
			
			String lastArg = args[args.length - 1];
			
			for(SubCommand command : commandList){
				
				if(command.name.startsWith(lastArg)){
					list.add(command.name);
				}	
			}
			
 			return list;
		}

		public void registerCommand(SubCommand command){
			this.commandList.add(command);
		}
		
	}
	
	static abstract class SubCommand{
		
		public final String name;
		public final String usage;
		
		public SubCommand(String name, String usage){
			this.name = name;
			this.usage = usage;
		}
		
		abstract void processCommand(ICommandSender sender, String[] args);
		
		List<String> autoComplete(ICommandSender sender, String[] args){
			return null;
		}
		
	}
	
}
