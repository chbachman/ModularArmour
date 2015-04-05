package chbachman.api.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.launchwrapper.Launch;
import net.minecraft.util.StatCollector;

public class Translator{
	
	private static final boolean developmentEnvironment = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
	
	Map<String, Boolean> hasWritten = new HashMap<String, Boolean>();
	
	public static String translate(String unlocalizedName){
		if(!developmentEnvironment){ //Try to not slow down non-development.
			return StatCollector.translateToLocal(unlocalizedName);
		}
		
		
		
		String translated = StatCollector.translateToLocal(unlocalizedName);
		
		if(translated.equals(unlocalizedName)){
			writeToLangFile(unlocalizedName + "=WRITE ME");
			
		}
		
		return translated;
	}
	
	
	private static PrintWriter w;
	private static final File langFile = new File("/assets/modulararmour/lang/en_US.lang");
	
	private static void writeToLangFile(String toWrite){
		try{
			w = new PrintWriter(new BufferedWriter(new FileWriter(langFile)));
			
			w.write(toWrite);
		}catch (IOException e){
			e.printStackTrace();
		}finally{
			w.close();
		}
		
		
	}

}
