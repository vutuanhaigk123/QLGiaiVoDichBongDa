package model;
import java.util.Vector;

public class Team {
	
	private String name, home_stadium;
	private Vector<Player> playerList;
	
	public Team(String name, String home_stadium,
			Vector<Player> playerList) {
		super();
		this.name = name;
		this.home_stadium = home_stadium;
		this.playerList = playerList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHome_stadium() {
		return home_stadium;
	}

	public void setHome_stadium(String home_stadium) {
		this.home_stadium = home_stadium;
	}

	public Vector<Player> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(Vector<Player> playerList) {
		this.playerList = playerList;
	}

	
	
	
	
}
