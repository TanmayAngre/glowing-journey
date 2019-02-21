import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;


public class SlotHistoryFileWriter {

	public SlotHistoryFileWriter() {
		// TODO Auto-generated constructor stub
	}
	private static final String COMMA = ",";
    private static final String NEWLINE = "\n";
    private static final String file = "E:\\didactic-fortnight\\ParkingSystem\\src\\slothistory.csv";
	public static boolean writeFile(Ticket t, Slot s, Vehicle v){
		FileWriter fileWriter = null;
		
		try{
			fileWriter = new FileWriter(file, true);
			fileWriter.append(NEWLINE);
			fileWriter.append(String.valueOf(t.getTicketNo()));
            fileWriter.append(COMMA);
            fileWriter.append(String.valueOf(s.getSlotNo()));
            fileWriter.append(COMMA);
            fileWriter.append(String.valueOf(s.getSlotType()));
            fileWriter.append(COMMA);
            fileWriter.append(String.valueOf(s.getFloor()));
            fileWriter.append(COMMA);
            fileWriter.append(String.valueOf(v.getModel()));
            fileWriter.append(COMMA);
            fileWriter.append(String.valueOf(v.getPlateNo()));
            fileWriter.append(COMMA);
            fileWriter.append(String.valueOf( new Timestamp(System.currentTimeMillis())));
			System.out.println("mmmmmmmmmmmmmmmm");
		}
		catch(Exception e){
            System.out.println("Error in CsvFileWriter !!!");
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
