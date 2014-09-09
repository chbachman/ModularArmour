package chbachman.armour.register;

import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

public class Thaumcraft extends Module{

	public Thaumcraft() {
		super("Thaumcraft");
	}

	@Override
	public void preInit() {
		
	}

	@Override
	public void init() {
		
	}

	@Override
	public void postInit() {
		ThaumcraftApi.registerObjectTag(Vanilla.heatedElectrum, new AspectList().add(Aspect.METAL, 4).add(Aspect.FIRE, 4).add(Aspect.GREED, 4));
		ThaumcraftApi.registerObjectTag(Vanilla.temperedElectrum, new AspectList().add(Aspect.METAL, 4).add(Aspect.ORDER, 4).add(Aspect.GREED, 4));
	}

	@Override
	public void registerUpgrades() {
		
	}

	@Override
	public void registerUpgradeRecipes() {
		
	}
	
	

}
