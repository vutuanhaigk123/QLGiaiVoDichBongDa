package viewmodel;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import model.Player;
import database.DBConnector;
import database.DBPlayer;

public class FootballPlayerListTable extends TableModel{
	
	protected Vector<Player> playerList;
	
	public FootballPlayerListTable(){
		super();
		dtm.addColumn("STT");
		dtm.addColumn("Cầu thủ");
		dtm.addColumn("Đội");
		dtm.addColumn("Loại cầu thủ");
		dtm.addColumn("Tổng số bàn thắng");

//		super.addEmptyRow(10);
		
		//tblPkg.setAutoCreateRowSorter(true);
//		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(dtm);
//		tblPkg.setRowSorter(sorter);
//		tblPkg.getRowSorter().toggleSortOrder(0);
		// Prevent manager edit this table
		table.setDefaultEditor(Object.class, null);
		
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Vector<Player> playerList = DBPlayer.getAllPlayer(DBConnector.getInstance());
					showPlayers(playerList);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		t.start();
	}
	
	public void showPlayers(Vector<Player> playerList){
		try {
			HashMap<Integer, String> typePlayer = 
					DBPlayer.getStringTypeOfPlayer(DBConnector.getInstance());
			this.playerList = playerList;
			for (int i = 0; i < playerList.size(); i++) {
				Vector<Object> p = new Vector<>();
				p.add(table.getRowCount() + 1);
				p.add(playerList.get(i).getName());
				p.add(playerList.get(i).getTeamName());
				p.add(typePlayer.get(playerList.get(i).getTypeOfPlayer()));
				p.add(playerList.get(i).getTotal_goal());
				dtm.addRow(p);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
