import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


public class JDBCCon {

	private static JDBCCon jdbc;
	
	private JDBCCon() {
		// TODO Auto-generated constructor stub
	}

	public static JDBCCon getInstanceJDBC(){
		if(jdbc == null)
			jdbc = new JDBCCon();
		return jdbc;
	}
	
	private Connection getConnection() throws ClassNotFoundException, SQLException{
		Connection con = null;
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/parkSystem","root","");
		return con;
	}
	
	/*  ----------------------------Retrieve Lists----------------------------------- */
	public List[] retrieveSlotLists() throws SQLException{
		ResultSet rs = null;
		ArrayList<Slot> available = new ArrayList<>();
		ArrayList<Slot> filled = new ArrayList<>();
		Connection con = null;
		PreparedStatement ps = null;
		List a[] = new ArrayList[2];
		try{
			con = this.getConnection();
			ps = con.prepareStatement("SELECT * FROM slots");
			rs = ps.executeQuery();
			
			while(rs.next()){
				//System.out.println("90");
				Slot s = new Slot(rs.getInt(2),rs.getInt(3),rs.getInt(1));
				if(rs.getInt(4)!=1){
					filled.add(s);
					s.setIsAvailable(false);
				}
				else if(rs.getInt(4)==1){
					available.add(s);
					s.setIsAvailable(true);
				}
				//System.out.println("Slot "+s.getSlotNo()+" removed");
			}
			
			a[0] = available;
			a[1] = filled;
		}
		catch(Exception e){
			System.out.println("Lot Lists ResultSet empty");
			e.printStackTrace();
		}
		finally{
			if(rs!=null)
				rs.close();
			if (ps!=null) 
        		ps.close();  
        	if(con!=null)  
        		con.close();  
		}
		return a;
	}
	
	/* --------------------------------RetrieveSlot------------------------------------ */
	public Slot retrieveSlot(String ticketNo) throws SQLException, ClassNotFoundException{
		ResultSet rs = null;
		//int updated = 0;
		Connection con = null;    
        PreparedStatement ps = null;
        Slot s = null;
        //LocalDateTime dt = null;
        try{
        	con = this.getConnection();
        	ps = con.prepareStatement("select floor,slotType,SlotNumber from slothistory where ID = ?");
        	ps.setString(1, ticketNo);
        	rs = ps.executeQuery();
        	rs.next();
        	int floor = rs.getInt(1);
        	int slotType = rs.getInt(2);
        	int slotNumber = rs.getInt(3);
        	s = new Slot(floor,slotType,slotNumber);
        }
        catch(Exception e){
        	System.out.println("Slot ResultSet empty");
        	e.printStackTrace();
        }
        finally{  
        	if(rs!=null)
        		rs.close();
        	if(ps!=null) 
        		ps.close();  
        	if(con!=null)  
        		con.close();  
        } 
		return s;
	}
	
	/* -----------------------------------RetrieveTicket------------------------------- */
	public Ticket retrieveTicket(String ticketNo) throws SQLException, ClassNotFoundException{
		ResultSet rs = null;
		//int updated = 0;
		Connection con = null;    
        PreparedStatement ps = null;
        LocalDateTime dt = null;
        Ticket tic = null;
        try{
        	con = this.getConnection();
        	ps = con.prepareStatement("select * from tickets where TicketID = ?");
        	ps.setString(1, ticketNo);
        	rs = ps.executeQuery();
        	rs.next();
        	LocalDate datePart = rs.getDate(2).toLocalDate();
            LocalTime timePart = rs.getTime(3).toLocalTime();
            
            dt = LocalDateTime.of(datePart, timePart);
            LocalDateTime nowdt = LocalDateTime.now();
			long diff = dt.until(nowdt,ChronoUnit.HOURS);
			System.out.println("DEBUG: diff " + diff);
			TicketGenerator ticketG = new TicketGenerator();
			tic = ticketG.generateTicket(Integer.parseInt(String.valueOf(diff)));
			tic.setEntryDate(datePart);
			tic.setEntryTime(timePart);
			tic.setExpiryDate(LocalDate.now());
			tic.setExpiryTime(LocalTime.now());
			tic.setTicketNo(ticketNo);
			tic.setServiceCost(rs.getDouble(6));
		
        }
        catch(Exception e){
        	System.out.println("Ticket ResultSet empty");
        	e.printStackTrace();
        }
        finally{  
        	if(rs!=null)
        		rs.close();
        	if(ps!=null) 
        		ps.close();  
        	if(con!=null)  
        		con.close();  
        } 
		return tic;
	}
	
	/* ---------------------------------------InsertTicketHistory------------------------------------ */
	public int insertTicketHistory(Ticket t) throws SQLException, ClassNotFoundException{
		int updated = 0;
		Connection con = null;    
        PreparedStatement ps = null;
        try{
        	con = this.getConnection();
			ps = con.prepareStatement("insert into tickethistory values(?,?,?,?,?,?,?)");
			ps.setString(1,t.getTicketNo());
			ps.setDate(2,Date.valueOf(t.getEntryDate()));
			ps.setTime(3,Time.valueOf(t.getEntryTime()));
			ps.setDate(4,Date.valueOf(t.getExpiryDate()));
			ps.setTime(5,Time.valueOf(t.getExpiryTime()));
			ps.setDouble(6,t.getServiceCost());
			ps.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
			updated = ps.executeUpdate();
			System.out.println(updated + " results inserted in TicketHistory");
        }
        catch(Exception e){
        	System.out.println("TicketHistory ResultSet empty");
        	e.printStackTrace();
        }
        finally{  
        	if (ps!=null) 
        		ps.close();  
        	if(con!=null)  
        		con.close();  
        } 
		return updated;
	}
	
	/* ----------------------------Insert SlotHistory---------------------------------- */
	public int insertSlotHistory(Ticket t, Vehicle v, Slot slot) throws SQLException  
    {  
        Connection con = null;    
        PreparedStatement ps = null;  
        int recordCounter = 0;  
        try{  
        	con = this.getConnection();  
        	
        	ps = con.prepareStatement("insert into slothistory values(?,?,?,?,?,?,?)");
        	ps.setString(1,t.getTicketNo());
        	ps.setInt(2,slot.getSlotNo());
        	ps.setInt(3, slot.getSlotType());
        	ps.setInt(4, slot.getFloor());
        	ps.setString(5, v.getModel());
        	ps.setString(6, v.getPlateNo());
        	ps.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
        	int updated = ps.executeUpdate();
        	System.out.println(updated + " results inserted in SlotHistory");
        	recordCounter+=updated;
        }
        catch (Exception e){
        	e.printStackTrace();
        }
        finally{  
        	if (ps!=null) 
        		ps.close();  
        	if(con!=null)  
        		con.close();  
        }  
       return recordCounter;  
    }
	
	/* ----------------------------Insert Ticket-------------------------------- */
	public int insertTicket(Ticket t) throws SQLException  
    {  
        Connection con = null;    
        PreparedStatement ps = null;  
        int recordCounter = 0;  
        try{  
        	con = this.getConnection();  
        	
			ps = con.prepareStatement("insert into tickets values(?,?,?,?,?,?,?)");
			ps.setString(1,t.getTicketNo());
			ps.setDate(2,Date.valueOf(t.getEntryDate()));
			ps.setTime(3,Time.valueOf(t.getEntryTime()));
			ps.setDate(4,Date.valueOf(t.getExpiryDate()));
			ps.setTime(5,Time.valueOf(t.getExpiryTime()));
			ps.setDouble(6,t.getServiceCost());
			ps.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
			int updated1 = ps.executeUpdate();
			System.out.println(updated1 + " results inserted in Ticket");
			recordCounter+=updated1;
        }
        catch (Exception e){
        	e.printStackTrace();
        }
        finally{  
        	if (ps!=null) 
        		ps.close();  
        	if(con!=null)  
        		con.close();  
        }  
       return recordCounter;  
    }  
	
	
	/* ---------------------------------UpdateSlot----------------------------- */
	public int updateSlot(Slot s) throws SQLException, ClassNotFoundException{
		int updated = 0;
		Connection con = null;    
        PreparedStatement ps = null;
        try{
        	con = this.getConnection();
        	ps = con.prepareStatement("update slots set Available = ? where slotNumber = ?");
        	if(s.isAvailable())
        		ps.setInt(1, 1);
        	else
        		ps.setInt(1, 0);
        	ps.setInt(2, s.getSlotNo());
        	updated = ps.executeUpdate();
        	System.out.println(updated + " results updated in Slot");
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        finally{  
        	if (ps!=null) 
        		ps.close();  
        	if(con!=null)  
        		con.close();  
        } 
		return updated;
	}
	
	/* ------------------------------------UpdateTicket------------------------------------ */
	public int updateTicket(Ticket t) throws SQLException, ClassNotFoundException{
		int updated = 0;
		Connection con = null;    
        PreparedStatement ps = null;
        try{
        	con = this.getConnection();
        	ps = con.prepareStatement("update tickets set ExpiryTime = ?,ExpiryDate = ?,Cost = ? where TicketID = ?");
        	java.util.Date date=new java.util.Date();
			java.sql.Date sqlDate = new java.sql.Date(date.getTime());
			java.sql.Time sqlTime = new java.sql.Time(date.getTime());
        	ps.setTime(1, sqlTime);
        	ps.setDate(2, sqlDate);
        	ps.setDouble(3, t.getServiceCost());
        	ps.setString(4, t.getTicketNo());
        	updated = ps.executeUpdate();
        	System.out.println(updated + " results updated in Ticket");
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        finally{  
        	if (ps!=null) 
        		ps.close();  
        	if(con!=null)  
        		con.close();  
        } 
		return updated;
	}
	
	
	/* -------------------------------------DeleteTicket----------------------------------- */
	public int deleteTicket(Ticket t) throws SQLException, ClassNotFoundException{
		int updated = 0;
		Connection con = null;    
        PreparedStatement ps = null;
        try{
        	con = this.getConnection();
			ps = con.prepareStatement("delete from tickets where TicketID = ?");
			ps.setString(1,t.getTicketNo());
			updated = ps.executeUpdate();
			System.out.println(updated + " results deleted");
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        finally{  
        	if (ps!=null) 
        		ps.close();  
        	if(con!=null)  
        		con.close();  
        } 
		return updated;
	}
}
