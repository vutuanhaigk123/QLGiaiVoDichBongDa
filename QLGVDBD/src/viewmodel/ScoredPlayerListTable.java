package viewmodel;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import database.DBConnector;
import database.DBPlayer;
import database.DBPlayerLeaderboard;
import model.Player;

public class ScoredPlayerListTable extends TableModel {

	protected Vector<Player> playerList;

	public ScoredPlayerListTable() {
		super();
		dtm.addColumn("STT");
		dtm.addColumn("Cầu thủ");
		dtm.addColumn("Đội");
		dtm.addColumn("Loại cầu thủ");
		dtm.addColumn("Số bàn thắng");

		table.setDefaultEditor(Object.class, null);

		getData();
		// for (int i = 0; i < 10; i++) {
		// Vector<Object> v = new Vector<>();
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
	}

	public void showPlayers(Vector<Player> playerList) {
		try {
			HashMap<Integer, String> typePlayer = DBPlayer.getStringTypeOfPlayer(DBConnector.getInstance());
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

	public void getData() {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					dtm.setRowCount(0);
					Vector<Player> playerList = DBPlayerLeaderboard.getAllPlayer(DBConnector.getInstance());
					showPlayers(playerList);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		t.start();
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
