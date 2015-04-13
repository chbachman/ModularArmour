package chbachman.armour.util.json;

import java.lang.reflect.Type;

import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;

import com.google.gson.*;

public class CustomNBTJson implements JsonDeserializer<NBTTagCompound>, JsonSerializer<NBTTagCompound>{

	@Override
	public JsonElement serialize(NBTTagCompound src, Type typeOfSrc, JsonSerializationContext context){
		return new JsonPrimitive(src.toString());
	}

	@Override
	public NBTTagCompound deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException{
		
		try{
			return (NBTTagCompound) JsonToNBT.func_150315_a(json.getAsString());
		}catch (NBTException e){
			throw new JsonParseException(e);
		}
		
	}

}
