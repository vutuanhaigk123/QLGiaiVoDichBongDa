package view;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.*;

import viewmodel.UpdateTabData;
import database.DBConnector;
import database.DBTeam;
import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.JMarsDarkTheme;

public class StartProgram {
	
	public static final String FOOTBALL_TEAM_TAB = "Football team";
	public static final String COMPETITION_TAB = "Competition";
	public static final String REPORT_TAB = "Report";
	public static final String REGULATION_TAB = "Regulation";
	public static final String FOOTBALL_PLAYER_TAB = "Football Player";
	
	static {
		try {
			UIManager.setLookAndFeel(new MaterialLookAndFeel(new JMarsDarkTheme()));
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	static void add(JTabbedPane tabbedPane, String label) {
		JPanel panel = null;
		switch (label) {
			case FOOTBALL_TEAM_TAB:
				panel = new FootballTeamPanel();
				break;
				
			case FOOTBALL_PLAYER_TAB:
				panel = new FootballPlayerPanel();
				break;
				
			case COMPETITION_TAB:
				panel = new CompetitionMatchPanel();
				break;
				
			case REPORT_TAB:
				panel = new ReportPanel();
				break;
				
			case REGULATION_TAB:
				panel = new RegulationPanel();
				break;
	
			default:
				break;
		}
		UpdateTabData.panelList.put(label, panel);
		tabbedPane.addTab(label, panel);
	}

	public static void main(String args[]) {
		try {
			final DBConnector db = DBConnector.getInstance();
			UpdateTabData.panelList = new HashMap<String, JPanel>();
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					JFrame frame = new JFrame("National Football Championship Manager");
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

					JTabbedPane tabbedPane = new JTabbedPane();
					tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
					String titles[] = {FOOTBALL_TEAM_TAB, FOOTBALL_PLAYER_TAB, 
							COMPETITION_TAB, REPORT_TAB, REGULATION_TAB};
					for (int i = 0, n = titles.length; i < n; i++) {
						add(tabbedPane, titles[i]);
					}

					frame.add(tabbedPane, BorderLayout.CENTER);
					frame.setMinimumSize(new Dimension(900, 400));
					frame.setSize(1100, 500);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
					frame.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosing(WindowEvent e) {
							db.close();
						}
//						@Override
//						public void windowClosed(WindowEvent e) {
//							dbi.close();
//						}
					});
				}
			});
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"Kết nối tới cơ sở dữ liệu thất bại. Vui lòng thử lại sau!");
		}
		
		
	}
}