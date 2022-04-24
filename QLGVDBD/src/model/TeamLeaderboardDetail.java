package model;

public class TeamLeaderboardDetail {
	private Team team;
	private int idLeaderboard, totalWin, totalDefeat, totalTire,
			difference, rank, rankScore, totalGoal;

	public TeamLeaderboardDetail(Team team, int idLeaderboard, int totalWin, int totalDefeat, int totalTire,
			int difference, int rank, int rankScore, int totalGoal) {
		this.team = team;
		this.idLeaderboard = idLeaderboard;
		this.totalWin = totalWin;
		this.totalDefeat = totalDefeat;
		this.totalTire = totalTire;
		this.difference = difference;
		this.rank = rank;
		this.rankScore = rankScore;
		this.totalGoal = totalGoal;
	}

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

	public int getIdLeaderboard() {
		return idLeaderboard;
	}

	public void setIdLeaderboard(int idLeaderboard) {
		this.idLeaderboard = idLeaderboard;
	}

}
