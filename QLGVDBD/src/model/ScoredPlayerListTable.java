package model;

public class ScoredPlayerListTable extends TableModel{

	public ScoredPlayerListTable(){
		super();
		dtm.addColumn("STT");
		dtm.addColumn("Cầu thủ");
		dtm.addColumn("Đội");
		dtm.addColumn("Loại cầu thủ");
		dtm.addColumn("Số bàn thắng");
		
//		for (int i = 0; i < 10; i++) {
//			Vector<Object> v = new Vector<>();
//			v.add(i);
//			v.add(i);
//			v.add(i);
//			v.add(i);
//			v.add(i);
//			dtm.addRow(v);
//		}
		super.addEmptyRow(10);
		
		//tblPkg.setAutoCreateRowSorter(true);
//		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(dtm);
//		tblPkg.setRowSorter(sorter);
//		tblPkg.getRowSorter().toggleSortOrder(0);
		// Prevent manager edit this table
//		tblPkg.setDefaultEditor(Object.class, null);
//		tblPkg.addMouseListener(new MouseListener() {
//
//			@Override
//			public void mouseReleased(MouseEvent e) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void mousePressed(MouseEvent e) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void mouseExited(MouseEvent e) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void mouseEntered(MouseEvent e) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				if (e.getClickCount() == 2) {     // to detect doble click events
////					JTable target = (JTable)e.getSource();
////					int row = target.getSelectedRow(); // select a row
////					int column = target.getSelectedColumn(); // select a column
////					JOptionPane.showMessageDialog(null, 
////							tblPkg.getValueAt(row, column) + " hiện dialog nữa"); // get the value of a row and column.
//					FootballTeamInfoDialog f = new FootballTeamInfoDialog();
//					f.setModal(true);
//					f.setVisible(true);
//				}
//			}
//		});
	}
}
