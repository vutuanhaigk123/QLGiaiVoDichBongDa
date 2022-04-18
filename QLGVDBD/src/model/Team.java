package model;
import java.util.Vector;

public class Team {

	private int id;
	private String name, home_stadium;
	private Vector<Player> playerList;
	
	public Team(int id, String name, String home_stadium,
			Vector<Player> playerList) {
		super();
		this.id = id;
		this.name = name;
		this.home_stadium = home_stadium;
		this.playerList = playerList;
	}
	
	public Team clone(){
		Vector<Player> pList = new Vector<Player>();
		pList = (Vector<Player>) this.getPlayerList().clone();
		return new Team(this.getId(), 
				this.getName(),this.getHome_stadium(), pList);
	}
	
	public boolean isEqual(Team t){
		if(t.getId() != id || t.getHome_stadium() != home_stadium 
				|| t.getName() != name 
				|| t.getPlayerList().size() != playerList.size()){
			return false;
		}
		for (int i = 0; i < t.getPlayerList().size(); i++) {
			if(!t.getPlayerList().get(i).isEqual(playerList.get(i))){
				return false;
			}
		}
		return true;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
