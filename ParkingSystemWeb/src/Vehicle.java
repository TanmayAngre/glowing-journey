import java.util.Properties;

abstract class Vehicle {

	private String model;
	private String plateNo;
	private String type;  
	
	public Vehicle(String model, String plateNo, String type) {
		super();
		this.model = model;
		this.plateNo = plateNo;
		this.type = type;
		System.out.println("vehicle created");
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getPlateNo() {
		return plateNo;
	}

	public void setPlsteNo(String plateNo) {
		this.plateNo = plateNo;
	}

	public abstract void display(Properties p);
	
}
