package model;

public class RegulationList {

	private int id;
	private String name, type, value;
	private boolean status;
	
	public static final int PLAYER_MIN_AGE_ID = 1;
	public static final int PLAYER_MAX_AGE_ID = 2;
	public static final int MIN_NUM_OF_PLAYER_ID = 3;
	public static final int MAX_NUM_OF_PLAYER_ID = 4;
	public static final int MAX_NUM_OF_ABROAD_PLAYER_ID = 5;
	public static final int MAX_TIME_SCORED_ID = 6;
	public static final int WIN_SCORE_ID = 7;
	public static final int TIRED_SCORE_ID = 8;
	public static final int DEFEAT_SCORE_ID = 9;
	public static final int NUM_OF_SCORED_TYPE_ID = 10;
			
	
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
