package viewmodel;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;

import view.FootballPlayerPanel;
import view.StartProgram;
import model.Player;
import model.Team;
import database.DBConnector;
import database.DBPlayer;
import database.DBTeam;

public class FootballTeamProfileTable extends TableModel{

	private DefaultCellEditor dce;
	private TableCellEditor tce;
	private TableColumn typeColumn, dateColumn;
	private HashMap<String, Integer> idList;
	private Team team, teamOriginal;
	private Vector<Player> deletedPlayer;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public FootballTeamProfileTable(){
		super();
		deletedPlayer = new Vector<Player>();
		team = null;
		dtm.addColumn("STT");
		dtm.addColumn("Cầu thủ");
		dtm.addColumn("Ngày sinh");
		dtm.addColumn("Loại cầu thủ");
		dtm.addColumn("Ghi chú");
		dtm.addColumn("");
//		super.addEmptyRow(10);
		
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

	synchronized public void setTeam(Team team){
		if(team != null){
			this.teamOriginal = team;
			this.team = (Team) teamOriginal.clone();
		}
		else{
			this.teamOriginal = null;
			this.team = null;
		}
	}
	
	public HashMap<String, Integer> getIdList(){
		return idList;
	}

	@Override
	public Object getSelectedItem() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void getData(){
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				if(team == null){
//					System.out.println("team is null");
				}
				else{
					showData();
				}
				
			}
		});
		t.start();
	}
	
	public void showData(){
		try {
			int last = team.getPlayerList().size();
			HashMap<Integer, String> typeList = 
					DBPlayer.getStringTypeOfPlayer(DBConnector.getInstance());
			for (int i = 0; i < last; i++) {
				Vector<Object> row = new Vector<>();
				row.add(i + 1);
				row.add(team.getPlayerList().get(i).getName());
				row.add(changeDateFormat(team.getPlayerList().get(i).getDob()));
				row.add(typeList.get(team.getPlayerList().get(i).getTypeOfPlayer()));
				row.add(team.getPlayerList().get(i).getNote());
				dtm.addRow(row);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private String changeDateFormat(Date d){
		if(!isEnable){
			return d.toString();
		}
		else{
			SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, YYYY");
			return sdf.format(d);
		}
		
	}
	
	public void changeDateAfterEdit(){
		if(team != null){
			int last = team.getPlayerList().size();
			for (int i = 0; i < last; i++) {
				if(team.getPlayerList().get(i) != null){
					table.setValueAt(changeDateFormat(
							team.getPlayerList().get(i).getDob()), i, 2);
				}
				
			}
		}
		
	}

	@Override
	public void addEmptyObject() {
		if(team != null){
			team.getPlayerList().add(null);
		}
	}

	public void saveData(String teamName, String home_stadium){
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				if(team == null){
					
				}
				else{
					checkDifferents(teamName, home_stadium);
				}
				saveToOriginalTeam(team);
				
				((FootballPlayerPanel)(UpdateTabData.panelList
						.get(StartProgram.FOOTBALL_PLAYER_TAB))).updateData();;
//				setTeam(teamOriginal);
			}
		});
		t.start();
		
	}
	
	private void saveToOriginalTeam(Team t){
		teamOriginal.setId(t.getId());
		teamOriginal.setHome_stadium(t.getHome_stadium());
		teamOriginal.setName(t.getName());
		teamOriginal.setPlayerList(t.getPlayerList());
	}
	
	private void checkDifferents(String teamName, String home_stadium){
//		Vector<Player> players = new Vector<>();
//		players.addAll(team.getPlayerList());
		checkDeletePlayer();
		checkUpdateTeamInfo(teamName, home_stadium);
		checkUpdatePlayer();
		checkAddPlayer();
	}

	@Override
	public void deleteObject(int modelRow) {
		if(team.getPlayerList().get(modelRow) != null){
			deletedPlayer.add(team.getPlayerList().get(modelRow));
		}
		team.getPlayerList().remove(modelRow);
		
//		System.out.println(team.getName() + " " 
//				+ team.getHome_stadium() + " "
//				+ team.getId() + " ");
//		for (int i = 0; i < table.getRowCount(); i++) {
//			System.out.println(team.getPlayerList().get(i).getName());
//		}
	}
	
	private void checkAddPlayer(){
		try {
			int num = countPlayerNotEmpty();
			if(table.getRowCount() > num){
				Vector<Player> pList = new Vector<Player>();
				for (int i = num; i < table.getRowCount(); i++) {
					pList.add(new Player(0, table.getValueAt(i, 4).toString(),
							table.getValueAt(i, 1).toString(),
							idList.get(table.getValueAt(i, 3)),
							getDate(i)));
					System.out.println(i + ": " + getDate(i));					
				}
//				System.out.println("Dong 222" + num + " " + pList.size());
				int index = 0;
				for (int i = num; i < table.getRowCount(); i++) {
					team.getPlayerList().set(i, DBPlayer.addPlayer(DBConnector.getInstance(), 
							pList.get(index++), team.getId(), team.getName()));

				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Date getDate(int index){
		try {
			return new Date(new SimpleDateFormat("yyyy-MM-dd")
				.parse(table.getValueAt(index, 2).toString()).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private int countPlayerNotEmpty(){
		int count =  0;
		for (int i = 0; i < team.getPlayerList().size(); i++) {
			if(team.getPlayerList().get(i) != null){
				count++;
			}
		}
		return count;
	}
	
	private void checkUpdateTeamInfo(String teamName, String home_stadium){
		if(teamName != team.getName()){
			team.setName(teamName);
		}
		if(home_stadium != team.getHome_stadium()){
			team.setHome_stadium(home_stadium);
		}
		try {
			DBTeam.updateTeam(DBConnector.getInstance(), team);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void checkUpdatePlayer(){
		try {
			int last = countPlayerNotEmpty();
			Vector<Player> pList = team.getPlayerList();
			for (int i = 0; i < last; i++) {
				Player p = new Player(pList.get(i).getId(),
						pList.get(i).getTotal_goal(),
						table.getValueAt(i, 1).toString(),
						table.getValueAt(i, 4).toString(),
						idList.get(table.getValueAt(i, 3).toString()),
						getDate(i),
						pList.get(i).getTeamName());

				if(!p.isEqual(pList.get(i))){
					DBPlayer.updatePlayer(DBConnector.getInstance(), p);

				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void checkDeletePlayer(){
		try {
			for (int i = 0; i < deletedPlayer.size(); i++) {
				if(DBPlayer.canDelete(DBConnector.getInstance(), 
						deletedPlayer.get(i))){
					DBPlayer.deletePlayer(DBConnector.getInstance(), 
							deletedPlayer.get(i));
				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	
	public void clearNullPlayer(){
		if(team != null){
			for (int i = 0; i < team.getPlayerList().size(); i++) {
				if(team.getPlayerList().get(i) == null){
					team.getPlayerList().remove(i);
					i--;
				}
			}
		}
		
	}

	@Override
	public boolean canDelete(int index) {
		boolean result = false;		
		try {
			Player player = team.getPlayerList().get(index);
			if(player == null || 
					DBPlayer.canDelete(DBConnector.getInstance(), player)){
				result = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void showErrDelete() {
		JOptionPane.showMessageDialog(null,
				"Không được xóa cầu thủ đã có dữ liệu ghi bàn");
	}
	
}
