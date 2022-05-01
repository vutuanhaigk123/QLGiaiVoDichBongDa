package view;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import viewmodel.LeaderboardTable;
import viewmodel.ScoredPlayerListTable;

import java.awt.FlowLayout;

@SuppressWarnings("serial")
public class ReportPanel extends JPanel {

	/**
	 * Create the panel.
	 */

	private TablePanel leaderboardPanel, playerListPanel;

	public ReportPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel pnLeaderboard = new JPanel();
		add(pnLeaderboard);
		pnLeaderboard.setLayout(new BoxLayout(pnLeaderboard, BoxLayout.Y_AXIS));

		JPanel panel_1 = new JPanel();
		pnLeaderboard.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblBngXpHng = new JLabel("BẢNG XẾP HẠNG");
		panel_1.add(lblBngXpHng);

		leaderboardPanel = new TablePanel(TablePanel.LEADERBOARD_TABLE);
		pnLeaderboard.add(leaderboardPanel);

		JPanel pnScoredPlayer = new JPanel();
		add(pnScoredPlayer);
		pnScoredPlayer.setLayout(new BoxLayout(pnScoredPlayer, BoxLayout.Y_AXIS));

		JPanel panel = new JPanel();
		pnScoredPlayer.add(panel);

		JLabel lblNewLabel = new JLabel("DANH SÁCH CẦU THỦ GHI BÀN");
		panel.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

		playerListPanel = new TablePanel(TablePanel.SCORED_PLAYER_LIST_TABLE);
		pnScoredPlayer.add(playerListPanel);

	}

	public void getLeaderboardPanel() {
		((LeaderboardTable) leaderboardPanel.getTblModel()).showLeaderboard();
	}

	public void setLeaderboardPanel(TablePanel leaderboardPanel) {
		this.leaderboardPanel = leaderboardPanel;
	}

	public void getPlayerListPanel() {
		((ScoredPlayerListTable) playerListPanel.getTblModel()).getData();
	}

	public void setPlayerListPanel(TablePanel playerListPanel) {
		this.playerListPanel = playerListPanel;
	}

}
