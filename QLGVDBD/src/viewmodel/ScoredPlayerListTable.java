package viewmodel;

import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import database.DBConnector;
import database.DBPlayer;
import database.DBTeamLeaderboardDetail;
import model.PlayerLeaderboardDetail;

public class ScoredPlayerListTable extends TableModel{

	public ScoredPlayerListTable(){
		super();
		dtm.addColumn("STT");
		dtm.addColumn("Cầu thủ");
		dtm.addColumn("Đội");
		dtm.addColumn("Loại cầu thủ");
		dtm.addColumn("Số bàn thắng");
		
		// Prevent manager edit this table
//		tblPkg.setDefaultEditor(Object.class, null);
	}
	
	synchronized public void showData(Date time){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Vector<PlayerLeaderboardDetail> players = 
							DBTeamLeaderboardDetail.getAllPlayers(DBConnector.getInstance(), time);
					HashMap<Integer, String> idTypeList = 
							DBPlayer.getStringTypeOfPlayer(DBConnector.getInstance());
					dtm.setRowCount(0);
					for (int i = 0; i < players.size(); i++) {
						Vector<Object> row = new Vector<>();
						row.add(i + 1);
						row.add(players.get(i).getPlayer().getName());
						row.add(players.get(i).getPlayer().getTeamName());
						row.add(idTypeList.get(players.get(i).getPlayer().getTypeOfPlayer()));
						row.add(players.get(i).getTotalGoal());
						dtm.addRow(row);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		
	}

	@Override
	public Object getSelectedItem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addEmptyObject() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteObject(int modelRow) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canDelete(int index) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void showErrDelete() {
		// TODO Auto-generated method stub
		
	}
}
