package chbachman.armour.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import chbachman.api.item.IModularItem;
import chbachman.api.nbt.NBTHelper;
import chbachman.api.upgrade.IUpgrade;
import chbachman.api.util.ArmourSlot;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

/**
 * Generic event handler, used for onEquip and onDequip methods.
 * @author Chbachman
 *
 */
public class GenericEventHandler {

	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (event.entity instanceof EntityPlayer && PlayerArmour.getFor((EntityPlayer) event.entity) == null) {
			PlayerArmour.register((EntityPlayer) event.entity);
		}
	}

	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent e) {
		EntityPlayer player = e.player;

		PlayerArmour storage = PlayerArmour.getFor(player);
		
		ItemStack[] armourList = player.inventory.armorInventory;
		
		for (int i = 0; i < armourList.length; i++) {

			ItemStack playerStack = armourList[i];
			ItemStack storedStack = storage.stacks[i];
			
			if(!ItemStack.areItemStacksEqual(playerStack, storedStack)){
				
				if(playerStack != null && playerStack.getItem() instanceof IModularItem){
					((IModularItem) playerStack.getItem()).onArmourEquip(player.worldObj, player, playerStack);
				}
				
				if(storedStack != null && storedStack.getItem() instanceof IModularItem){
					((IModularItem) storedStack.getItem()).onArmourDequip(player.worldObj, player, storedStack);
				}
				
			}
			
		}
		
		storage.update(armourList);
	}

	@SubscribeEvent
	public void onPlayerJoinWorld(PlayerEvent.PlayerLoggedInEvent e) {
		for (ItemStack stack : e.player.inventory.armorInventory) {
			if (stack != null && stack.getItem() instanceof IModularItem) {

				NBTHelper.createDefaultStackTag(stack);

				IModularItem armour = (IModularItem) stack.getItem();

				for (IUpgrade upgrade : NBTHelper.getNBTUpgradeList(stack.stackTagCompound)) {
					if (upgrade == null) {
						continue;
					}

					upgrade.onEquip(e.player.worldObj, e.player, stack, ArmourSlot.getArmourSlot(armour.getSlot()));
				}

			}
		}
	}

}
