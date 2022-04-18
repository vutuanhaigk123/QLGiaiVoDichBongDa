package model;

import java.sql.Date;

public class Match_Schedule {

	private int id;
	private int round;
	private Date time;
	private Team firstTeam, secondTeam;
	private String stadium;
	private Result matchResult;
	
	
	public Match_Schedule(int id, int round, Date time, Team firstTeam,
			Team secondTeam, String stadium, Result matchResult) {
		super();
		this.id = id;
		this.round = round;
		this.time = time;
		this.firstTeam = firstTeam;
		this.secondTeam = secondTeam;
		this.stadium = stadium;
		this.matchResult = matchResult;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRound() {
		return round;
	}
	public void setRound(int round) {
		this.round = round;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Team getFirstTeam() {
		return firstTeam;
	}
	public void setFirstTeam(Team firstTeam) {
		this.firstTeam = firstTeam;
	}
	public Team getSecondTeam() {
		return secondTeam;
	}
	public void setSecondTeam(Team secondTeam) {
		this.secondTeam = secondTeam;
	}
	public String getStadium() {
		return stadium;
	}
	public void setStadium(String stadium) {
		this.stadium = stadium;
	}
	public Result getMatchResult() {
		return matchResult;
	}
	public void setMatchResult(Result matchResult) {
		this.matchResult = matchResult;
	}
}
