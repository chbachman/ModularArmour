package chbachman.api.configurability;

public class Percentage{

	private byte percentage;

	public Percentage(int amount) {
		percentage = (byte) amount;
	}

	public float getPercentage(){
		return percentage / 100F;
	}

	public void setPercentage(int amount){
		percentage = (byte) amount;
	}

	public int getAmount(){
		return percentage;
	}

}
