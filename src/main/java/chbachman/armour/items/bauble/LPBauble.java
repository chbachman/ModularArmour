package chbachman.armour.items.bauble;

import chbachman.armour.items.armour.logic.LPUpgradeLogic;

public class LPBauble extends ItemBauble {

    public LPBauble() {
        this.holder = new LPUpgradeLogic(this);
    }

}
