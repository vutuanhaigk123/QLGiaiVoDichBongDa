package model;

import java.sql.Date;

public class ResultDetail {

	private Player player;
	private String typeOfGoal;
	private Date time;
	
	
	public ResultDetail(Player player, String typeOfGoal, Date time) {
		super();
		this.player = player;
		this.typeOfGoal = typeOfGoal;
		this.time = time;
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
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
}
