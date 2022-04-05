package model;

import java.time.LocalDate;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

public class FootballTeamProfileTable extends TableModel{

	private DefaultCellEditor dce;
	private TableCellEditor tce;
	private TableColumn typeColumn, dateColumn;
	private boolean isEnable;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public FootballTeamProfileTable(){
		super();
		dtm.addColumn("STT");
		dtm.addColumn("Cầu thủ");
		dtm.addColumn("Ngày sinh");
		dtm.addColumn("Loại cầu thủ");
		dtm.addColumn("Ghi chú");
		dtm.addColumn("");
		super.addEmptyRow(10);
		
		table.setDefaultRenderer(LocalDate.class, new DateTableEditor());
	    DateTableEditor dateEdit = new DateTableEditor();
	    table.setDefaultEditor(LocalDate.class, dateEdit);
	    
	    dateColumn = table.getColumnModel().getColumn(2);
		dateColumn.setCellRenderer(table.getDefaultRenderer(LocalDate.class));
		dateColumn.setCellEditor(table.getDefaultEditor(LocalDate.class));

		JComboBox comboBox = new JComboBox();
		comboBox.addItem("Trong nước");
		comboBox.addItem("Nước ngoài");
		dce = new DefaultCellEditor(comboBox);
		
		typeColumn = table.getColumnModel().getColumn(3);
		typeColumn.setCellEditor(dce);
		
		super.bindingDeleteBtn("Are you sure to delete this player?");
		
		//tblPkg.setAutoCreateRowSorter(true);
//		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(dtm);
//		tblPkg.setRowSorter(sorter);
//		tblPkg.getRowSorter().toggleSortOrder(0);
		tce = table.getDefaultEditor(Object.class);
//		setEnable(false);
		isEnable = true;
		
		
		
		// Prevent manager edit this table
//		table.setDefaultEditor(Object.class, null);
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
			table.setDefaultEditor(Object.class, tce );
			typeColumn.setCellEditor(dce);
			dateColumn.setCellEditor(table.getDefaultEditor(LocalDate.class));
			dateColumn.setCellRenderer(table.getDefaultRenderer(LocalDate.class));
			isEnable = true;
		}
		else{
			table.setDefaultEditor(Object.class, null );
			typeColumn.setCellEditor(null);
			dateColumn.setCellEditor(null);
			dateColumn.setCellRenderer(null);
			isEnable = false;
		}
		table.repaint();
	}

	public boolean isEnable() {
		return isEnable;
	}

}
