package chbachman.armour.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import chbachman.api.IModularItem;
import chbachman.api.IUpgrade;
import chbachman.armour.handler.UpgradeHandler;
import chbachman.armour.items.ItemModularArmour;
import chbachman.armour.upgrade.UpgradeException;
import chbachman.armour.upgrade.UpgradeList;
import cofh.lib.util.helpers.StringHelper;

public class UpgradeUtil {
    
    public static boolean doesPlayerHaveUpgrade(EntityPlayer player, IUpgrade upgrade) {
        ItemStack[] armourArray = player.inventory.armorInventory;
        
        for (ItemStack armour : armourArray) {
            
            if (armour != null) {
                if (armour.getItem() instanceof IModularItem) {
                    
                    for (IUpgrade armourUpgrade : NBTHelper.getNBTUpgradeList(armour.stackTagCompound)) {
                        
                        if (upgrade.getId() == armourUpgrade.getId()) {
                            return true;
                        }
                    }
                }
            }
            
        }
        
        return false;
    }
    
    public static boolean doesPlayerHaveUpgrade(EntityPlayer player, String string) {
        return doesPlayerHaveUpgrade(player, getUpgradeFromName(string));
    }
    
    public static IUpgrade getUpgradeFromName(String unlocalizedName) {
        for (IUpgrade upgrade : UpgradeList.list) {
            if (upgrade.getUnlocalizedName().equals(unlocalizedName)) {
                return upgrade;
            }
        }
        
        for (IUpgrade upgrade : UpgradeList.list) {
            if (upgrade.getUnlocalizedName().equals("upgrade.chbachman." + StringHelper.camelCase(unlocalizedName).replace(" ", "") + ".name")) {
                return upgrade;
            }
        }
        
        return null;
    }
    
    public static void removeUpgrade(ItemStack container, IUpgrade upgrade) {
        
        ItemStack stack = container.copy();
        
        if (stack.stackTagCompound == null) {
            NBTHelper.createDefaultStackTag(stack);
            return;
        }
        
        if (stack.getItem() instanceof IModularItem) {
            
            NBTUpgradeList list = NBTHelper.getNBTUpgradeList(stack.stackTagCompound);
            
            Iterator<IUpgrade> iterator = list.iterator();
            while(iterator.hasNext()){
                if (iterator.next().getId() == upgrade.getId()) {
                    iterator.remove();
                }
            }
            
            
            try {
                Iterator<IUpgrade> iterator2 = list.iterator();
                
                while(iterator2.hasNext()){
                    
                
                    UpgradeHandler.checkDependencies(stack, iterator.next());
                }
                
            } catch (UpgradeException e) {
                throw new UpgradeException("The %s Upgrade requires the %s Upgrade", e.cause, upgrade);
            }
            
            container.stackTagCompound = stack.stackTagCompound;
        }
        
    }
    
    public static List<String> getDependencyList(IUpgrade upgrade) {
        List<String> list = new ArrayList<String>();
        list.add(upgrade.getUnlocalizedName());
        return list;
    }
    
    public static List<String> getDependencyList(String upgrade) {
        List<String> list = new ArrayList<String>();
        list.add(upgrade);
        return list;
    }
    
    public static boolean doesNBTListContainUpgrade(NBTUpgradeList list, IUpgrade upgrade) {
        
        for (IUpgrade up : list) {
            if (up.getId() == upgrade.getId()) {
                return true;
            }
        }
        
        return false;
    }
    
    public static boolean doesItemStackContainUpgrade(ItemStack stack, IUpgrade upgrade) {
        
        if (stack.stackTagCompound == null) {
            NBTHelper.createDefaultStackTag(stack);
            return false;
        }
        
        NBTUpgradeList list = NBTHelper.getNBTUpgradeList(stack.stackTagCompound);
        
        return doesNBTListContainUpgrade(list, upgrade);
    }
    
    public static boolean doesItemStackContainUpgrade(ItemStack stack, String name) {
        
        return doesItemStackContainUpgrade(stack, getUpgradeFromName(name));
    }
    
}
