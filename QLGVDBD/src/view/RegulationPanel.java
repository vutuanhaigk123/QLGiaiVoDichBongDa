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

import javax.swing.JTextField;

import database.DBConnector;
import database.DBRegulationList;
import model.Regulation;

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

	/**
	 * Create the panel.
	 */
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
				if (!txtMinAge.getText().chars().allMatch(Character::isDigit) || !txtMaxPlayerAge.getText().chars().allMatch(Character::isDigit)
						|| !txtWinScore.getText().chars().allMatch(Character::isDigit) || !txtTiredScore.getText().chars().allMatch(Character::isDigit) ||
						!txtDefeatScore.getText().chars().allMatch(Character::isDigit) || !txtMinNumOfPlayers.getText().chars().allMatch(Character::isDigit)
						|| !txtMaxNumOfPlayer.getText().chars().allMatch(Character::isDigit) || !txtMaxAbroadPlayer.getText().chars().allMatch(Character::isDigit) ||
						!txtNumOfScored.getText().chars().allMatch(Character::isDigit) || !txtMaxTimeScored.getText().chars().allMatch(Character::isDigit))
					JOptionPane.showMessageDialog(null, "Thông tin không hợp lệ", "Lỗi",
							JOptionPane.ERROR_MESSAGE);
				else {
					try {
						getDataToSave();
						DBRegulationList.saveData(DBConnector.getInstance(), Regulation.regulationList);
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

		JLabel lblThTu = new JLabel("Thứ tự ưu tiên khi xếp hạng:");
		panel_18.add(lblThTu);

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

	public void getDataToSave(){
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
}