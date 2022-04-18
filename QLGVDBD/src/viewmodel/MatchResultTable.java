package viewmodel;


public class MatchResultTable extends TableModel{

	public MatchResultTable(){
		super();
		dtm.addColumn("STT");
		dtm.addColumn("Cầu thủ");
		dtm.addColumn("Đội");
		dtm.addColumn("Loại bàn thắng");
		dtm.addColumn("Thời điểm");
		dtm.addColumn("");
		
//		for (int i = 0; i < 10; i++) {
//			Vector<Object> v = new Vector<>();
//			v.add(i);
//			v.add(i);
//			v.add(i);
//			v.add(i);
//			v.add(i);
//			v.add(new ImageIcon("/imgs/blank.png"));
//			dtm.addRow(v);
//		}
		super.addEmptyRow(10);
		super.bindingDeleteBtn("Are you sure to delete this match result?");
		
		//tblPkg.setAutoCreateRowSorter(true);
//		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(dtm);
//		tblPkg.setRowSorter(sorter);
//		tblPkg.getRowSorter().toggleSortOrder(0);
		// Prevent manager edit this table
//		tblPkg.setDefaultEditor(Object.class, null);		
		
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
