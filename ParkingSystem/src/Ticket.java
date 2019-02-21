import java.util.*;
import java.time.*;

public class Ticket {

	private String ticketNo = String.valueOf(new Date().getTime());
	private int totalParkTime;
	private LocalDate entryDate;
	private LocalTime entryTime;
	private LocalDate expiryDate;
	private LocalTime expiryTime;
	private double serviceCost;
	
	public double getServiceCost() {
		return serviceCost;
	}

	public void setServiceCost(double serviceCost) {
		this.serviceCost = serviceCost;
	}

	
	
	Ticket(int t){
		totalParkTime = t;
	}
	
	public String getTicketNo() {
		return ticketNo;
	}

	public void setTicketNo(String ticketNo) {
		this.ticketNo = ticketNo;
	}

	public int getTotalParkTime() {
		return totalParkTime;
	}

	public void setTotalParkTime(int totalParkTime) {
		this.totalParkTime = totalParkTime;
	}

	public LocalDate getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(LocalDate entryDate) {
		this.entryDate = entryDate;
	}

	public LocalTime getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(LocalTime entryTime) {
		this.entryTime = entryTime;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	public LocalTime getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(LocalTime expiryTime) {
		this.expiryTime = expiryTime;
	}

	
	
	
	public void displayTicket(ParkingLot pl, Slot s){
		System.out.println("\n");
		System.out.println("---------------------- Ticket Generation ------------------------\n");
		System.out.println("Ticket Serial No. : " + getTicketNo());
		System.out.println("Lot location:" + pl.getLocation());
		System.out.println("Slot ID:" + s.getSlotNo());
		System.out.println("Entry Time : " + getEntryDate() + "  " + getEntryTime());
		System.out.println("Expiry Time : " + getExpiryDate() + "  " + getExpiryTime());
		System.out.println("Total Cost : " + getServiceCost());
	}
	
	/*public Ticket returnRef(long ticNo){
		return this;
	}*/

}
