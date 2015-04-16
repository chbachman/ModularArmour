package chbachman.armour.network;

public interface IContainerSyncable{
	
	void clientLoad(ArmourPacket p);
	
	void serverLoad(ArmourPacket p);

}
