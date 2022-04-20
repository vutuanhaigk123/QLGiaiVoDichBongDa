package model;

public class RegulationList {

	private int id;
	private String name, type, value;
	private boolean status;
	
	public RegulationList(int id, String name, String type, String value, boolean status) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.value = value;
		this.status = status;
	}
	
	public int getID() {
		return id;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
}
