import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;


public class SlotFileWriter {
	private static final String FILE_HEADER = "SlotNumber,Floor,SlotType,Available";
	private static final String COMMA = ",";
    private static final String NEWLINE = "\n";

	public SlotFileWriter() {
		// TODO Auto-generated constructor stub
	}
	
	//writing current state back to the file
	public static boolean writeSlotFile(String file, ParkingLot pl){
		FileWriter fileWriter = null;
		//FileReader in = null;
		//String line = null;
		//BufferedReader br = null;
		//File f = new File("Resources//testFile1.csv");
		
		try{
			/*br = new BufferedReader(new FileReader(file));
			while((line = br.readLine())!=null){
				String[] tok = line.split(",");
				if(tok.length>0){
					if(tok[0].equals(s.getSlotNo())){
						if(tok[3].equals(anObject))
					}
				}
			}*/
			PrintWriter writer = new PrintWriter(file);
			writer.print("");
			writer.flush();
			writer.close();
			fileWriter = new FileWriter(file);
			fileWriter.append(FILE_HEADER);
			fileWriter.append(NEWLINE);
			for(Slot s:pl.getSlotAvailableList()){
	            fileWriter.append(String.valueOf(s.getSlotNo()));
	            fileWriter.append(COMMA);
	            fileWriter.append(String.valueOf(s.getFloor()));
	            fileWriter.append(COMMA);
	            fileWriter.append(String.valueOf(s.getSlotType()));
	            fileWriter.append(COMMA);
	            fileWriter.append(String.valueOf(s.isAvailable()));
	            fileWriter.append(COMMA);
	            fileWriter.append(String.valueOf( new Timestamp(System.currentTimeMillis())));
				fileWriter.append(NEWLINE);
			}
			for(Slot s:pl.getSlotFilledList()){
	            fileWriter.append(String.valueOf(s.getSlotNo()));
	            fileWriter.append(COMMA);
	            fileWriter.append(String.valueOf(s.getFloor()));
	            fileWriter.append(COMMA);
	            fileWriter.append(String.valueOf(s.getSlotType()));
	            fileWriter.append(COMMA);
	            fileWriter.append(String.valueOf(s.isAvailable()));
	            fileWriter.append(COMMA);
	            fileWriter.append(String.valueOf( new Timestamp(System.currentTimeMillis())));
				fileWriter.append(NEWLINE);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
	        try{
                fileWriter.flush();
                fileWriter.close();
            } catch(IOException e){
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }
		}
		return true;
	}
}
