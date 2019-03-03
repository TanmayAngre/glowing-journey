import java.util.Properties;


public class AllocateController {
	
	public AllocateController() {
		// TODO Auto-generated constructor stub
		
	}
//find an empty slot if present
	public static Slot getEmptySlot(ParkingLot pl, Vehicle v, Properties p){
		System.out.println((v.getType()));
		for(Slot slot:pl.getSlotAvailableList()){
				if(slot.getSlotType() == Integer.parseInt(p.getProperty(v.getType())) && slot.isAvailable()){
					pl.getSlotAvailableList().remove(slot);
					pl.getSlotFilledList().add(slot);
					slot.setIsAvailable(false);
					return slot;
				}
		}
		return null;
	}
	
	
}
