package viewmodel;

import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;

import view.FootballPlayerPanel;
import view.StartProgram;
import model.Team;
import database.DBConnector;
import database.DBPlayer;
import database.DBTeam;


public class FootballTeamListTable extends TableModel{
	
	protected Vector<Team> teamList, deletedTeam;

	public FootballTeamListTable(){
		super();
		dtm.addColumn("STT");
		dtm.addColumn("Tên đội");
		dtm.addColumn("Sân nhà");
		dtm.addColumn("");
		
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Vector<Team> teamList = DBTeam.getAllTeam(DBConnector.getInstance());
					showTeams(teamList);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		t.start();
		deletedTeam = new Vector<Team>();	
		
		super.bindingDeleteBtn("Are you sure to delete this team?");
		isEnable = true;
		
		//tblPkg.setAutoCreateRowSorter(true);
//		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(dtm);
//		tblPkg.setRowSorter(sorter);
//		tblPkg.getRowSorter().toggleSortOrder(0);
		// Prevent manager edit this table
		table.setDefaultEditor(Object.class, null);
	}
	
	public void showTeams(Vector<Team> teamList){
		this.teamList = teamList;
		for (int i = 0; i < teamList.size(); i++) {
			Vector<Object> t = new Vector<>();
			t.add(table.getRowCount() + 1);
			t.add(teamList.get(i).getName());
			t.add(teamList.get(i).getHome_stadium());
			t.add("");
 			dtm.addRow(t);
		}
	}
	
	public void saveData(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				checkDeleteTeam();
				((FootballPlayerPanel)(UpdateTabData.panelList
						.get(StartProgram.FOOTBALL_PLAYER_TAB))).updateData();
			}
		}).start();
	}
	
	public void checkDeleteTeam(){
		try {
			for (int i = 0; i < deletedTeam.size(); i++) {
				if(DBTeam.canDelete(DBConnector.getInstance(), 
						deletedTeam.get(i))){
					DBTeam.deleteTeam(DBConnector.getInstance(), 
							deletedTeam.get(i));
				}

			}
			deletedTeam.clear();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	synchronized public void setEnable(boolean b){
		if(b){
			isEnable = true;
		}
		else {
			
			isEnable = false;
			table.getSelectionModel().clearSelection();
		}
	}
	
	@Override
	public Object getSelectedItem() {
		int index = table.getSelectedRow();
		if(index >= 0 && index < table.getRowCount()){
//			if(teamList.get(index) != null){
////				System.out.println("khac null");
//			}
			return teamList.get(index);
		}
		return null;
	}

	@Override
	public void addEmptyObject() {
		teamList.add(null);
	}

	@Override
	public void deleteObject(int modelRow) {
		if(deletedTeam != null){
			deletedTeam.add(teamList.get(modelRow));
		}
		teamList.remove(modelRow);
	}

	@Override
	public boolean canDelete(int index) {
		try {
//			System.out.println(teamList.get(index).getName());
			return DBTeam.canDelete(DBConnector.getInstance(), teamList.get(index));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void showErrDelete() {
		JOptionPane.showMessageDialog(null,
				"Không được xóa đội bóng đã có dữ liệu thi đấu");
	}

	public Vector<Team> getTeamList(){
		return teamList;
	}
	
}
