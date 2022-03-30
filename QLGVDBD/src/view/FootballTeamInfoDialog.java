package view;

import java.awt.*;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;

import java.awt.GridLayout;

@SuppressWarnings("serial")
public class FootballTeamInfoDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	public FootballTeamInfoDialog() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("<Team> Profile");
		setBounds(100, 100, 626, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			contentPanel.add(panel, BorderLayout.NORTH);
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			{
				JPanel panel_1 = new JPanel();
				panel.add(panel_1);
				{
					JLabel lblProfile = new JLabel("<Team> Profile");
					panel_1.add(lblProfile);
					lblProfile.setHorizontalAlignment(SwingConstants.CENTER);
				}
			}
			{
				JPanel panel_1 = new JPanel();
				panel.add(panel_1);
				panel_1.setLayout(new GridLayout(1, 0, 0, 0));
				{
					JPanel panel_2 = new JPanel();
					panel_1.add(panel_2);
					{
						JLabel lblFootballTeamName = new JLabel("Football team name:");
						panel_2.add(lblFootballTeamName);
						lblFootballTeamName.setHorizontalAlignment(SwingConstants.TRAILING);
					}
				}
				{
					JPanel panel_2 = new JPanel();
					panel_1.add(panel_2);
					{
						JLabel lblNewLabel = new JLabel("Home stadium:");
						panel_2.add(lblNewLabel);
					}
				}
			}
		}
		{
			TablePanel panel = new TablePanel(TablePanel.FOOTBALL_TEAM_PROFILE_TABLE);
			contentPanel.add(panel, BorderLayout.CENTER);
		}
		setLocationRelativeTo(null);
	}

}
