package view;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.FlowLayout;

@SuppressWarnings("serial")
public class MatchResultDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JButton btnAdd, btnSave;

	public MatchResultDialog() {
		setTitle("Kết quả trận đấu <Match>");
		setBounds(100, 100, 450, 300);
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
				panel_1.setLayout(new GridLayout(0, 3, 0, 0));
				{
					JPanel panel_2 = new JPanel();
					panel_1.add(panel_2);
					{
						JLabel lblNewLabel = new JLabel("Đội 1:");
						panel_2.add(lblNewLabel);
					}
					{
						JLabel lblNewLabel_1 = new JLabel("<name>");
						panel_2.add(lblNewLabel_1);
					}
				}
				{
					JPanel panel_2 = new JPanel();
					panel_1.add(panel_2);
					{
						JLabel lblNewLabel_2 = new JLabel("Đội 2:");
						panel_2.add(lblNewLabel_2);
					}
					{
						JLabel label = new JLabel("<name>");
						panel_2.add(label);
					}
				}
				{
					JPanel panel_2 = new JPanel();
					panel_1.add(panel_2);
					{
						JLabel lblTS = new JLabel("Tỷ số:");
						panel_2.add(lblTS);
					}
					{
						JLabel label = new JLabel("<tyso>");
						panel_2.add(label);
					}
				}
			}
			{
				JPanel panel_1 = new JPanel();
				panel.add(panel_1);
				panel_1.setLayout(new GridLayout(0, 3, 0, 0));
				{
					JPanel panel_2 = new JPanel();
					panel_1.add(panel_2);
					{
						JLabel lblSn = new JLabel("Sân:");
						panel_2.add(lblSn);
					}
					{
						JLabel label = new JLabel("<name>");
						panel_2.add(label);
					}
				}
				{
					JPanel panel_2 = new JPanel();
					panel_1.add(panel_2);
					{
						JLabel lblNgy = new JLabel("Ngày:");
						panel_2.add(lblNgy);
					}
					{
						JLabel label = new JLabel("<date>");
						panel_2.add(label);
					}
				}
				{
					JPanel panel_2 = new JPanel();
					panel_1.add(panel_2);
					{
						JLabel lblGi = new JLabel("Giờ:");
						panel_2.add(lblGi);
					}
					{
						JLabel label = new JLabel("<time>");
						panel_2.add(label);
					}
				}
			}
			{
				JPanel panel_1 = new JPanel();
				FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
				flowLayout.setAlignment(FlowLayout.TRAILING);
				panel.add(panel_1);
				{
					btnAdd = new JButton(new ImageIcon("resources/add.png"));
					btnAdd.setToolTipText("Add a new scored player");
					panel_1.add(btnAdd);
				}
				{
					btnSave = new JButton(new ImageIcon("resources/save.png"));
					btnAdd.setToolTipText("Save changes");
					panel_1.add(btnSave);
				}
			}
		}
		{
			TablePanel panel = new TablePanel(TablePanel.MATCH_RESULT_TABLE);
			contentPanel.add(panel, BorderLayout.CENTER);
		}
	}

}
