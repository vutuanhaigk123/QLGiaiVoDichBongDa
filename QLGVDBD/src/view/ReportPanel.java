package view;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;

import javax.swing.JButton;

import viewmodel.LeaderboardTable;
import viewmodel.ScoredPlayerListTable;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DateTimePicker;
import com.github.lgooddatepicker.components.DatePickerSettings.DateArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ReportPanel extends JPanel {

	private JButton btnSearch;
	private DatePicker datePicker;

	public ReportPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel panel_4 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_4.getLayout();
		flowLayout.setAlignment(FlowLayout.TRAILING);
		add(panel_4);


		DatePickerSettings settings = new DatePickerSettings();
		settings.setGapBeforeButtonPixels(0);
		settings.setSizeTextFieldMinimumWidthDefaultOverride(false);
		settings.setColor(DateArea.BackgroundMonthAndYearMenuLabels, Color.DARK_GRAY);
		settings.setColor(DateArea.BackgroundTodayLabel, Color.DARK_GRAY);
		settings.setColor(DateArea.BackgroundClearLabel, Color.DARK_GRAY);
		settings.setColor(DateArea.BackgroundCalendarPanelLabelsOnHover, Color.ORANGE);
		settings.setColor(DateArea.DatePickerTextValidDate, Color.ORANGE);
		
		JLabel lblPickDate = new JLabel("Chọn ngày lập báo cáo:");
		panel_4.add(lblPickDate);
		datePicker = new DatePicker(settings );
		panel_4.add(datePicker);
		btnSearch = new JButton(new ImageIcon("resources/search.png"));
		btnSearch.setFocusPainted( false );
		btnSearch.setToolTipText("Back to round list");
		panel_4.add(btnSearch);



		JPanel pnLeaderboard = new JPanel();
		add(pnLeaderboard);
		pnLeaderboard.setLayout(new BoxLayout(pnLeaderboard, BoxLayout.Y_AXIS));

		JPanel panel_1 = new JPanel();
		pnLeaderboard.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblBngXpHng = new JLabel("BẢNG XẾP HẠNG");
		panel_1.add(lblBngXpHng);

		TablePanel teamPanel = new TablePanel(TablePanel.LEADERBOARD_TABLE);
		pnLeaderboard.add(teamPanel);

		JPanel pnScoredPlayer = new JPanel();
		add(pnScoredPlayer);
		pnScoredPlayer.setLayout(new BoxLayout(pnScoredPlayer, BoxLayout.Y_AXIS));

		JPanel panel = new JPanel();
		pnScoredPlayer.add(panel);

		JLabel lblNewLabel = new JLabel("DANH SÁCH CẦU THỦ GHI BÀN");
		panel.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

		TablePanel playerPanel = new TablePanel(TablePanel.SCORED_PLAYER_LIST_TABLE);
		pnScoredPlayer.add(playerPanel);
		pnLeaderboard.setVisible(false);
		pnScoredPlayer.setVisible(false);

		btnSearch.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if(datePicker.getDate() == null){
					JOptionPane.showMessageDialog(null,
							"Vui lòng chọn ngày lập báo cáo");
				}
				else{
					pnLeaderboard.setVisible(true);
					pnScoredPlayer.setVisible(true);
					new Thread(new Runnable() {

						@Override
						public void run() {
							Date time = Date.valueOf(datePicker.getDate());
							((LeaderboardTable)teamPanel.getTblModel()).showData(time);
							((ScoredPlayerListTable)playerPanel.getTblModel()).showData(time);
						}
					}).start();
				}
			}
		});

	}

}
