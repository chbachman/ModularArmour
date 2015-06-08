package chbachman.armour.gui.recipe;

import repack.cofh.lib.gui.GuiBase;
import repack.cofh.lib.gui.element.ElementListBox;
import repack.cofh.lib.gui.element.TabBase;
import repack.cofh.lib.gui.element.listbox.ListBoxElementText;
import chbachman.api.registry.UpgradeRegistry;
import chbachman.api.upgrade.Recipe;

import com.badlogic.gdx.utils.IntArray;

public class TabRecipeList extends TabBase{
	
	IntArray indicies;
	
	ElementListBox list;
	
	public TabRecipeList(GuiBase gui, IntArray indicies) {
		super(gui, LEFT);
		
		this.indicies = indicies;
		
		
		
		
		this.maxHeight = 115;
		
		ListBoxElementText text;
		int max = 0;
		for(Recipe recipe: UpgradeRegistry.getRecipeList()){
			text = new ListBoxElementText(recipe.getRecipeOutput().getName());
			max = Math.max(text.getWidth(), max);
		}
		
		this.list = new ElementListBox(this.gui, 7, 7, max, 100);
		
		this.maxWidth = max + 12;
		
		this.addElement(list);
	}
	
	public void updateList(){
		this.list.removeAll();
		
		for(int i = 0; i < indicies.size; i++){
			int index = indicies.get(i);
			
			ListBoxElementText text = new ListBoxElementText(UpgradeRegistry.getRecipeList().get(index).getRecipeOutput().getName());
			
			this.list.add(text);
		}

	}
	
	@Override
	public void drawBackground(int mouseX, int mouseY, float gameTicks){
		super.drawBackground(mouseX, mouseY, gameTicks);
	}

	@Override
	public void drawForeground(int mouseX, int mouseY){
		super.drawForeground(mouseX, mouseY);
		
	}

	@Override
	public boolean onMouseWheel(int mouseX, int mouseY, int movement){
		return super.onMouseWheel(mouseX, mouseY, movement);
		
	}
	
	
	
	
	
	

}
