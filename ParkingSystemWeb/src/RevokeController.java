
public class RevokeController {

	public RevokeController() {
		// TODO Auto-generated constructor stub
	}
//revoke filled slot and add to available slots list
	public static Slot revokeFilledSlot(ParkingLot pl, Slot slot){
			pl.getSlotFilledList().remove(slot);
			pl.getSlotAvailableList().add(slot);
			slot.setIsAvailable(true);
			return slot;
		
	}
}
