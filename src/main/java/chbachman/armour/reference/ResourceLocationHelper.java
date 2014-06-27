package chbachman.armour.reference;

import net.minecraft.util.ResourceLocation;

public class ResourceLocationHelper {
	
	public static ResourceLocation getResourceLocation(String name){
		return new ResourceLocation(Reference.TEXTURE_LOCATION + name);
	}

}
