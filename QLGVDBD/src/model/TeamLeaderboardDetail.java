package model;

public class TeamLeaderboardDetail {

	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	public int getTotalWin() {
		return totalWin;
	}
	public void setTotalWin(int totalWin) {
		this.totalWin = totalWin;
	}
	public int getTotalDefeat() {
		return totalDefeat;
	}
	public void setTotalDefeat(int totalDefeat) {
		this.totalDefeat = totalDefeat;
	}
	public int getTotalTire() {
		return totalTire;
	}
	public void setTotalTire(int totalTire) {
		this.totalTire = totalTire;
	}
	public int getDifference() {
		return difference;
	}
	public void setDifference(int difference) {
		this.difference = difference;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public int getRankScore() {
		return rankScore;
	}
	public void setRankScore(int rankScore) {
		this.rankScore = rankScore;
	}
	public int getTotalGoal() {
		return totalGoal;
	}
	public void setTotalGoal(int totalGoal) {
		this.totalGoal = totalGoal;
	}
	private Team team;
	private int totalWin, totalDefeat, totalTire, 
		difference, rank, rankScore, totalGoal;
	

}
