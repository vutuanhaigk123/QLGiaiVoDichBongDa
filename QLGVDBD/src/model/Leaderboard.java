package model;

import java.sql.Date;

public abstract class Leaderboard {
	protected int id;
	protected Date time;
	
	public abstract void createReport();
}
