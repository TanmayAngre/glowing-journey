import java.sql.SQLException;
import java.util.*;
import java.io.*;

//import org.apache.log4j.Logger;

public class Main {
	//static Logger log = Logger.getLogger(Main.class.getName());
	
	
	

	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
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
		//add slotFilled parameter
		ParkingLot pl = null;
		pl = new ParkingLot("Andheri", 3, 200, slotAvailableList, slotFilledList);
		
		
		//log.debug("Hello this is a debug message");
        //log.info("Hello this is an info message");
		
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
			
			//check conditions for invalid inputs...(done)
			
			
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
			
			//Have to implement 2 more classes of vehicle...(Done)
			
			AllocateController ac = new AllocateController();
			//System.out.println(v.getType());
			Slot slot = ac.getEmptySlot(pl, v, p);
			//if slots full, can check for other lot for empty slots(optional)
			if(slot == null){
				System.out.println("\nParking Slot not available!!!");
				sc.close();
				return;
			}
			System.out.println(slot);
			System.out.println("Enter the service time (in hours) : ");
			int serviceTime = sc.nextInt();
			TicketGenerator tg = new TicketGenerator();
			Ticket tic = tg.generateTicket(serviceTime);
			CostGenerator cg = new CostGenerator();
			double cost = cg.generateCost(tic, slot);
			System.out.println(cost);
			/*Ticket tic = new Ticket(serviceTime, slot);
			tic.generateTicket();
			double cost = tic.generateCost();
			*/
			try{
				int u = jdbc.updateSlot(slot);
				System.out.println("DEBUG: " + u + " results updated");
				int x = jdbc.insertTicket(tic);
				TicketFileWriter.writeFile(tic, "E:\\didactic-fortnight\\ParkingSystem\\src\\ticket.csv");
				System.out.println("DEBUG: " + x + " results inserted in tickets");
				int y = jdbc.insertSlotHistory(tic, v, slot);
				SlotHistoryFileWriter.writeFile(tic, slot, v);
				System.out.println("DEBUG: " + y + " results inserted in slothistory");
				int z = jdbc.insertTicketHistory(tic);
				TicketHistoryFileWriter.writeFile(tic, "E:\\didactic-fortnight\\ParkingSystem\\src\\tickethistory.csv");
				System.out.println("DEBUG: " + z + " results inserted in tickethistory");
			}
			catch(Exception e){
				System.out.println("Exception caught");
				e.printStackTrace();
			}
			SlotFileWriter.writeSlotFile("E:\\didactic-fortnight\\ParkingSystem\\src\\slot.csv",pl);
		}
		else if(option == 2){
			//generating a task to free slot after service time...
			/*pl.addSlotAfter(tic, slot);*/
			
				System.out.println("Enter ticket number");
				String number = sc.next();
				//can do multithreading to deal with removing vehicle other than the latest one
				
				
				try{
					Ticket ticket = jdbc.retrieveTicket(number);
					Slot sslot = jdbc.retrieveSlot(number);        //define this method in jdbccon
					CostGenerator costG = new CostGenerator();
					double newCost = costG.generateCost(ticket, sslot);
					System.out.println("New Cost is:"+newCost);
					//jdbc.updateTicket(ticket);
					jdbc.insertTicketHistory(ticket);
					TicketHistoryFileWriter.writeFile(ticket, "E:\\didactic-fortnight\\ParkingSystem\\src\\tickethistory.csv");
					RevokeController rc = new RevokeController();
					Slot filled = rc.revokeFilledSlot(pl, sslot);
					System.out.println("DEBUG:" + filled.isAvailable());
					jdbc.updateSlot(sslot);
					jdbc.deleteTicket(ticket);
				}
				catch(Exception e){
					System.out.println(e);
				}
					
		}
		
		System.out.println("Enter 3 to repeat the process...\n");
		//System.out.println(     slot.isAvailable());
		ch = sc.next();
		}while(ch.equals("3"));
		sc.close();
	}

}
//remove objects, make methods static... add code for file initialize option...