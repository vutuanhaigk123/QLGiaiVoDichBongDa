package view;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.BoxLayout;

import java.awt.BorderLayout;

import javax.swing.JButton;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JTextField;

import database.DBConnector;
import database.DBPriority;
import database.DBRegulationList;
import model.Priority;
import model.Regulation;
import viewmodel.UpdateTabData;

import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class RegulationPanel extends JPanel {
	private JTextField txtMinAge;
	private JTextField txtMaxPlayerAge;
	private JTextField txtWinScore;
	private JTextField txtTiredScore;
	private JTextField txtDefeatScore;
	private JTextField txtMinNumOfPlayers;
	private JTextField txtMaxNumOfPlayer;
	private JTextField txtMaxAbroadPlayer;
	private JTextField txtNumOfScored;
	private JTextField txtMaxTimeScored;
	private JButton btnEdit, btnSave;
	private Vector<Priority> priorityList;
	private Vector<JComboBox<String>> cbList;
	private Vector<JPanel> jPanelList;
	private Vector<JLabel> jLabelList;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public RegulationPanel() {
		setLayout(new BorderLayout(0, 0));

		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.TRAILING);
		add(panel, BorderLayout.NORTH);

		btnEdit = new JButton("Edit regulations");
		btnEdit.setIcon(new ImageIcon("resources/edit.png"));
		btnEdit.setFocusPainted(false);
		btnEdit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				btnEdit.setVisible(false);
				btnSave.setVisible(true);
				setEditable(true);
			}
		});
		panel.add(btnEdit);

		btnSave = new JButton("Save changes");
		btnSave.setIcon(new ImageIcon("resources/save.png"));
		btnSave.setFocusPainted(false);
		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!txtMinAge.getText().chars().allMatch(Character::isDigit)
						|| !txtMaxPlayerAge.getText().chars().allMatch(Character::isDigit)
						|| !txtWinScore.getText().chars().allMatch(Character::isDigit)
						|| !txtTiredScore.getText().chars().allMatch(Character::isDigit) ||
						!txtDefeatScore.getText().chars().allMatch(Character::isDigit)
						|| !txtMinNumOfPlayers.getText().chars().allMatch(Character::isDigit)
						|| !txtMaxNumOfPlayer.getText().chars().allMatch(Character::isDigit)
						|| !txtMaxAbroadPlayer.getText().chars().allMatch(Character::isDigit) ||
						!txtNumOfScored.getText().chars().allMatch(Character::isDigit)
						|| !txtMaxTimeScored.getText().chars().allMatch(Character::isDigit) ||
						!canSave()) {
					JOptionPane.showMessageDialog(null, "Thông tin không hợp lệ", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
				}

				else {
					if (checkValid() == false) {
						return;
					}
					try {
						getDataToSave();
						DBRegulationList.saveData(DBConnector.getInstance(), Regulation.regulationList);
						changePriority();
						((ReportPanel) (UpdateTabData.panelList
								.get(StartProgram.REPORT_TAB))).getLeaderboardPanel();
						((ReportPanel) (UpdateTabData.panelList
								.get(StartProgram.REPORT_TAB))).getPlayerListPanel();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					btnSave.setVisible(false);
					btnEdit.setVisible(true);
					setEditable(false);
				}
			}

		});
		panel.add(btnSave);
		btnSave.setVisible(false);

		JPanel panel_16 = new JPanel();
		add(panel_16, BorderLayout.CENTER);
		panel_16.setLayout(new BoxLayout(panel_16, BoxLayout.Y_AXIS));

		JPanel panel_1 = new JPanel();
		panel_16.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 4, 0, 0));

		JPanel panel_3 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_3.getLayout();
		flowLayout_1.setAlignment(FlowLayout.TRAILING);
		panel_1.add(panel_3);

		JLabel lblNewLabel = new JLabel("Tuổi tối thiểu của cầu thủ:");
		panel_3.add(lblNewLabel);

		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout_8 = (FlowLayout) panel_2.getLayout();
		flowLayout_8.setAlignment(FlowLayout.LEADING);
		panel_1.add(panel_2);

		txtMinAge = new JTextField();
		panel_2.add(txtMinAge);
		txtMinAge.setColumns(10);

		JPanel panel_4 = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panel_4.getLayout();
		flowLayout_2.setAlignment(FlowLayout.TRAILING);
		panel_1.add(panel_4);

		JLabel lblSLngCu_1 = new JLabel("Số lượng cầu thủ tối đa:");
		panel_4.add(lblSLngCu_1);

		JPanel panel_5 = new JPanel();
		FlowLayout flowLayout_9 = (FlowLayout) panel_5.getLayout();
		flowLayout_9.setAlignment(FlowLayout.LEADING);
		panel_1.add(panel_5);

		txtMaxNumOfPlayer = new JTextField();
		panel_5.add(txtMaxNumOfPlayer);
		txtMaxNumOfPlayer.setColumns(10);

		JPanel panel_6 = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) panel_6.getLayout();
		flowLayout_3.setAlignment(FlowLayout.TRAILING);
		panel_1.add(panel_6);

		JLabel lblSLngCu = new JLabel("Số lượng cầu thủ tối thiểu:");
		panel_6.add(lblSLngCu);

		JPanel panel_7 = new JPanel();
		FlowLayout flowLayout_10 = (FlowLayout) panel_7.getLayout();
		flowLayout_10.setAlignment(FlowLayout.LEADING);
		panel_1.add(panel_7);

		txtMinNumOfPlayers = new JTextField();
		panel_7.add(txtMinNumOfPlayers);
		txtMinNumOfPlayers.setColumns(10);

		JPanel panel_8 = new JPanel();
		FlowLayout flowLayout_4 = (FlowLayout) panel_8.getLayout();
		flowLayout_4.setAlignment(FlowLayout.TRAILING);
		panel_1.add(panel_8);

		JLabel lblSLngCc = new JLabel("Số lượng các loại bàn thắng:");
		panel_8.add(lblSLngCc);

		JPanel panel_9 = new JPanel();
		FlowLayout flowLayout_11 = (FlowLayout) panel_9.getLayout();
		flowLayout_11.setAlignment(FlowLayout.LEADING);
		panel_1.add(panel_9);

		txtNumOfScored = new JTextField();
		panel_9.add(txtNumOfScored);
		txtNumOfScored.setColumns(10);

		JPanel panel_10 = new JPanel();
		FlowLayout flowLayout_5 = (FlowLayout) panel_10.getLayout();
		flowLayout_5.setAlignment(FlowLayout.TRAILING);
		panel_1.add(panel_10);

		JLabel lblSCuTh = new JLabel("Số cầu thủ nước ngoài tối đa:");
		panel_10.add(lblSCuTh);

		JPanel panel_11 = new JPanel();
		FlowLayout flowLayout_12 = (FlowLayout) panel_11.getLayout();
		flowLayout_12.setAlignment(FlowLayout.LEADING);
		panel_1.add(panel_11);

		txtMaxAbroadPlayer = new JTextField();
		panel_11.add(txtMaxAbroadPlayer);
		txtMaxAbroadPlayer.setColumns(10);

		JPanel panel_12 = new JPanel();
		FlowLayout flowLayout_6 = (FlowLayout) panel_12.getLayout();
		flowLayout_6.setAlignment(FlowLayout.TRAILING);
		panel_1.add(panel_12);

		JLabel lblimThng = new JLabel("Điểm thắng:");
		panel_12.add(lblimThng);

		JPanel panel_13 = new JPanel();
		FlowLayout flowLayout_13 = (FlowLayout) panel_13.getLayout();
		flowLayout_13.setAlignment(FlowLayout.LEADING);
		panel_1.add(panel_13);

		txtWinScore = new JTextField();
		panel_13.add(txtWinScore);
		txtWinScore.setColumns(10);

		JPanel panel_14 = new JPanel();
		FlowLayout flowLayout_7 = (FlowLayout) panel_14.getLayout();
		flowLayout_7.setAlignment(FlowLayout.TRAILING);
		panel_1.add(panel_14);

		JLabel lblThiimGhi = new JLabel("Thời điểm ghi bàn tối đa:");
		panel_14.add(lblThiimGhi);

		JPanel panel_15 = new JPanel();
		FlowLayout flowLayout_14 = (FlowLayout) panel_15.getLayout();
		flowLayout_14.setAlignment(FlowLayout.LEADING);
		panel_1.add(panel_15);

		txtMaxTimeScored = new JTextField();
		panel_15.add(txtMaxTimeScored);
		txtMaxTimeScored.setColumns(10);

		JPanel panel_20 = new JPanel();
		FlowLayout flowLayout_16 = (FlowLayout) panel_20.getLayout();
		flowLayout_16.setAlignment(FlowLayout.TRAILING);
		panel_1.add(panel_20);

		JLabel lblimHo = new JLabel("Điểm hoà:");
		panel_20.add(lblimHo);

		JPanel panel_22 = new JPanel();
		FlowLayout flowLayout_18 = (FlowLayout) panel_22.getLayout();
		flowLayout_18.setAlignment(FlowLayout.LEADING);
		panel_1.add(panel_22);

		txtTiredScore = new JTextField();
		panel_22.add(txtTiredScore);
		txtTiredScore.setColumns(10);

		JPanel panel_24 = new JPanel();
		FlowLayout flowLayout_20 = (FlowLayout) panel_24.getLayout();
		flowLayout_20.setAlignment(FlowLayout.TRAILING);
		panel_1.add(panel_24);

		JLabel lblTuiTia = new JLabel("Tuổi tối đa của cầu thủ:");
		panel_24.add(lblTuiTia);

		JPanel panel_25 = new JPanel();
		FlowLayout flowLayout_15 = (FlowLayout) panel_25.getLayout();
		flowLayout_15.setAlignment(FlowLayout.LEADING);
		panel_1.add(panel_25);

		txtMaxPlayerAge = new JTextField();
		panel_25.add(txtMaxPlayerAge);
		txtMaxPlayerAge.setColumns(10);

		JPanel panel_26 = new JPanel();
		FlowLayout flowLayout_17 = (FlowLayout) panel_26.getLayout();
		flowLayout_17.setAlignment(FlowLayout.TRAILING);
		panel_1.add(panel_26);

		JLabel lblimThua = new JLabel("Điểm thua:");
		panel_26.add(lblimThua);

		JPanel panel_27 = new JPanel();
		FlowLayout flowLayout_19 = (FlowLayout) panel_27.getLayout();
		flowLayout_19.setAlignment(FlowLayout.LEADING);
		panel_1.add(panel_27);

		txtDefeatScore = new JTextField();
		panel_27.add(txtDefeatScore);
		txtDefeatScore.setColumns(10);

		JPanel panel_18 = new JPanel();
		panel_16.add(panel_18);
		panel_18.setLayout(new BoxLayout(panel_18, BoxLayout.Y_AXIS));

		JPanel panel_19 = new JPanel();
		panel_18.add(panel_19);

		JLabel lblThTu = new JLabel("Thứ tự ưu tiên khi xếp hạng:");
		panel_19.add(lblThTu);

		JPanel panel_17 = new JPanel();
		panel_18.add(panel_17);
		panel_17.setLayout(new GridLayout(0, 2, 0, 0));

		JPanel panel_23 = new JPanel();
		FlowLayout flowLayout_21 = (FlowLayout) panel_23.getLayout();
		flowLayout_21.setAlignment(FlowLayout.TRAILING);
		panel_17.add(panel_23);

		JLabel label = new JLabel("1.");
		panel_23.add(label);

		jPanelList = new Vector<JPanel>();
		jLabelList = new Vector<JLabel>();

		jLabelList.add(label);
		// cb1 = new JComboBox();
		// panel_23.add(cb1);
		jPanelList.add(panel_23);

		JPanel panel_30 = new JPanel();
		panel_23.add(panel_30);

		JPanel panel_21 = new JPanel();
		FlowLayout flowLayout_23 = (FlowLayout) panel_21.getLayout();
		flowLayout_23.setAlignment(FlowLayout.LEADING);
		panel_17.add(panel_21);

		JPanel panel_32 = new JPanel();
		panel_21.add(panel_32);

		JLabel lblPriority = new JLabel("2.");
		panel_21.add(lblPriority);
		jLabelList.add(lblPriority);

		// cb2 = new JComboBox();
		// panel_21.add(cb2);
		jPanelList.add(panel_21);

		JPanel panel_28 = new JPanel();
		FlowLayout flowLayout_22 = (FlowLayout) panel_28.getLayout();
		flowLayout_22.setAlignment(FlowLayout.TRAILING);
		panel_17.add(panel_28);

		JLabel label_1 = new JLabel("3.");
		panel_28.add(label_1);
		jLabelList.add(label_1);

		// cb3 = new JComboBox();
		// panel_28.add(cb3);
		jPanelList.add(panel_28);

		JPanel panel_31 = new JPanel();
		panel_28.add(panel_31);

		JPanel panel_29 = new JPanel();
		FlowLayout flowLayout_24 = (FlowLayout) panel_29.getLayout();
		flowLayout_24.setAlignment(FlowLayout.LEADING);
		panel_17.add(panel_29);

		JPanel panel_33 = new JPanel();
		panel_29.add(panel_33);

		JLabel label_2 = new JLabel("4.");
		panel_29.add(label_2);
		jLabelList.add(label_2);

		// cb4 = new JComboBox();
		// panel_29.add(cb4);
		jPanelList.add(panel_29);

		setComboBoxItem();

		txtMinAge.setHorizontalAlignment(JTextField.CENTER);
		txtMaxPlayerAge.setHorizontalAlignment(JTextField.CENTER);
		txtWinScore.setHorizontalAlignment(JTextField.CENTER);
		txtTiredScore.setHorizontalAlignment(JTextField.CENTER);
		txtDefeatScore.setHorizontalAlignment(JTextField.CENTER);
		txtMinNumOfPlayers.setHorizontalAlignment(JTextField.CENTER);
		txtMaxNumOfPlayer.setHorizontalAlignment(JTextField.CENTER);
		txtMaxAbroadPlayer.setHorizontalAlignment(JTextField.CENTER);
		txtNumOfScored.setHorizontalAlignment(JTextField.CENTER);
		txtMaxTimeScored.setHorizontalAlignment(JTextField.CENTER);

		setEditable(false);
		loadData();
	}

	private void setComboBoxItem() {
		try {
			cbList = new Vector<JComboBox<String>>();
			priorityList = DBPriority.getAllPriority(DBConnector.getInstance());
			Regulation.priorityList = priorityList;
			for (int i = 0; i < priorityList.size(); i++) {
				cbList.add(new JComboBox<String>());

				jPanelList.get(i).removeAll();
				jPanelList.get(i).revalidate();
				jPanelList.get(i).repaint();
				jPanelList.get(i).add(new JPanel());
				jPanelList.get(i).add(jLabelList.get(i));
				jPanelList.get(i).add(cbList.get(i));
				jPanelList.get(i).add(new JPanel());

			}
			for (int i = 0; i < priorityList.size(); i++) {
				for (int j = 0; j < priorityList.size(); j++) {
					cbList.get(i).addItem(priorityList.get(j).getName());
				}

				cbList.get(i).setSelectedIndex(i);
			}
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

	}

	private void setEditable(boolean b) {
		txtMinAge.setEnabled(b);
		txtMaxPlayerAge.setEnabled(b);
		txtWinScore.setEnabled(b);
		txtTiredScore.setEnabled(b);
		txtDefeatScore.setEnabled(b);
		txtMinNumOfPlayers.setEnabled(b);
		txtMaxNumOfPlayer.setEnabled(b);
		txtMaxAbroadPlayer.setEnabled(b);
		txtNumOfScored.setEnabled(b);
		txtMaxTimeScored.setEnabled(b);
		for (int i = 0; i < cbList.size(); i++) {
			cbList.get(i).setEnabled(b);
		}
	}

	public void loadData() {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Regulation.regulationList = DBRegulationList
							.getAllRegulation(DBConnector.getInstance());
					txtMinAge.setText(Regulation.regulationList.get(0).getValue());
					txtMaxPlayerAge.setText(Regulation.regulationList.get(1).getValue());
					txtMinNumOfPlayers.setText(Regulation.regulationList.get(2).getValue());
					txtMaxNumOfPlayer.setText(Regulation.regulationList.get(3).getValue());
					txtMaxAbroadPlayer.setText(Regulation.regulationList.get(4).getValue());
					txtMaxTimeScored.setText(Regulation.regulationList.get(5).getValue());
					txtWinScore.setText(Regulation.regulationList.get(6).getValue());
					txtTiredScore.setText(Regulation.regulationList.get(7).getValue());
					txtDefeatScore.setText(Regulation.regulationList.get(8).getValue());
					txtNumOfScored.setText(Regulation.regulationList.get(9).getValue());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		t.start();
	}

	public void getDataToSave() {
		Regulation.regulationList.get(0).setValue(txtMinAge.getText());
		Regulation.regulationList.get(1).setValue(txtMaxPlayerAge.getText());
		Regulation.regulationList.get(2).setValue(txtMinNumOfPlayers.getText());
		Regulation.regulationList.get(3).setValue(txtMaxNumOfPlayer.getText());
		Regulation.regulationList.get(4).setValue(txtMaxAbroadPlayer.getText());
		Regulation.regulationList.get(5).setValue(txtMaxTimeScored.getText());
		Regulation.regulationList.get(6).setValue(txtWinScore.getText());
		Regulation.regulationList.get(7).setValue(txtTiredScore.getText());
		Regulation.regulationList.get(8).setValue(txtDefeatScore.getText());
		Regulation.regulationList.get(9).setValue(txtNumOfScored.getText());
	}

	private boolean canSave() {
		for (int i = 0; i < cbList.size(); i++) {
			if (cbList.get(i).getSelectedIndex() == -1) {
				return false;
			}
			for (int j = i + 1; j < cbList.size(); j++) {
				if (cbList.get(i).getSelectedIndex() == cbList.get(j).getSelectedIndex()) {
					return false;
				}
			}

		}
		return true;
	}

	private void changePriority() {
		try {
			for (int i = 0; i < jPanelList.size(); i++) {
				// if(priorityList.get(i).getOrderPriority() != i){
				// priorityList.get(i).setOrderPriority();
				priorityList.get(cbList.get(i).getSelectedIndex()).setOrderPriority(i);
				// System.out.println(cbList.get(i).getSelectedItem());
				// }
				DBPriority.changePriority(DBConnector.getInstance(),
						priorityList.get(cbList.get(i).getSelectedIndex()).getId(), i);

			}
			Regulation.priorityList = priorityList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean checkValid() {
		if (Integer.parseInt(txtWinScore.getText()) > Integer.parseInt(txtTiredScore.getText()) &&
				Integer.parseInt(txtTiredScore.getText()) > Integer.parseInt(txtDefeatScore.getText())) {

			return true;
		}
		JOptionPane.showMessageDialog(null,
				"Ràng buộc: Điểm thắng > Điểm hòa > Điểm thua");
		return false;
	}
}