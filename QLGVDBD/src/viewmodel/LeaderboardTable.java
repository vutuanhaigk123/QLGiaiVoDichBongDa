package viewmodel;

import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import model.Team;
import model.TeamLeaderboard;
import model.TeamLeaderboardDetail;
import database.DBConnector;
import database.DBTeam;
import database.DBTeamLeaderboardDetail;

public class LeaderboardTable extends TableModel{

	private TeamLeaderboard teamLeaderboard;
	
	public LeaderboardTable(){
		super();
		dtm.addColumn("STT");
		dtm.addColumn("Đội");
		dtm.addColumn("Thắng");
		dtm.addColumn("Hoà");
		dtm.addColumn("Thua");
		dtm.addColumn("Hiệu số");
		dtm.addColumn("Hạng");
		
		teamLeaderboard = new TeamLeaderboard();

		//tblPkg.setAutoCreateRowSorter(true);
//		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(dtm);
//		tblPkg.setRowSorter(sorter);
//		tblPkg.getRowSorter().toggleSortOrder(0);
		// Prevent manager edit this table
//		tblPkg.setDefaultEditor(Object.class, null);
		
	}
	
	public void showData(Date time){
		try {
			if(DBTeamLeaderboardDetail.findIdLeaderboardByTime(
					DBConnector.getInstance(), time) != -1){

				System.out.println("Da ton tai");
			}
			else {
				System.out.println("Chua ton tai");
				HashMap<Integer, Team> idTeamList = new HashMap<Integer, Team>();
				Vector<TeamLeaderboardDetail> teamList = getAllTeamList(idTeamList, time);
				
				teamLeaderboard.setTeamList(teamList);
				teamLeaderboard.sort(time);
				dtm.setRowCount(0);
				for (int i = 0; i < teamList.size(); i++) {
					Vector<Object> row = new Vector<Object>();
					row.add(i + 1);
					row.add(idTeamList.get(teamList.get(i).getIdTeam()).getName());
					row.add(teamList.get(i).getTotalWin());
					row.add(teamList.get(i).getTotalTire());
					row.add(teamList.get(i).getTotalDefeat());
					row.add(teamList.get(i).getDifference());
					row.add(teamList.get(i).getRank());
					dtm.addRow(row);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Vector<TeamLeaderboardDetail> getAllTeamList(
			HashMap<Integer, Team> idTeamList, 
			Date time){

		Vector<TeamLeaderboardDetail> teamList = new Vector<TeamLeaderboardDetail>();
		try {
			Vector<Team> teams = DBTeam.getAllTeam(DBConnector.getInstance());
			for (int i = 0; i < teams.size(); i++) {
				idTeamList.put(teams.get(i).getId(), teams.get(i));
				int totalWin = DBTeamLeaderboardDetail.countTotal(
						DBConnector.getInstance(),
						DBTeamLeaderboardDetail.COUNT_TOTAL_WIN,
						teams.get(i).getId(), time);
				int totalDefeat = DBTeamLeaderboardDetail.countTotal(
						DBConnector.getInstance(),
						DBTeamLeaderboardDetail.COUNT_TOTAL_DEFEAT,
						teams.get(i).getId(), time);
				int totalTire = DBTeamLeaderboardDetail.countTotal(
						DBConnector.getInstance(),
						DBTeamLeaderboardDetail.COUNT_TOTAL_TIRE,
						teams.get(i).getId(), time);
				int difference = DBTeamLeaderboardDetail.countTotal(
						DBConnector.getInstance(),
						DBTeamLeaderboardDetail.COUNT_TOTAL_DIFFERENCE,
						teams.get(i).getId(), time);
				int totalGoal = DBTeamLeaderboardDetail.countTotalGoal(DBConnector.getInstance(), 
						teams.get(i).getId(), 
						time);
				TeamLeaderboardDetail temp = new TeamLeaderboardDetail(
						teams.get(i).getId(), 
						totalWin, 
						totalDefeat, 
						totalTire, 
						difference, 
						-1, 
						-1, 
						totalGoal);
				temp.updateInfo(); // update rankScore
				teamList.add(temp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return teamList;
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
