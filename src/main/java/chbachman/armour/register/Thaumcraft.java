package chbachman.armour.register;

import thaumcraft.api.ItemApi;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import chbachman.api.registry.UpgradeRegistry;
import chbachman.api.upgrade.IUpgrade;
import chbachman.api.upgrade.Recipe;
import chbachman.api.util.ArmourSlot;
import chbachman.armour.upgrade.upgradeList.UpgradeBasic;

public class Thaumcraft implements Module {

    public static IUpgrade visDiscount;
    public static IUpgrade gogglesOfRevealing;

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
        visDiscount = new UpgradeBasic("visDiscount");
        gogglesOfRevealing = new UpgradeBasic("gogglesOfRevealing").setArmourSlot(ArmourSlot.HELMET);
    }

    @Override
    public void registerUpgradeRecipes() {
        UpgradeRegistry.registerRecipe(new Recipe(gogglesOfRevealing, "iii", "igi", "iii", 'i', "ingotIron", 'g', ItemApi.getItem("itemGoggles", 0)));
        UpgradeRegistry.registerRecipe(new Recipe(visDiscount, "c c", "ccc", "ccc", 'c', ItemApi.getItem("itemResource", 7)));
    }

}
