package model;

public class TeamLeaderboardDetail {

	public void updateInfo(){
		rankScore = totalWin * Regulation.getValueOf(RegulationList.WIN_SCORE_ID)
				+ totalDefeat * Regulation.getValueOf(RegulationList.DEFEAT_SCORE_ID)
				+ totalTire * Regulation.getValueOf(RegulationList.TIRED_SCORE_ID);
		
	}
	
	public int getIdTeam() {
		return id_team;
	}
	public void setIdTeam(int team) {
		this.id_team = team;
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
	private int id_team;
	private int totalWin, totalDefeat, totalTire, 
		difference, rank, rankScore, totalGoal;
	
	public TeamLeaderboardDetail(int id_team, int totalWin, int totalDefeat,
			int totalTire, int difference, int rank, int rankScore,
			int totalGoal) {
		super();
		this.id_team = id_team;
		this.totalWin = totalWin;
		this.totalDefeat = totalDefeat;
		this.totalTire = totalTire;
		this.difference = difference;
		this.rank = rank;
		this.rankScore = rankScore;
		this.totalGoal = totalGoal;
	}
	

}
