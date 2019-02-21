import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;


public class TicketHistoryFileWriter {

	public TicketHistoryFileWriter() {
		// TODO Auto-generated constructor stub
	}
	private static final String COMMA = ",";
    private static final String NEWLINE = "\n";
    

	public static boolean writeFile(Ticket s, String file){
		FileWriter fileWriter = null;
		
		try{
			fileWriter = new FileWriter(file,true);
			fileWriter.append(NEWLINE);
			fileWriter.append(String.valueOf(s.getTicketNo()));
            fileWriter.append(COMMA);
            fileWriter.append(String.valueOf(s.getEntryDate()));
            fileWriter.append(COMMA);
            fileWriter.append(String.valueOf(s.getEntryTime()));
            fileWriter.append(COMMA);
            fileWriter.append(String.valueOf(s.getExpiryDate()));
            fileWriter.append(COMMA);
            fileWriter.append(String.valueOf(s.getExpiryTime()));
            fileWriter.append(COMMA);
            fileWriter.append(String.valueOf(s.getServiceCost()));
            fileWriter.append(COMMA);
            fileWriter.append(String.valueOf( new Timestamp(System.currentTimeMillis())));
			
		}
		catch(Exception e){
            System.out.println("Error in TicketHistoryFileWriter !!!");
            e.printStackTrace();
        }
		finally{     
            try{
                fileWriter.flush();
                fileWriter.close();
            } 
            catch(IOException e){
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }
             
        }
		
		return true;
	}
}
