package chbachman.armour.util.json;

import java.lang.reflect.Type;

import chbachman.api.registry.UpgradeRegistry;
import chbachman.api.upgrade.IUpgrade;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class CustomIUpgradeJson implements JsonDeserializer<IUpgrade>, JsonSerializer<IUpgrade>{

	@Override
	public JsonElement serialize(IUpgrade src, Type typeOfSrc, JsonSerializationContext context){
		return new JsonPrimitive(src.getBaseName());
	}

	@Override
	public IUpgrade deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException{
		return UpgradeRegistry.getUpgrade(json.getAsString());
	}

}
