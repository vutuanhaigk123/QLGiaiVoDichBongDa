package model;

import java.util.Vector;

public class Result {
	
	private int id, firstTeamScore, secondTeamScore;
	private Vector<ResultDetail> scoredList;
	public Result(int id, int firstTeamScore, int secondTeamScore,
			Vector<ResultDetail> scoredList) {
		super();
		this.id = id;
		this.firstTeamScore = firstTeamScore;
		this.secondTeamScore = secondTeamScore;
		this.scoredList = scoredList;
	}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFirstTeamScore() {
		return firstTeamScore;
	}
	public void setFirstTeamScore(int firstTeamScore) {
		this.firstTeamScore = firstTeamScore;
	}
	public int getSecondTeamScore() {
		return secondTeamScore;
	}
	public void setSecondTeamScore(int secondTeamScore) {
		this.secondTeamScore = secondTeamScore;
	}
	public Vector<ResultDetail> getScoredList() {
		return scoredList;
	}
	public void setScoredList(Vector<ResultDetail> scoredList) {
		this.scoredList = scoredList;
	}
	
}
