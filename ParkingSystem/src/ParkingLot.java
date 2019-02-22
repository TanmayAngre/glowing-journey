import java.util.*;


public class ParkingLot {

	private String location;
	private int floors;
	private int totalSlots;
	private ArrayList<Slot> slotAvailableList;
	private ArrayList<Slot> slotFilledList;

	public ParkingLot(String location, int floors, int totalSlots,
			ArrayList<Slot> slotAvailable, ArrayList<Slot> slotFilled) {
		super();
		this.location = location;
		this.floors = floors;
		this.totalSlots = totalSlots;
		this.slotAvailableList = slotAvailable;
		this.slotFilledList = slotFilled;
	}
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getFloors() {
		return floors;
	}

	public void setFloors(int floors) {
		this.floors = floors;
	}

	public int getTotalSlots() {
		return totalSlots;
	}

	public void setTotalSlots(int totalSlots) {
		this.totalSlots = totalSlots;
	}

	public ArrayList<Slot> getSlotAvailableList() {
		return slotAvailableList;
	}

	public void setSlotAvailableList(ArrayList<Slot> slotAvailable) {
		this.slotAvailableList = slotAvailable;
	}
	
	public ArrayList<Slot> getSlotFilledList() {
		return slotFilledList;
	}

	public void setSlotFilledList(ArrayList<Slot> slotFilled) {
		this.slotFilledList = slotFilled;
	}

}

