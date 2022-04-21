package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import database.DBConnector;
import database.DBPlayer;
import model.Player;
import viewmodel.FootballPlayerListTable;

@SuppressWarnings("serial")
public class FootballPlayerPanel extends JPanel {

	private JButton btnSearch;
	private JTextField txtSearch;
	private TablePanel panel_1;
	public static Vector<Player> searchResult;

	public FootballPlayerPanel() {
		setLayout(new GridLayout(1, 0, 0, 50));

		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));

		panel_1 = new TablePanel(TablePanel.FOOTBALL_PLAYER_LIST_TABLE);
		panel.add(panel_1);

		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.NORTH);
		FlowLayout flowLayout = (FlowLayout) panel_3.getLayout();
		flowLayout.setAlignment(FlowLayout.TRAILING);

		txtSearch = new JTextField();
		txtSearch.setToolTipText("Type name or player ID");
		panel_3.add(txtSearch);
		txtSearch.setColumns(15);

		btnSearch = new JButton(new ImageIcon("resources/search.png"));
		btnSearch.setFocusPainted(false);
		btnSearch.setToolTipText("Search football player");
		panel_3.add(btnSearch);

		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					System.out.println("Keyword: " + "<" + txtSearch.getText() + ">");
					panel.remove(panel_1);
					searchResult = DBPlayer.searchPlayer(DBConnector.getInstance(), txtSearch.getText());
					panel_1 = new TablePanel(TablePanel.FOOTBALL_PLAYER_SEARCH_TABLE);
					panel.add(panel_1);
					panel_1.add(panel_3, BorderLayout.NORTH);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}

	public void updateData() {
		((FootballPlayerListTable) (panel_1.getTblModel())).getData();
	}

}
