package Buss;

public class Bus{
	
	private int id;
	private int capacity;
	private String description;
	private String type;
	private String engineNumber;
	
	public Bus() {}
	
	public int getID() {
		return id;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public int getCapacity() {
		return capacity;
	}
	
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public String getType(){
		return type;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getEngineNumber(){
		return engineNumber;
	}

	public void setEngineNumber(String engineNumber){
		this.engineNumber = engineNumber;
	}

}
