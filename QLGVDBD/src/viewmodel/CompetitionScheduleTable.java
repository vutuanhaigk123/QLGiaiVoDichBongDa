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

public class CompetitionScheduleTable extends TableModel{
	
	private boolean isEnable;
	private DateTimeTableEditor dateTimeEdit;
	private TableColumn dateColumn, fTeamColumn, sTeamColumn;
	private JComboBox comboBox, comboBox2;
	private Vector<Team> teamList;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CompetitionScheduleTable(){
		super();
		dtm.addColumn("STT");
		dtm.addColumn("Đội 1");
		dtm.addColumn("Đội 2");
		dtm.addColumn("Sân");
		dtm.addColumn("Ngày - giờ");
		dtm.addColumn("");
		comboBox = new JComboBox();
		comboBox2 = new JComboBox();
		comboBox.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				int index = comboBox.getSelectedIndex();
				if(index != -1 && teamList != null){
					
					table.setValueAt(teamList.get(index).getHome_stadium(),
							table.getSelectedRow(), 3);
				}
			}
		});

		
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
		
		updateData();
		fTeamColumn.setCellEditor(new DefaultCellEditor(comboBox));
		sTeamColumn.setCellEditor(new DefaultCellEditor(comboBox2));
		
		
		super.bindingDeleteBtn("Are you sure to delete this competition match?");
		
//		setEnable(false);
		isEnable = true;
		
		
		//tblPkg.setAutoCreateRowSorter(true);
//		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(dtm);
//		tblPkg.setRowSorter(sorter);
//		tblPkg.getRowSorter().toggleSortOrder(0);
		// Prevent manager edit this table
//		tblPkg.setDefaultEditor(Object.class, null);
	}
	
	public void updateCombobox(){
		try {
			teamList = DBTeam.updateTeamCombobox(DBConnector.getInstance(), comboBox, comboBox2);
			comboBox.setSelectedIndex(-1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateData(){
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				updateCombobox();
			}
		});
		t.start();
		
		
	}

	public void setEnable(boolean b){
		if(b){
			table.setDefaultEditor(LocalDateTime.class, dateTimeEdit);
			dateColumn.setCellEditor(table.getDefaultEditor(LocalDateTime.class));
			dateColumn.setCellRenderer(table.getDefaultRenderer(LocalDateTime.class));
			fTeamColumn.setCellEditor(new DefaultCellEditor(comboBox));
			sTeamColumn.setCellEditor(new DefaultCellEditor(comboBox2));
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
