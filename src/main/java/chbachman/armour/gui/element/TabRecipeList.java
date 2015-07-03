package chbachman.armour.gui.element;

import java.util.LinkedList;
import java.util.List;

import chbachman.api.registry.UpgradeRegistry;
import chbachman.api.upgrade.Recipe;
import cofh.lib.gui.GuiBase;
import cofh.lib.gui.element.ElementListBox;
import cofh.lib.gui.element.TabBase;
import cofh.lib.gui.element.listbox.IListBoxElement;
import cofh.lib.gui.element.listbox.ListBoxElementText;

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
	
	private final List<IListBoxElement> elements = new LinkedList<IListBoxElement>();
	
	public void updateList(){
		
		for(int i = 0; i < elements.size(); i++){ //A really bad clear method.
			elements.remove(i);
			this.list.removeAt(i);
		}
		
		for(int i = 0; i < indicies.size; i++){
			int index = indicies.get(i);
			
			ListBoxElementText text = new ListBoxElementText(UpgradeRegistry.getRecipeList().get(index).getRecipeOutput().getName());
			
			this.list.add(text);
			elements.add(text);
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
