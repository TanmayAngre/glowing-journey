

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class ParkingLotListener
 *
 */
@WebListener
public class ParkingLotListener implements ServletContextListener{

    /**
     * Default constructor. 
     */
    public ParkingLotListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextAttributeListener#attributeAdded(ServletContextAttributeEvent)
     */
    
	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    	System.out.println("Project undeployed!");
    }

	/**
     * @see ServletContextAttributeListener#attributeReplaced(ServletContextAttributeEvent)
     */
    
	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    	System.out.println("Inside contextInitialized...");
    	ServletContext e = arg0.getServletContext();
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
		catch(SQLException E){
			System.out.println("ResultSet exception caught"+E);
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
		e.setAttribute("pl", pl);
		Properties p = new Properties();
		InputStream input = null;
		input = ParkingLotListener.class.getClassLoader().getResourceAsStream("slotconfig.properties");
		try{
			p.load(input);
			e.setAttribute("p",p);
		}
		catch(IOException exx){
			System.out.println("Exception caught" + e);
			exx.printStackTrace();
		}
		finally{
			if(input!=null){
				try{
					input.close();
				}
				catch(IOException ex){
					System.out.println("Exception caught while closing stream");
					ex.printStackTrace();
				}
				
			}
		}
    }
	
}
