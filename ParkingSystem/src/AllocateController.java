import java.util.Properties;


public class AllocateController {
	
	public AllocateController() {
		// TODO Auto-generated constructor stub
		
	}

	public Slot getEmptySlot(ParkingLot pl, Vehicle v, Properties p){
		//code for isAvailableSlot() for vehicle v...
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
