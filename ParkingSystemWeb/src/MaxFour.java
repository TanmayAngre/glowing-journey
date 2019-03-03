import java.util.Properties;


public class MaxFour extends Vehicle {

	private int wheels = 4;

	public MaxFour(String model, String pno, String type) {
		super(model, pno, type);
		System.out.println("Max 4 wheeler created");
	}

	public int getWheels() {
		return wheels;
	}

	

	public void display(Properties p){
		//Properties p = new Properties()
		System.out.println("\n----------------------Displaying MaxSize Four Wheeler information---------------------");
		System.out.println("Model : " + getModel());
		System.out.println("Registration Plate Number: " + getPlateNo());
		System.out.println("Type : " + getType());
		System.out.println("Wheeler : " + getWheels());
		System.out.println("Slot type required : " + p.getProperty(getType()));
	}
	


}
