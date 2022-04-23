package model;

import java.sql.Date;

public class ResultDetail {

	private Player player;
	private String typeOfGoal;
	private int time, id;
	
	
	public ResultDetail(int id, Player player, String typeOfGoal, int time) {
		super();
		this.id = id;
		this.player = player;
		this.typeOfGoal = typeOfGoal;
		this.time = time;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public String getTypeOfGoal() {
		return typeOfGoal;
	}
	public void setTypeOfGoal(String typeOfGoal) {
		this.typeOfGoal = typeOfGoal;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}

	public boolean isEqual(ResultDetail resultDetail) {
		if(!player.isEqual(resultDetail.getPlayer()) ||
				typeOfGoal != resultDetail.getTypeOfGoal() ||
				time != resultDetail.getTime()){
			return false;
		}
		return true;
	}
	
}
