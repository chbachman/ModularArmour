package chbachman.armour.gui.element;

import chbachman.api.registry.UpgradeRegistry;
import chbachman.api.upgrade.IUpgrade;
import chbachman.api.upgrade.Recipe;
import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.TabBase;
import cofh.lib.gui.element.listbox.ListBoxElementText;
import cofh.lib.util.helpers.StringHelper;

import com.badlogic.gdx.utils.IntArray;

public class TabRecipeList extends TabBase {

    IntArray indicies;

    ElementUpgradeListBox list;

    public TabRecipeList(GuiBase gui, IntArray indicies) {
        super(gui, LEFT);

        this.indicies = indicies;

        this.maxHeight = 115;

        ListBoxElementText text;
        int max = 0;
        for (Recipe recipe : UpgradeRegistry.getRecipeList()) {
            text = new ListBoxElementText(StringHelper.localize(recipe.getRecipeOutput().getName()));
            max = Math.max(text.getWidth(), max);
        }

        this.list = new ElementUpgradeListBox(this.gui, 7, 7, max, 100) {

            public void onUpgradeSelected(IUpgrade upgrade, int index) {
                TabRecipeList.this.onUpgradeSelected(upgrade, index);
            }

        };

        this.maxWidth = Math.min(max + 12, 132);

        this.addElement(list);
    }

    public void updateList() {

        this.list.removeAll();

        for (int i = 0; i < indicies.size; i++) {
            int index = indicies.get(i);

            this.list.add(UpgradeRegistry.getRecipeList().get(index).getRecipeOutput());
        }

    }

    public void onUpgradeSelected(IUpgrade upgrade, int index) {

    }
}
