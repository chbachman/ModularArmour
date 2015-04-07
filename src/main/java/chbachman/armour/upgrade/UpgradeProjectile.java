package chbachman.armour.upgrade;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public abstract class UpgradeProjectile extends KeybindUpgrade{
	
	public UpgradeProjectile(String name) {
		super(name);
	}
	
	@Override
	public void keyPressServer(EntityPlayer player){
		
	}

	@Override
	public boolean keyPress(EntityPlayer player){
		return false;
	}
	
	@SuppressWarnings("unused")
	private static class EntityBaseProjectile extends EntityThrowable{

		public EntityBaseProjectile(World p_i1582_1_) {
			super(p_i1582_1_);
		}

		@Override
		protected void onImpact(MovingObjectPosition p_70184_1_){
			
		}

		@Override
		protected float getGravityVelocity(){
			return 0;
		}
		
		
		
	}
	
}
