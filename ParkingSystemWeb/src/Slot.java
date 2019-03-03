
public class Slot {

	private int floor;
	private int slotType;
	private int slotNo;
	//1 : Bike
	//2 : Car
	//3 : Truck / Bus
	private boolean isAvailable = true;

	Slot(int f, int s, int no){
		floor = f;
		slotType = s;
		slotNo = no;
		//isAvailable = i;
	}
	
	public boolean equals(Object o){
		if(o==this)
			return true;
		if(!(o instanceof Slot))
			return false;
		Slot s =(Slot)o;
		return s.slotNo == this.slotNo;
	}
	
	public int hashCode(){
		int result = 17;
		return (31*result + slotNo);
	}
	
	public int getFloor(){
		return floor;
	}
	
	public int getSlotType(){
		return slotType;
	}
	
	public boolean isAvailable(){
		return isAvailable;
	}
	
	

	/*public boolean isAvailable() {
		return isAvailable;
	}*/

	public void setIsAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public void setSlotType(int slotType) {
		this.slotType = slotType;
	}

	public int getSlotNo() {
		return slotNo;
	}

	public void setSlotNo(int slotNo) {
		this.slotNo = slotNo;
	}
	
}
