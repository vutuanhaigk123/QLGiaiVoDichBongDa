package viewmodel;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.table.TableColumn;

import model.Team;
import database.DBConnector;
import database.DBTeam;

public class CompetitionScheduleListTable extends TableModel{
	
	private boolean isEnable;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CompetitionScheduleListTable(){
		super();
		dtm.addColumn("STT");
		dtm.addColumn("Vòng");
		dtm.addColumn("");
		table.setDefaultEditor(Object.class, null );
		
		super.bindingDeleteBtn("Are you sure to delete this round match?");
		
//		setEnable(false);
		isEnable = true;
		
		
		//tblPkg.setAutoCreateRowSorter(true);
//		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(dtm);
//		tblPkg.setRowSorter(sorter);
//		tblPkg.getRowSorter().toggleSortOrder(0);
		// Prevent manager edit this table
//		tblPkg.setDefaultEditor(Object.class, null);
	}
	
	
	
	public void updateData(){
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				
			}
		});
		t.start();
		
		
	}


	public boolean isEnable() {
		return isEnable;
	}

	@Override
	public Object getSelectedItem() {
		int index = table.getSelectedRow();
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
		return true;
	}

	@Override
	public void showErrDelete() {
		// TODO Auto-generated method stub
		
	}

}
