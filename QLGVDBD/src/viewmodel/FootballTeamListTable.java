package viewmodel;

import java.sql.SQLException;
import java.util.Vector;

import model.Team;
import database.DBConnector;
import database.DBTeam;


public class FootballTeamListTable extends TableModel{
	
	protected Vector<Team> teamList;

	public FootballTeamListTable(){
		super();
		dtm.addColumn("STT");
		dtm.addColumn("Tên đội");
		dtm.addColumn("Sân nhà");
		dtm.addColumn("");
		
//		for (int i = 0; i < 10; i++) {
//			Vector<Object> v = new Vector<>();
//			v.add(i);
//			v.add(i);
//			v.add(i);
//			v.add(new ImageIcon("/imgs/blank.png"));
//			dtm.addRow(v);
//		}
//		super.addEmptyRow(10);
		
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
			
		
		super.bindingDeleteBtn("Are you sure to delete this team?");
		
		//tblPkg.setAutoCreateRowSorter(true);
//		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(dtm);
//		tblPkg.setRowSorter(sorter);
//		tblPkg.getRowSorter().toggleSortOrder(0);
		// Prevent manager edit this table
//		tblPkg.setDefaultEditor(Object.class, null);
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

}
