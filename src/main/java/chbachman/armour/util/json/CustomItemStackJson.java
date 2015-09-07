package chbachman.armour.util.json;

import java.lang.reflect.Type;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import cpw.mods.fml.common.registry.GameData;

public class CustomItemStackJson implements JsonDeserializer<ItemStack>, JsonSerializer<ItemStack> {

    @Override
    public ItemStack deserialize(JsonElement element, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject json = element.getAsJsonObject();

        boolean isBlock = json.get("isBlock").getAsBoolean();
        String name = json.get("name").getAsString();
        int meta = json.get("metadata").getAsInt();

        ItemStack stack;
        if (isBlock) {
            stack = new ItemStack(GameData.getBlockRegistry().getObject(name), 1, meta);
        } else {
            stack = new ItemStack(GameData.getItemRegistry().getObject(name), 1, meta);
        }

        stack.stackTagCompound = context.deserialize(json.get("nbt"), NBTTagCompound.class);

        return stack;
    }

    @Override
    public JsonElement serialize(ItemStack src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("isBlock", src.getItem() instanceof ItemBlock);

        if (src.getItem() instanceof ItemBlock) {
            jsonObject.addProperty("name", GameData.getBlockRegistry().getNameForObject(Block.getBlockFromItem(src.getItem())));
        } else {
            jsonObject.addProperty("name", GameData.getItemRegistry().getNameForObject(src.getItem()));
        }

        jsonObject.addProperty("metadata", src.getItemDamage());

        jsonObject.add("nbt", context.serialize(src.stackTagCompound, NBTTagCompound.class));

        return jsonObject;
    }
}
