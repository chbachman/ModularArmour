package chbachman.armour.gui.recipe;

import repack.cofh.lib.gui.GuiBase;
import repack.cofh.lib.gui.element.ElementListBox;
import repack.cofh.lib.gui.element.TabBase;
import repack.cofh.lib.gui.element.listbox.ListBoxElementText;
import chbachman.api.util.ImmutableArray;
import chbachman.armour.crafting.Recipe;

public class TabRecipeList extends TabBase{
	
	ImmutableArray<Recipe> recipeList;
	
	ElementListBox list;
	
	public TabRecipeList(GuiBase gui, ImmutableArray<Recipe> recipeList) {
		super(gui, LEFT);
		
		this.recipeList = recipeList;
		this.list = new ElementListBox(this.gui, 7, 7, 50, 100);
		
		this.maxHeight = 115;
		this.maxWidth = 62;
		
		this.addElement(list);
	}
	
	public void updateList(){
		this.list.removeAll();
		
		for(Recipe recipe : recipeList){
			this.list.add(new ListBoxElementText(recipe.getRecipeOutput().getName()));
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
