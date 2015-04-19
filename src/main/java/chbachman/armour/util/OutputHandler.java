package chbachman.armour.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class OutputHandler {
	
	File file;
	
	public Map<String, List<String>> categoryList = new HashMap<String, List<String>>();
	
	public OutputHandler(File file){
		if(file.exists()){
			file.delete();
		}
		try{
			file.createNewFile();
			
		}catch (IOException e){
			
		}
		
		this.file = file;
	}
	
	public void write(String category, String message, Object... params){
		List<String> list = categoryList.get(category);
		
		if(list == null){
			list = new ArrayList<String>();
			list.add(String.format(message, params));
			categoryList.put(category, list);
		}else{
			list.add(String.format(message, params));
		}
		
		
	}
	
	public void save(){
		try {
			BufferedWriter write = new BufferedWriter(new FileWriter(file));
			
			for(Entry<String, List<String>> entry : categoryList.entrySet()){
				
				write.write(entry.getKey());
				write.write("{");
				write.newLine();
				
				for(String s : entry.getValue()){
					write.write("\t");
					write.write(s);
					write.newLine();
				}
				
				write.write("}");
				write.newLine();
				write.newLine();
				
			}
			
			
			write.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
