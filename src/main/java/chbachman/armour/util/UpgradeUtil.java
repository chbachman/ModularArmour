package chbachman.armour.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import chbachman.api.IModularItem;
import chbachman.api.IUpgrade;
import chbachman.armour.handler.UpgradeHandler;
import chbachman.armour.upgrade.UpgradeException;
import cpw.mods.fml.common.Loader;

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
        
        if(Loader.isModLoaded("Baubles")){
        	IInventory inventory = BaublesApi.getBaubles(player);
        	
        	for(int i = 0; i < inventory.getSizeInventory(); i++){
        		ItemStack bauble = inventory.getStackInSlot(i);
        		
        		if(bauble != null && bauble.getItem() instanceof IBauble){
        			for(IUpgrade armourUpgrade : NBTHelper.getNBTUpgradeList(bauble)){
        				if(armourUpgrade.getId() == upgrade.getId()){
        					return true;
        				}
        			}
        		}
        	}
        }
        
        return false;
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
    
    public static List<IUpgrade> getDependencyList(IUpgrade upgrade) {
        List<IUpgrade> list = new ArrayList<IUpgrade>();
        list.add(upgrade);
        return list;
    }
    
    public static boolean doesItemStackContainUpgrade(ItemStack stack, IUpgrade upgrade) {
        
        if (stack.stackTagCompound == null) {
            NBTHelper.createDefaultStackTag(stack);
            return false;
        }
        
        NBTUpgradeList list = NBTHelper.getNBTUpgradeList(stack.stackTagCompound);
        
        return list.contains(upgrade);
    }
    
}
