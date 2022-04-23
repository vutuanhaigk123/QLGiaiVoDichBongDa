package viewmodel;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.table.TableColumn;

import view.FootballPlayerPanel;
import view.StartProgram;
import model.Match_Schedule;
import model.Player;
import model.Round;
import model.Team;
import database.DBConnector;
import database.DBMatchSchedule;
import database.DBPlayer;
import database.DBResultDetail;
import database.DBTeam;

public class CompetitionScheduleTable extends TableModel{
	
	private boolean isEnable;
	private DateTimeTableEditor dateTimeEdit;
	private TableColumn dateColumn, fTeamColumn, sTeamColumn;
	private JComboBox comboBox, comboBox2;
	private DefaultCellEditor editor1, editor2;
	private Round round;
	private Vector<Team> teamList, firstTeamList, secondTeamList;
	private Vector<Match_Schedule> msList, deletedMatches;

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
		

		
		firstTeamList = new Vector<Team>();
		secondTeamList = new Vector<Team>();
		deletedMatches = new Vector<Match_Schedule>();
		
		editor1 = new DefaultCellEditor(comboBox);
		editor2 = new DefaultCellEditor(comboBox2);
		editor1.setClickCountToStart(2);
		editor2.setClickCountToStart(2);
		table.getColumnModel().getColumn(1).setCellEditor(editor1);
		table.getColumnModel().getColumn(2).setCellEditor(editor2);
		
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
//		fTeamColumn.setCellEditor(new DefaultCellEditor(comboBox));
//		sTeamColumn.setCellEditor(new DefaultCellEditor(comboBox2));
		
		
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
	
	public void addEvents(){
		if(table.getRowCount() > 0){
			table.setRowSelectionInterval(0, 0);
			table.revalidate();
			table.repaint();
		}
		
		comboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(ItemEvent.SELECTED == e.getStateChange()){
					int num = countMatchNotEmpty();
					int index = comboBox.getSelectedIndex();
					if(index != -1 && teamList != null){
						table.setValueAt(teamList.get(index).getHome_stadium(),
								table.getSelectedRow(), 3);
						if(table.getSelectedRow() < num){
							firstTeamList.set(table.getSelectedRow(), 
									teamList.get(index));
							
						}
						else {
							firstTeamList.add(teamList.get(index));
						}
//						System.out.println("combo1: " 
//								+ table.getSelectedRow() + " "
//								+ msList.get(table.getSelectedRow()).getFirstTeam().getName() + " "
//						+ firstTeamList.get(table.getSelectedRow()).getName());
					}
				}
				
			}
		});
		comboBox2.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(ItemEvent.SELECTED == e.getStateChange()){
					int num = countMatchNotEmpty();
					int index = comboBox2.getSelectedIndex();
					if(index != -1 && teamList != null){
						if(table.getSelectedRow() < num){
							secondTeamList.set(table.getSelectedRow(), 
									teamList.get(index));
						}
						else {
							secondTeamList.add(teamList.get(index));
						}
					}
//					System.out.println("combo2: " 
//							+ table.getSelectedRow() + " "
//							+ msList.get(table.getSelectedRow()).getSecondTeam().getName() + " "
//					+ secondTeamList.get(table.getSelectedRow()).getName());
				}
				
			}
		});
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
		
	synchronized public void setRound(Round round){
		this.round = round;
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				dtm.setRowCount(0);
				showData();
				addEvents();
			}
		}).start();
		
	}
	
	public void showData(){
		firstTeamList.clear();
		secondTeamList.clear();
		msList = getData();
		if(msList != null){
			for (int i = 0; i < msList.size(); i++) {
				Vector<Object> row = new Vector<>();
				row.add(i + 1);
				row.add(msList.get(i).getFirstTeam().getName());
				row.add(msList.get(i).getSecondTeam().getName());
				row.add(msList.get(i).getFirstTeam().getHome_stadium());
				row.add(msList.get(i).getTime());
				dtm.addRow(row);
				firstTeamList.add(msList.get(i).getFirstTeam());
				secondTeamList.add(msList.get(i).getSecondTeam());
			}
		}
	}
	
	public Vector<Match_Schedule> getData(){
		Vector<Match_Schedule> tList = null;
		try {
			tList = DBMatchSchedule.
					getAllMatchSchedule(DBConnector.getInstance(), round.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tList;
		
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
//			fTeamColumn.setCellEditor(new DefaultCellEditor(comboBox));
//			sTeamColumn.setCellEditor(new DefaultCellEditor(comboBox2));
			fTeamColumn.setCellEditor(editor1);
			sTeamColumn.setCellEditor(editor2);
			isEnable = true;
		}
		else{
			table.setDefaultEditor(Object.class, null );
			dateColumn.setCellEditor(null);
			dateColumn.setCellRenderer(null);
			fTeamColumn.setCellEditor(null);
			sTeamColumn.setCellEditor(null);
			isEnable = false;
			table.getSelectionModel().clearSelection();
		}
		table.repaint();
	}
	
	public Vector<Match_Schedule> getMatchScheduleList(){
		return msList;
	}

	public void saveData(){
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				checkDifferents();
			}});
		t.start();
		
	}
	
	public void checkDifferents(){
		checkDeleteMatch();
		checkUpdateMatch();
		checkAddMatch();
	}
	
	private void checkUpdateMatch() {
		try {
			int last = countMatchNotEmpty();
			for (int i = 0; i < last; i++) {
				Match_Schedule ms = new Match_Schedule(
						msList.get(i).getId(), 
						round.getId(), 
						convertToLocalDateTime(table.getValueAt(i, 4).toString()),
						firstTeamList.get(i), 
						secondTeamList.get(i), 
						table.getValueAt(i, 3).toString(), 
						msList.get(i).getMatchResult());
				if(ms.getFirstTeam().getId() != msList.get(i).getFirstTeam().getId() ||
						ms.getSecondTeam().getId() != msList.get(i).getSecondTeam().getId() || 
						!ms.getTime().toString().equals(table.getValueAt(i, 4).toString()) ){
					System.out.println("Thay đổi: " + msList.get(i).getFirstTeam().getName() + " " 
							+ msList.get(i).getSecondTeam().getName()  + " thành "
							+ ms.getFirstTeam().getName() 
							+ "," + ms.getSecondTeam().getName());
					DBMatchSchedule.updateMatchSchedule(DBConnector.getInstance(), ms);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static LocalDateTime convertToLocalDateTime(String time){
		return LocalDateTime.parse(time);
	}
	
	private void checkAddMatch() {
		try {
			int num = countMatchNotEmpty();
			if(table.getRowCount() > num){
				Vector<Match_Schedule> tempList = new Vector<Match_Schedule>();
				for (int i = num; i < table.getRowCount(); i++) {				
					tempList.add(new Match_Schedule(round.getId(),
							convertToLocalDateTime(table.getValueAt(i, 4).toString()),
							firstTeamList.get(i),
							secondTeamList.get(i),
							table.getValueAt(i, 3).toString(), null));			
				}
				int index = 0;
				for (int i = num; i < table.getRowCount(); i++) {
					msList.set(i, DBMatchSchedule.addMatchSchedule(
							DBConnector.getInstance(), 
							round.getId(), 
							tempList.get(index++)));

				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private int countMatchNotEmpty(){
		int count =  0;
		for (int i = 0; i < msList.size(); i++) {
			if(msList.get(i) != null){
				count++;
			}
		}
		return count;
	}

	private void checkDeleteMatch() {
		try {
			for (int i = 0; i < deletedMatches.size(); i++) {
				DBMatchSchedule.deleteMatch(DBConnector.getInstance(), 
						deletedMatches.get(i));
			}
			deletedMatches.clear();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		if(msList != null){
			msList.add(null);
		}
	}

	@Override
	public void deleteObject(int modelRow) {
		if(msList.get(modelRow) != null){
			deletedMatches.add(msList.get(modelRow));
		}
		msList.remove(modelRow);
		firstTeamList.remove(modelRow);
		secondTeamList.remove(modelRow);
//		for (int i = 0; i < deletedMatches.size(); i++) {
//			System.out.println(deletedMatches.get(i).getFirstTeam().getName());
//		}
	}

	@Override
	public boolean canDelete(int index) {
		return true;
	}

	@Override
	public void showErrDelete() {
		// TODO Auto-generated method stub
		
	}

}
