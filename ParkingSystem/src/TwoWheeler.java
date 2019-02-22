import java.util.Properties;


public class TwoWheeler extends Vehicle {

	private int wheels = 2;

	public TwoWheeler(String model, String pno, String type) {
		super(model, pno, type);
	
		System.out.println("2 wheeler created");
	}

	public int getWheels() {
		return wheels;
	}

	

	public void display(Properties p){
		//Properties p = new Properties()
		System.out.println("\n----------------------Displaying Two-Wheeler information---------------------");
		System.out.println("Model : " + getModel());
		System.out.println("Registration Plate Number: " + getPlateNo());
		System.out.println("Type : " + getType());
		System.out.println("Wheeler : " + getWheels());
		System.out.println("Slot type required : " + p.getProperty(getType()));
	}
	

}
