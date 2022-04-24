package viewmodel;

import java.sql.SQLException;
import java.util.Vector;

import database.DBConnector;
import database.DBTeamLeaderboardDetail;
import model.TeamLeaderboard;

public class LeaderboardTable extends TableModel {

	public LeaderboardTable() {
		super();
		dtm.addColumn("STT");
		dtm.addColumn("Đội");
		dtm.addColumn("Thắng");
		dtm.addColumn("Hoà");
		dtm.addColumn("Thua");
		dtm.addColumn("Hiệu số");
		dtm.addColumn("Hạng");

		table.setDefaultEditor(Object.class, null);

		// for (int i = 0; i < 10; i++) {
		// Vector<Object> v = new Vector<>();
		// v.add(i);
		// v.add(i);
		// v.add(i);
		// v.add(i);
		// v.add(i);
		// v.add(i);
		// v.add(i);
		// dtm.addRow(v);
		// }

		// super.addEmptyRow(10);
		// tblPkg.setAutoCreateRowSorter(true);
		// TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(dtm);
		// tblPkg.setRowSorter(sorter);
		// tblPkg.getRowSorter().toggleSortOrder(0);
		// Prevent manager edit this table
		// tblPkg.setDefaultEditor(Object.class, null);
		// tblPkg.addMouseListener(new MouseListener() {
		//
		// @Override
		// public void mouseReleased(MouseEvent e) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void mousePressed(MouseEvent e) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void mouseExited(MouseEvent e) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void mouseEntered(MouseEvent e) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void mouseClicked(MouseEvent e) {
		// if (e.getClickCount() == 2) { // to detect doble click events
		//// JTable target = (JTable)e.getSource();
		//// int row = target.getSelectedRow(); // select a row
		//// int column = target.getSelectedColumn(); // select a column
		//// JOptionPane.showMessageDialog(null,
		//// tblPkg.getValueAt(row, column) + " hiện dialog nữa"); // get the value of a
		// row and column.
		// FootballTeamInfoDialog f = new FootballTeamInfoDialog();
		// f.setModal(true);
		// f.setVisible(true);
		// }
		// }
		// });

		try {
			DBTeamLeaderboardDetail.getAllTeam(DBConnector.getInstance());
			showLeaderboard();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showLeaderboard() {
		for (int i = 0; i < TeamLeaderboard.teamLeaderboardList.size(); i++) {
			Vector<Object> p = new Vector<>();
			p.add(table.getRowCount() + 1);
			p.add(TeamLeaderboard.teamLeaderboardList.get(i).getTeam().getName());
			p.add(TeamLeaderboard.teamLeaderboardList.get(i).getTotalWin());
			p.add(TeamLeaderboard.teamLeaderboardList.get(i).getTotalTire());
			p.add(TeamLeaderboard.teamLeaderboardList.get(i).getTotalDefeat());
			p.add(TeamLeaderboard.teamLeaderboardList.get(i).getDifference());
			p.add(TeamLeaderboard.teamLeaderboardList.get(i).getRank());
			dtm.addRow(p);
		}
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
