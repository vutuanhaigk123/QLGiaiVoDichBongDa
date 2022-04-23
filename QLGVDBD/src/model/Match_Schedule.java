package model;

import java.time.LocalDateTime;

public class Match_Schedule {

	private int id;
	private int round;
	private LocalDateTime time;
	private Team firstTeam, secondTeam;
	private String stadium;
	private Result matchResult;
	
	
	public Match_Schedule(int id, int round, LocalDateTime time, Team firstTeam,
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
	
	public Match_Schedule(int round, LocalDateTime time, Team firstTeam,
			Team secondTeam, String stadium, Result matchResult) {
		super();
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
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
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

	public boolean isEqual(Match_Schedule match_Schedule) {
		if(!this.firstTeam.isEqual(match_Schedule.getFirstTeam()) || 
				!this.secondTeam.isEqual(match_Schedule.getSecondTeam()) ||
				this.id != match_Schedule.getId() ||
				this.round != match_Schedule.getRound() ||
				this.stadium != match_Schedule.getStadium() ||
				!this.time.toString().equals(match_Schedule.getTime().toString()) ||
				!this.matchResult.isEqual(match_Schedule.getMatchResult())){
			return false;
		}
		return true;
	}
}
