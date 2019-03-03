import java.sql.SQLException;
import java.util.*;
import java.io.*;

//import org.apache.log4j.Logger;

public class Main {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		ParkingLot pl = null;
		
		/* --------------------Initialization Part----------------------- */
		
		ArrayList<Slot> slotAvailableList = null;
		ArrayList<Slot> slotFilledList = null;
		
		JDBCCon jdbc = JDBCCon.getInstanceJDBC();
		try {
			List[] a = jdbc.retrieveSlotLists();
			slotAvailableList = (ArrayList<Slot>) a[0];
			slotFilledList = (ArrayList<Slot>) a[1];
			
			System.out.println("DEBUG: slotFilledList size = "+slotFilledList.size());
			System.out.println("DEBUG: slotAvailableList size = "+slotAvailableList.size());
		}
		catch(SQLException e){
			System.out.println("ResultSet exception caught"+e);
		}
		finally{
			
		}
		
		/* --------------Initiation from file------------- */
			
		//ArrayList<Slot> slotAvailableListFromFile = null;
		//ArrayList<Slot> slotFilledListFromFile = null;
		/*
		List[] fileLists = SlotFileReader.readFile("C:\\Users\\TANMAY\\Desktop\\glowing-journey\\ParkingSystem\\src\\slot.csv");
		slotAvailableList = (ArrayList<Slot>) fileLists[0];
		slotFilledList = (ArrayList<Slot>) fileLists[1];
		pl = new ParkingLot("Andheri", 3, 200, slotAvailableList, slotFilledList);
		*/
		
		pl = new ParkingLot("Andheri", 3, 200, slotAvailableList, slotFilledList);
		
		Properties p = new Properties();
		InputStream input = null;
		input = Main.class.getClassLoader().getResourceAsStream("slotconfig.properties");
		try{
			p.load(input);
		}
		catch(IOException e){
			System.out.println("Exception caught" + e);
			e.printStackTrace();
		}
		finally{
			if(input!=null){
				try{
					input.close();
				}
				catch(IOException e){
					System.out.println("Exception caught while closing stream");
					e.printStackTrace();
				}
			}
		}
		/* ----------------End of initialization----------------- */
		
		String ch = null;
		do{
		//String asd = null;	
		System.out.println("\nEnter an option: 1. Enter a vehicle...\n 2. Remove a vehicle...\n 3. Exit\n");
		int option = sc.nextInt();
		sc.nextLine();
		if(option == 1){
			/* ------------------create a Vehicle instance---------------------- */
			System.out.print("\nEnter the details of your vehicle... ");
			System.out.print("\nEnter the model name :");
			//sc.next();
			
			String m = sc.nextLine();
			System.out.print("M:"+m);
			if(m.equals("")){
				System.out.print("Enter the name again");
				m = sc.nextLine();
			}
			System.out.println("m: " + m);
			//while(m==null)
				//m = sc.nextLine();
			String pno = null;
			do{
				System.out.println("\nEnter the plate number:");
				pno = sc.nextLine();
			}while(pno.length()!=13);
			String t = null;
			do{
				System.out.println("\nEnter the type of vehicle : (twowheeler/minifour/maxfour)");
				t = sc.next();
			}while(!(t.equals("twowheeler") || t.equals("minifour") || t.equals("maxfour")));
			
			//
			Vehicle v = null;
			if(t.equals("twowheeler")){
				v = new TwoWheeler( m, pno, t);
			}
			if(t.equals("minifour")){
				v = new FourMini( m, pno, t);
			}
			if(t.equals("maxfour")){
				v = new MaxFour( m, pno, t);
			}
			v.display(p);
			
			//System.out.println(v.getType());
			/* ---------------Finding empty slot ----------------------- */
			Slot slot = AllocateController.getEmptySlot(pl, v, p);
			if(slot == null){
				System.out.println("\nParking Slot not available!!!");
				sc.close();
				return;
			}
			System.out.println("Slot allocated: " + slot);
			System.out.println("Enter the service time (in hours) : ");
			int serviceTime = sc.nextInt();
			
			/* ----------------Generate Ticket and Cost--------------------- */
			Ticket tic = TicketGenerator.generateTicket(serviceTime);
			double cost = CostGenerator.generateCost(tic, slot);
			System.out.println(cost);
			try{
				/* --------------------Database inserts--------------------- */
				/* --------------------File writes-------------------- */
				int u = jdbc.updateSlot(slot);
				System.out.println("DEBUG: " + u + " results updated");
				int x = jdbc.insertTicket(tic);
				TicketFileWriter.writeFile(tic, "C:\\Users\\TANMAY\\Desktop\\glowing-journey\\ParkingSystem\\src\\ticket.csv");
				System.out.println("DEBUG: " + x + " results inserted in tickets");
				int y = jdbc.insertSlotHistory(tic, v, slot);
				SlotHistoryFileWriter.writeFile(tic, slot, v);
				System.out.println("DEBUG: " + y + " results inserted in slothistory");
				int z = jdbc.insertTicketHistory(tic);
				TicketHistoryFileWriter.writeFile(tic, "C:\\Users\\TANMAY\\Desktop\\glowing-journey\\ParkingSystem\\src\\tickethistory.csv");
				System.out.println("DEBUG: " + z + " results inserted in tickethistory");
			}
			catch(Exception e){
				System.out.println("Exception caught");
				e.printStackTrace();
			}
		}
		else if(option == 2){
				System.out.println("Enter ticket number");
				String number = sc.next();
				
				try{
					/* -----------------------Retrieve Ticket and Slot------------------- */
					Ticket ticket = jdbc.retrieveTicket(number);
					Slot sslot = jdbc.retrieveSlot(number);       
					
					/* ----------------------Generate new Cost ---------------------- */
					double newCost = CostGenerator.generateCost(ticket, sslot);
					System.out.println("New Cost is:"+newCost);
					jdbc.insertTicketHistory(ticket);
					TicketHistoryFileWriter.writeFile(ticket, "C:\\Users\\TANMAY\\Desktop\\glowing-journey\\ParkingSystem\\src\\tickethistory.csv");
					/* -----------------Revoking the filled slot----------- */
					Slot filled = RevokeController.revokeFilledSlot(pl, sslot);
					System.out.println("DEBUG:" + filled.isAvailable());
					jdbc.updateSlot(sslot);
					/* Delete ticket entry after exit... Ticket Entry persists in TicketHistory */
					jdbc.deleteTicket(ticket);
				}
				catch(Exception e){
					System.out.println(e);
				}
					
		}
		/* -------------Writing the state of slots back to file required for initialization---------------- */
		System.out.println("Enter 3 to repeat the process...\n");
		ch = sc.next();
		SlotFileWriter.writeSlotFile("C:\\Users\\TANMAY\\Desktop\\glowing-journey\\ParkingSystem\\src\\slot.csv",pl);
		
		}while(ch.equals("3"));
		sc.close();
		
	}

}