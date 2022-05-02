package model;

import java.sql.Date;

public abstract class Leaderboard {
	public int getId_Leaderboard() {
		return id_leaderboard;
	}

	public void setId_Leaderboard(int id) {
		this.id_leaderboard = id;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	protected int id_leaderboard;
	protected Date time;
	
	public abstract void createReport();
	
	public abstract void sort(Date time);
}
