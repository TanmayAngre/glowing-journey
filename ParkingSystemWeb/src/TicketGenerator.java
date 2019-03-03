import java.time.LocalDate;
import java.time.LocalTime;


public class TicketGenerator {
	//private int serviceTime;
	public TicketGenerator() {
		// TODO Auto-generated constructor stub
		
	}
	
	public static Ticket generateTicket(int serviceTime){
		Ticket t = new Ticket(serviceTime);
		t.setEntryDate(LocalDate.now());
		t.setEntryTime(LocalTime.now());
		int entryH = t.getEntryTime().getHour();
		//System.out.println("Enter the service time (in hours):");
		//totalParkTime = getparkTime();
		int serviceDays = t.getTotalParkTime()/24;
		int plusServiceTime = t.getTotalParkTime()%24;
		t.setExpiryDate(t.getEntryDate().plusDays(serviceDays));
		t.setExpiryTime(t.getEntryTime().plusHours(plusServiceTime));
		int expiryH = t.getExpiryTime().getHour();
		if(expiryH < entryH)
			t.setExpiryDate(t.getExpiryDate().plusDays(1));
		return t;
	}

}
