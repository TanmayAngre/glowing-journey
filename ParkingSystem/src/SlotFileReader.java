import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SlotFileReader {
	private static final String COMMA = ",";
	public SlotFileReader(){
		// TODO Auto-generated constructor stub
	}
	public static List[] readFile(String file){
		BufferedReader bufferedReader = null;
		String s = null;
		List a[] = new ArrayList[2];
		try{
        	ArrayList<Slot> available = new ArrayList<>();
    		ArrayList<Slot> filled = new ArrayList<>();
            bufferedReader = new BufferedReader(new FileReader(file));
            //for headers
            bufferedReader.readLine();
            while((s = bufferedReader.readLine()) != null){
                //Get all tokens available in line
                String[] slotA = s.split(COMMA);
                System.out.println("DEBUG: slotA length:" + slotA.length);
                Slot slot = new Slot(Integer.parseInt(slotA[1]),Integer.parseInt(slotA[2]),Integer.parseInt(slotA[0]));
                
                if(slotA[3].equals("TRUE")){
                	slot.setIsAvailable(true);
                	available.add(slot);
                }
                else if(slotA[3].equals("FALSE")){
                	slot.setIsAvailable(false);
                	filled.add(slot);
                }
            }
            a[0] = available;
            a[1] = filled;
            System.out.println(a[1]);
        } 
        catch(Exception e){
            System.out.println("In SlotFileReader Error");
            e.printStackTrace();
        } 
		finally{
            try{
                bufferedReader.close();
            } 
            catch(IOException e){
                System.out.println("Error while closing fileReader !!!");
                e.printStackTrace();
            }
        }
		return a;
    }
}

