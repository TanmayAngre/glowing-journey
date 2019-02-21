import java.util.Properties;


public class FourMini extends Vehicle {

	private int wheels = 4;

	public FourMini(String model, String pno, String type) {
		super(model, pno, type);
		
		System.out.println("Mini Four created");
	}

	public int getWheels() {
		return wheels;
	}

	

	public void display(Properties p){
		//Properties p = new Properties()
		System.out.println("\n----------------------Displaying vehicle information---------------------");
		System.out.println("Model : " + getModel());
		System.out.println("Registration Plate Number: " + getPlateNo());
		System.out.println("Type : " + getType());
		System.out.println("Wheeler : " + getWheels());
		System.out.println("Slot type required : " + p.getProperty(getType()));
	}
	


}
