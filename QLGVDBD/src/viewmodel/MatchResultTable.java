package viewmodel;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;


import model.Match_Schedule;
import model.Player;
import model.Regulation;
import model.Result;
import model.ResultDetail;
import database.DBConnector;
import database.DBMatchSchedule;
import database.DBPlayer;
import database.DBRegulationList;
import database.DBResultDetail;


public class MatchResultTable extends TableModel{

	private HashMap<String, Integer> typeOfGoalMap;
	private DefaultCellEditor editor1, editor2;
	private TableCellEditor tce;
	private DefaultCellEditor dce;
	private Vector<ResultDetail> deletedResultDetail;
	private Vector<Player> playerList, selectedPlayerList;
	private Result res;
	private Match_Schedule ms;
	private JComboBox<String> cbPlayer;

	public MatchResultTable(){
		super();
		table = new JTable(dtm){

	        public boolean isCellEditable(int row, int column) {                
	                return column != 0 && column != 2;               
	        };
	    };
	    table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
	    
		dtm.addColumn("STT");
		dtm.addColumn("Cầu thủ");
		dtm.addColumn("Đội");
		dtm.addColumn("Loại bàn thắng");
		dtm.addColumn("Thời điểm (phút thứ)");
		dtm.addColumn("");
		deletedResultDetail = new Vector<ResultDetail>();
		playerList = new Vector<Player>();
		selectedPlayerList= new Vector<Player>();
		JComboBox<String> cbTypeOfGoal = new JComboBox<String>();
		cbPlayer = new JComboBox<String>();
		try {
			typeOfGoalMap = DBRegulationList.getAllTypeOfGoal(DBConnector.getInstance());
			typeOfGoalMap.forEach((k,v) ->
			{
				cbTypeOfGoal.addItem(k);
			});
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tce = table.getDefaultEditor(Object.class);
		cbTypeOfGoal.setSelectedIndex(-1);
		editor1 = new DefaultCellEditor(cbTypeOfGoal);
		editor1.setClickCountToStart(2);
		table.getColumnModel().getColumn(3).setCellEditor(editor1);
		editor2 = new DefaultCellEditor(cbPlayer);
		editor2.setClickCountToStart(2);
		table.getColumnModel().getColumn(1).setCellEditor(editor2);

		super.addEmptyRow(10);
		super.bindingDeleteBtn("Are you sure to delete this match result?");

		//tblPkg.setAutoCreateRowSorter(true);
		//		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(dtm);
		//		tblPkg.setRowSorter(sorter);
		//		tblPkg.getRowSorter().toggleSortOrder(0);
		// Prevent manager edit this table
		//		tblPkg.setDefaultEditor(Object.class, null);		

	}

	public void showData(Result res, Match_Schedule ms, 
			JTextField txtFTeamScore, 
			JTextField txtSTeamScore){
		this.ms = ms;
		new Thread(new Runnable() {

			@Override
			public void run() {
				addCombobox();
				setResult(res);
				if(res != null){
					txtFTeamScore.setText(res.getFirstTeamScore() + "");
					txtSTeamScore.setText(res.getSecondTeamScore() + "");
				}
				
				
				
			}
		}).start();

		
		
	}

	private void addCombobox(){
		try {
			playerList.addAll(DBPlayer.getAllPlayer(
					DBConnector.getInstance(), ms.getFirstTeam().getId()));
			playerList.addAll(DBPlayer.getAllPlayer(
					DBConnector.getInstance(), ms.getSecondTeam().getId()));
			for (int i = 0; i < playerList.size(); i++) {
				cbPlayer.addItem(playerList.get(i).getName());
			}
			cbPlayer.setSelectedIndex(-1);
			dce = new DefaultCellEditor(cbPlayer);
			table.getColumnModel().getColumn(1).setCellEditor(dce);
			cbPlayer.addItemListener(new ItemListener() {
				
				@Override
				public void itemStateChanged(ItemEvent e) {
					if(e.getStateChange() == ItemEvent.SELECTED){
						int cbSelected = cbPlayer.getSelectedIndex();
						int selectedRow = table.getSelectedRow();
						table.setValueAt(playerList.get(cbSelected)
								.getTeamName(), 
								selectedRow, 2);
						selectedPlayerList.set(selectedRow, 
								playerList.get(cbSelected));
//						System.out.println(selectedPlayerList.get(selectedRow).getName());
					}
				}
			});
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	synchronized private void setResult(Result res){
		this.res = res;
		getData();
		dtm.setRowCount(0);
		if(res != null){
			selectedPlayerList.clear();
			for (int i = 0; i < res.getScoredList().size(); i++) {
				Vector<Object> row = new Vector<>();
				row.add(i + 1);
				row.add(res.getScoredList().get(i).getPlayer().getName());
				row.add(res.getScoredList().get(i).getPlayer().getTeamName());
				row.add(res.getScoredList().get(i).getTypeOfGoal());
				row.add(res.getScoredList().get(i).getTime());
				dtm.addRow(row);
				selectedPlayerList.add(res.getScoredList().get(i).getPlayer());
			}
		}


	}

	
	private void getData(){
		try {
			if(res != null){
				Vector<ResultDetail> resDetailList = DBResultDetail.getResultDetailByID(
						DBConnector.getInstance(), res.getId());
				if(res.getScoredList() != null){
					res.getScoredList().clear();
					res.getScoredList().addAll(resDetailList);
				}
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	synchronized public void setEnable(boolean b){
		if(b){
			table.setDefaultEditor(Object.class, tce);
			table.getColumnModel().getColumn(3).setCellEditor(editor1);
			table.getColumnModel().getColumn(1).setCellEditor(editor2);
			table.getColumnModel().getColumn(2).setCellEditor(null);
			isEnable = true;
		}
		else{
			table.setDefaultEditor(Object.class, null);
			isEnable = false;
		}
	}
	
	public int countOccurences(){
		int count = 0;
		if(res != null && res.getScoredList() != null){
			for (int i = 0; i < res.getScoredList().size(); i++) {
				if(res.getScoredList().get(i) != null){
					count++;
				}
				else{
					break;
				}
			}
		}
		return count;
	}
	
	public void addEmptyRow(int quantity){
		super.addEmptyRow(quantity);
		addEmptyObject();
	}

	@Override
	public Object getSelectedItem() {
		// TODO Auto-generated method stub
		return null;
	}

 	
	@Override
	public void addEmptyObject() {
		if(res != null){
			res.getScoredList().add(null);
		}

		selectedPlayerList.add(null);
	}

	@Override
	public void deleteObject(int modelRow) {
		if(res.getScoredList().get(modelRow) != null){
			deletedResultDetail.add(res.getScoredList().get(modelRow));
		}
		res.getScoredList().remove(modelRow);
		selectedPlayerList.remove(modelRow);
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

	
	public void saveData(int first_team_score, int second_team_score) {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				checkDifferents(first_team_score, second_team_score);
			}});
		t.start();
	}

	public void checkDifferents(int first_team_score, int second_team_score){
		if(res != null){
			createIfNeeded();
			checkDeleteResult();
			checkUpdateScore(first_team_score, second_team_score);
			checkUpdateResult();
			checkAddResult();
		}
		else{

			try {
				Vector<ResultDetail> resultDetail = new Vector<ResultDetail>();
				for (int i = 0; i < selectedPlayerList.size(); i++) {
					resultDetail.add(new ResultDetail(-1, 
							selectedPlayerList.get(i), 
							table.getValueAt(i, 3).toString(),
							Integer.parseInt(table.getValueAt(i, 4).toString())));
				}
				ms.setMatchResult(DBResultDetail.addResult(
								DBConnector.getInstance(), 
								new Result(-1, first_team_score, 
								first_team_score, resultDetail),ms.getId()));
				res = ms.getMatchResult();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void createIfNeeded() {
		if(res == null){
			Result temp;
			try {
				temp = new Result(
								DBResultDetail.createResultInDB(DBConnector.getInstance(), 
								0, 0), 0, 0, new Vector<ResultDetail>());
				ms.setMatchResult(temp);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		res = ms.getMatchResult();
	}

	private void checkAddResult() {
		try {
//			int num = countPlayerNotEmpty();
			int num = res.getScoredList().size();
			boolean condition = false;
			if(table.getRowCount() > countOccurences()){
				condition = true;
				num = countOccurences();
			}
			System.out.println(289 + " " + table.getRowCount() + " " + num + " " + countOccurences());
			if(table.getRowCount() > num || condition){
				Vector<ResultDetail> resultDetailList = new Vector<ResultDetail>();
				for (int i = num; i < table.getRowCount(); i++) {
					resultDetailList.add(new ResultDetail(-1, 
							selectedPlayerList.get(i), 
							table.getValueAt(i, 3).toString(),
							Integer.parseInt(table.getValueAt(i, 4).toString())));				
				}
				System.out.println(312);
				int index = 0;
				HashMap<String, Integer> idTypeOfGoalMap = 
						DBResultDetail.getAllTypeOfGoalMap(DBConnector.getInstance());
				for (int i = num; i < table.getRowCount(); i++) {
					ResultDetail rdTemp = DBResultDetail.createResultDetailInDB(
							DBConnector.getInstance(), 
							resultDetailList.get(index++), 
							res.getId(), idTypeOfGoalMap);
					System.out.println(num + " " + res.getScoredList().size());
					if(res.getScoredList().size() - 1 <= i && 
							res.getScoredList().size() - 1 != -1){
						res.getScoredList().set(i, rdTemp);
					}
					else {
						res.getScoredList().add(rdTemp);
						num++;
					}
					

				}
				ms.setMatchResult(res);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void checkUpdateResult() {
		try {
			int last = countOccurences();
			HashMap<String, Integer> idTypeOfGoalMap = 
					DBResultDetail.getAllTypeOfGoalMap(DBConnector.getInstance());
			for (int i = 0; i < last; i++) {
				if(res.getScoredList().get(i).getPlayer().getId() != 
						selectedPlayerList.get(i).getId() ||
						!res.getScoredList().get(i).getTypeOfGoal().equals(
								table.getValueAt(i, 3).toString()) ||
						res.getScoredList().get(i).getTime() != 
								Integer.parseInt(table.getValueAt(i, 4).toString())){
					
					
					ResultDetail rd = new ResultDetail(res.getScoredList().get(i).getId(), 
							selectedPlayerList.get(i), 
							table.getValueAt(i, 3).toString(), 
							Integer.parseInt(table.getValueAt(i, 4).toString()));
					DBResultDetail.updateResultDetail(DBConnector.getInstance(), rd, 
							idTypeOfGoalMap.get(table.getValueAt(i, 3).toString()));
					res.getScoredList().set(i, rd);
				}
				
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void checkUpdateScore(int first_team_score, int second_team_score){
		if(first_team_score != res.getFirstTeamScore() ||
				second_team_score != res.getSecondTeamScore()){
			try {
				DBMatchSchedule.updateResultScore(DBConnector.getInstance(), res.getId(),
						first_team_score, second_team_score);
				res.setFirstTeamScore(first_team_score);
				res.setSecondTeamScore(second_team_score);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void checkDeleteResult() {
		try {
			for (int i = 0; i < deletedResultDetail.size(); i++) {
				System.out.println(deletedResultDetail.get(i).getPlayer().getName());
				System.out.println(
				DBMatchSchedule.deleteResultDetail(DBConnector.getInstance(), 
						deletedResultDetail.get(i)));
			}
			deletedResultDetail.clear();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deleteResultDetail(){
		try {
//			DBResultDetail.deleteResultDetail(DBConnector.getInstance(), res.getId());
			DBMatchSchedule.updateMatchScheduleResultToNull(DBConnector.getInstance(), 
					ms.getId());
			DBResultDetail.deleteResult(DBConnector.getInstance(), res.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String isValid(){
		for (int i = 0; i < table.getRowCount(); i++) {
			if(table.getValueAt(i, 1).toString().trim().length() == 0 ||
					table.getValueAt(i, 3).toString().trim().length() == 0 || 
					table.getValueAt(i, 4).toString().trim().length() == 0){
				return "Vui lòng nhập đủ thông tin vào dòng còn thiếu";
			}
			if(Integer.parseInt(table.getValueAt(i, 4).toString()) < 0||
					Integer.parseInt(table.getValueAt(i, 4).toString()) > 
					Integer.parseInt(Regulation.regulationList.get(5).getValue())){
				return "Thời điểm ghi bàn phải từ 0 - " + Regulation.regulationList.get(5);
			}
			
		}
		return null;
	}
	
}
