package chbachman.armour.upgrade.upgradeList;

import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import chbachman.api.Upgrade;
import chbachman.armour.reference.ArmourSlot;

public class UpgradeMagnet extends Upgrade{

	public UpgradeMagnet() {
		super("magnet");
	}
	
	public int onTick(World world, EntityPlayer player, ItemStack stack, ArmourSlot slot) {
		AxisAlignedBB box = player.boundingBox.expand(5, 5, 5);
		
		@SuppressWarnings("unchecked")
		List<EntityItem> list = world.getEntitiesWithinAABB(EntityItem.class, box);
		
		int energy = 0;
		
		for (EntityItem e : list)
		{
			if (e.age >= 10)
			{
				double x = player.posX - e.posX;
				double y = player.posY - e.posY;
				double z = player.posZ - e.posZ;
				
				double length = Math.sqrt(x * x + y * y + z * z) * 2;
				
				x = x / length + player.motionX / 2;
				y = y / length + player.motionY / 2;
				z = z / length + player.motionZ / 2;
				
				e.motionX =+ x;
				e.motionY =+ y;
				e.motionZ =+ z;
				e.isAirBorne = true;
				
				if (e.isCollidedHorizontally)
				{
					e.motionY += 1;
				}
				
				energy += 100;
			}
		}
		
		return energy;
    }

}
