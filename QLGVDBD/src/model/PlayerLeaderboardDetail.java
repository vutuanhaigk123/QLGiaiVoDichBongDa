package model;

public class PlayerLeaderboardDetail {
	private Player player;
	private int totalGoal;
	
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public int getTotalGoal() {
		return totalGoal;
	}
	public void setTotalGoal(int totalGoal) {
		this.totalGoal = totalGoal;
	}
	
	public PlayerLeaderboardDetail(Player player, int totalGoal) {
		super();
		this.player = player;
		this.totalGoal = totalGoal;
	}
}
