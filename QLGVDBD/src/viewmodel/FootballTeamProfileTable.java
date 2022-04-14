package viewmodel;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import database.DBConnector;
import database.DBPlayer;

public class FootballTeamProfileTable extends TableModel{

	private DefaultCellEditor dce;
	private TableCellEditor tce;
	private TableColumn typeColumn, dateColumn;
	private HashMap<String, Integer> idList;
	
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
		idList = new HashMap<String, Integer>();
		try {
			DBPlayer.getAllTypeOfPlayer(DBConnector.getInstance(), comboBox, idList);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	}
	
	synchronized public void setEnable(boolean b){
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

	
	
	public HashMap<String, Integer> getIdList(){
		return idList;
	}

}
