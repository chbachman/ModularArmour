package chbachman.armour.plugins;


public class Thaumcraft
{
    
    /*
    
    public static void preInit()
    {
    }
    
    public static void initialize()
    {
    }
    
    public static void postInit()
    {
        if (!Loader.isModLoaded("Thaumcraft")) {
            return;
        }
        
    }
    
    public static void registerStack(ItemStack paramItemStack, String paramString)
    {
        registerStack(paramItemStack, parseAspects(paramString));
    }
    
    public static void registerStack(ItemStack paramItemStack, AspectList paramAspectList)
    {
        if (paramItemStack != null)
            ThaumcraftApi.registerObjectTag(paramItemStack, paramAspectList);
    }
    
    private static AspectList parseAspects(String paramString)
    {
        AspectList localAspectList = new AspectList();
        String[] arrayOfString1 = paramString.split(",");
        
        for (int i = 0; i < arrayOfString1.length; i++) {
            String[] arrayOfString2 = arrayOfString1[i].trim().split(" ");
            arrayOfString2[1] = arrayOfString2[1].toLowerCase();
            Aspect localAspect = Aspect.getAspect(arrayOfString2[1]);
            
            if (localAspect != null) {
                localAspectList.add(localAspect, Integer.parseInt(arrayOfString2[0]));
            }
        }
        return localAspectList;
    }
    */
}