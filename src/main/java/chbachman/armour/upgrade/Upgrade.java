package chbachman.armour.upgrade;

import java.util.ArrayList;
import java.util.List;

public abstract class Upgrade {

	public static List<Upgrade> upgradeList = new ArrayList<Upgrade>();

	private int id;
	private String name;

	public Upgrade(String name) {
		this.id = getNextAvailableId();
		upgradeList.add(this);
		this.name = name;
	}

	private int getNextAvailableId() {
		return upgradeList.size();
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getId(){
		return this.id;
	}

}
