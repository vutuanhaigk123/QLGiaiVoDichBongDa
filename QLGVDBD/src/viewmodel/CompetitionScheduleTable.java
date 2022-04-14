package viewmodel;

import java.time.LocalDateTime;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.table.TableColumn;

public class CompetitionScheduleTable extends TableModel{
	
	private boolean isEnable;
	private DateTimeTableEditor dateTimeEdit;
	private TableColumn dateColumn, fTeamColumn, sTeamColumn;
	private JComboBox comboBox;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CompetitionScheduleTable(){
		super();
		dtm.addColumn("STT");
		dtm.addColumn("Đội 1");
		dtm.addColumn("Đội 2");
		dtm.addColumn("Sân");
		dtm.addColumn("Ngày - giờ");
		dtm.addColumn("");
		
		super.addEmptyRow(10);
//		JPanel panel = new JPanel();
//		panel.add(new DatePicker());
//		for(int j = 0; j < 10; j++){
//			Vector<Object> row = new Vector<>();
//			int n = table.getColumnCount() - 1;
//			row.add((table.getRowCount() + 1) + "");
//			for(int i = 1; i < n; i++){
//				if(i == n){
//					row.add(new ImageIcon("/imgs/blank.png"));
//				}
//				else if(i == 3){
//					row.add(panel);
//				}
//				else{
//					row.add("");
//				}
//			}
//			dtm.addRow(row);
//		}
		
		
//		TableColumn dateColumn = table.getColumnModel().getColumn(3);
//        dateColumn.setCellEditor(new DatePickerCellEditor());
		
		
		table.setDefaultRenderer(LocalDateTime.class, new DateTimeTableEditor());
	    dateTimeEdit = new DateTimeTableEditor();
	    table.setDefaultEditor(LocalDateTime.class, dateTimeEdit);
	    
	    dateColumn = table.getColumnModel().getColumn(4);
		dateColumn.setCellRenderer(table.getDefaultRenderer(LocalDateTime.class));
		dateColumn.setCellEditor(table.getDefaultEditor(LocalDateTime.class));
		
		
		fTeamColumn = table.getColumnModel().getColumn(1);
		sTeamColumn = table.getColumnModel().getColumn(2);
		comboBox = new JComboBox();
		comboBox.addItem("Team số 1");
		comboBox.addItem("Team số 2");
		comboBox.addItem("Team số 3");
		comboBox.addItem("Team số 4");
		comboBox.addItem("Team số 5");
		comboBox.addItem("Team số 6");
		fTeamColumn.setCellEditor(new DefaultCellEditor(comboBox));
		sTeamColumn.setCellEditor(new DefaultCellEditor(comboBox));
		
		
		super.bindingDeleteBtn("Are you sure to delete this competition match?");
		
//		setEnable(false);
		isEnable = true;
		
		
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

	public void setEnable(boolean b){
		if(b){
			table.setDefaultEditor(LocalDateTime.class, dateTimeEdit);
			dateColumn.setCellEditor(table.getDefaultEditor(LocalDateTime.class));
			dateColumn.setCellRenderer(table.getDefaultRenderer(LocalDateTime.class));
			fTeamColumn.setCellEditor(new DefaultCellEditor(comboBox));
			sTeamColumn.setCellEditor(new DefaultCellEditor(comboBox));
			isEnable = true;
		}
		else{
			table.setDefaultEditor(Object.class, null );
			dateColumn.setCellEditor(null);
			dateColumn.setCellRenderer(null);
			fTeamColumn.setCellEditor(null);
			sTeamColumn.setCellEditor(null);
			isEnable = false;
		}
		table.repaint();
	}

	public boolean isEnable() {
		return isEnable;
	}

}
