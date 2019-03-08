import java.time.temporal.ChronoUnit;


public class CostGenerator {

	public CostGenerator() {
		// TODO Auto-generated constructor stub
	}

	public static double generateCost(Ticket t, Slot s){
		long days = ChronoUnit.DAYS.between(t.getEntryDate(), t.getExpiryDate());
		long hours = ChronoUnit.HOURS.between(t.getEntryTime(), t.getExpiryTime());
		if(hours < 0){
			days -= 1;
			hours += 24;
		}
		//System.out.println(days + "   " + hours);
		int typeSlot = s.getSlotType();
		double slotTypeCost = 50 * typeSlot;
		//System.out.println(slotTypeCost);
		//double cost = 0;
		//if(hours < 0  && days >= 1)
			//cost = (days - 1) * slotTypeCost;
		hours = (days * 24) + hours;
		//System.out.println(hours);
		slotTypeCost = slotTypeCost * hours / 24.0;
		
		t.setServiceCost(slotTypeCost);
		//System.out.println(slotTypeCost);
		return slotTypeCost;
	}
}

