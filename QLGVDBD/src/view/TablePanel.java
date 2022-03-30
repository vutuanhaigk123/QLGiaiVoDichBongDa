package view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import model.*;

@SuppressWarnings("serial")
public class TablePanel extends JPanel {

	public static final int FOOTBALL_TEAM_LIST_TABLE = 0;
	public static final int FOOTBALL_TEAM_PROFILE_TABLE = 1;
	public static final int COMPETITION_SCHEDULE_TABLE = 2;
	public static final int MATCH_RESULT_TABLE = 3;
	public static final int FOOTBALL_PLAYER_LIST_TABLE = 4;
	public static final int LEADERBOARD_TABLE = 5;
	public static final int SCORED_PLAYER_LIST_TABLE = 6;

	@SuppressWarnings("unused")
	private DefaultTableModel dtm;
	private JTable table; 
	private int tableMode;
	private TableModel tblModel;


	public TablePanel(int tableMode) {
		this.tableMode = tableMode;
		createTable();
		dtm = tblModel.getDtm();
		table = tblModel.getTable();
		addControls();
	}

	private void createTable(){
		switch (tableMode) {
			case FOOTBALL_TEAM_LIST_TABLE:
				tblModel = new FootballTeamListTable();
				break;
			case FOOTBALL_TEAM_PROFILE_TABLE:
				tblModel = new FootballTeamProfileTable();
				break;
			case COMPETITION_SCHEDULE_TABLE:
				tblModel = new CompetitionScheduleTable();
				break;
	
			case MATCH_RESULT_TABLE:
				tblModel = new MatchResultTable();
				break;
			case FOOTBALL_PLAYER_LIST_TABLE:
				tblModel = new FootballPlayerListTable();
				break;
			case LEADERBOARD_TABLE:
				tblModel = new LeaderboardTable();
				break;
			case SCORED_PLAYER_LIST_TABLE:
				tblModel = new ScoredPlayerListTable();
				break;
	
			default:
				break;
		}
	}

	public void addControls(){		
		this.setLayout(new BorderLayout());
		
		if(tableMode == FOOTBALL_TEAM_PROFILE_TABLE ||
				tableMode == COMPETITION_SCHEDULE_TABLE ||
				tableMode == MATCH_RESULT_TABLE ||
				tableMode == FOOTBALL_TEAM_LIST_TABLE){
			table.getColumnModel().getColumn(table.getColumnCount() - 1). setMaxWidth(40); 
			table.getColumnModel().getColumn(table.getColumnCount() - 1). setMinWidth(40);			
		}

		table.getColumnModel().getColumn(0). setMaxWidth(50); 
		table.getColumnModel().getColumn(0). setMinWidth(50);


		JScrollPane scrollPane = new JScrollPane(
				table,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.add(scrollPane, BorderLayout.CENTER);
	}

}
