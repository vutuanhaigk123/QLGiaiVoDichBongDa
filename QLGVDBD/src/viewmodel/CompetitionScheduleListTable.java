package viewmodel;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Vector;

import javax.swing.table.TableCellEditor;

import model.Round;
import database.DBConnector;
import database.DBMatchSchedule;


public class CompetitionScheduleListTable extends TableModel{
	
	private boolean isEnable;
	private Vector<Round> roundList, deletedRound;
	private TableCellEditor de;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CompetitionScheduleListTable(){
		super();
		dtm.addColumn("STT");
		dtm.addColumn("Vòng");
		dtm.addColumn("");
		
		getData();
		super.bindingDeleteBtn("Are you sure to delete this round match?");
		de = table.getDefaultEditor(LocalDateTime.class);
		setEnable(false);
//		isEnable = true;
		
		
		//tblPkg.setAutoCreateRowSorter(true);
//		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(dtm);
//		tblPkg.setRowSorter(sorter);
//		tblPkg.getRowSorter().toggleSortOrder(0);
		// Prevent manager edit this table
//		tblPkg.setDefaultEditor(Object.class, null);
	}
	


	public void getData(){
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					dtm.setRowCount(0);
					roundList = DBMatchSchedule.getAllRound(DBConnector.getInstance());

					for (int i = 0; i < roundList.size(); i++) {
						Vector<Object> v = new Vector<>();
						v.add(i + 1);
						v.add(roundList.get(i).getName());
						dtm.addRow(v);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		t.start();
	}
	

	public boolean isEnable() {
		return isEnable;
	}
	
	synchronized public void setEnable(boolean b){
		isEnable = b;
		if(b){
			table.setDefaultEditor(Object.class, de);
		}
		else{
			table.setDefaultEditor(Object.class, null );
		}
	}

	@Override
	public Object getSelectedItem() {
		return null;
	}

	@Override
	public void addEmptyObject() {
		if(roundList != null){
			roundList.add(null);
		}
	}

	@Override
	public void deleteObject(int modelRow) {
		if(roundList.get(modelRow) != null){
			deletedRound.add(roundList.get(modelRow));
		}
		roundList.remove(modelRow);
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



	public Vector<Round> getRoundList() {
		// TODO Auto-generated method stub
		return roundList;
	}

}
