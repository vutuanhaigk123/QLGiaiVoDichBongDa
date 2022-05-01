package view;

import java.awt.*;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import model.Team;
import viewmodel.FootballTeamListTable;
import viewmodel.FootballTeamProfileTable;
import viewmodel.UpdateTabData;

@SuppressWarnings("serial")
public class FootballTeamInfoDialog extends JDialog {

	@Override
	public void dispose() {
		if (!needUpdateData) {
			return;
		}
		setNeedUpdateData(false);
		if (btnSave.isVisible()) {
			if (!isTeamInfoEmpty()) {
				int dialogResult = JOptionPane.showConfirmDialog(null,
						"Data have been changed. Do you want to save changes?",
						"Do you want to save changes?",
						JOptionPane.YES_NO_CANCEL_OPTION);
				if (dialogResult == JOptionPane.CANCEL_OPTION) {
					return;
				} else if (dialogResult == JOptionPane.YES_OPTION) {
					if (checkValid()) {
						customTableModel.saveData(txtTeamName.getText(),
								txtStadium.getText(), tableModelTeamList);
						System.out.println("Saved Successful");
					} else {

						return;
					}

				}
			}

		}
		customTableModel.clearNullPlayer();
		updateTableModelTeamList();
		btnSave.setVisible(false);
		super.dispose();
	}

	private boolean checkValid() {
		String msg = customTableModel.isValid();
		if (msg != null) {
			JOptionPane.showMessageDialog(null, msg);
			return false;
		}
		return true;
	}

	private boolean needUpdateData = true;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtTeamName, txtStadium;
	private JButton btnAdd, btnSave, btnEdit;
	private FootballTeamProfileTable customTableModel;
	private TablePanel tablePanel;
	private FootballTeamListTable tableModelTeamList;
	private Team team;

	public FootballTeamInfoDialog(Team team, FootballTeamListTable tableModelTeamList) {
		this.tableModelTeamList = tableModelTeamList;
		addControls(team);
	}

	synchronized public void setNeedUpdateData(boolean b) {
		needUpdateData = b;
	}

	private void addControls(Team t) {
		// System.out.println("is null: " + t == null);
		this.team = t;
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Team Profile");
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
				panel_1.setLayout(new GridLayout(1, 0, 0, 0));
				{
					JPanel panel_2 = new JPanel();
					panel_1.add(panel_2);
					{
						JLabel lblFootballTeamName = new JLabel("Football team name:");
						panel_2.add(lblFootballTeamName);
						lblFootballTeamName.setHorizontalAlignment(SwingConstants.TRAILING);
					}
					{
						txtTeamName = new JTextField();
						panel_2.add(txtTeamName);
						txtTeamName.setColumns(10);
					}
				}
				{
					JPanel panel_2 = new JPanel();
					panel_1.add(panel_2);
					{
						JLabel lblNewLabel = new JLabel("Home stadium:");
						panel_2.add(lblNewLabel);
					}
					{
						txtStadium = new JTextField();
						panel_2.add(txtStadium);
						txtStadium.setColumns(10);
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
					btnAdd.setToolTipText("Add a new player");
					btnAdd.setFocusPainted(false);
					panel_1.add(btnAdd);
				}
				{
					btnSave = new JButton(new ImageIcon("resources/save.png"));
					btnSave.setToolTipText("Save changes");
					btnSave.setFocusPainted(false);
					panel_1.add(btnSave);
				}
				btnEdit = new JButton("Edit", new ImageIcon("resources/edit.png"));
				btnEdit.setToolTipText("Edit Football team information");
				btnEdit.setFocusPainted(false);
				panel_1.add(btnEdit);
				btnAdd.setVisible(false);
				btnSave.setVisible(false);
			}
		}
		{
			tablePanel = new TablePanel(TablePanel.FOOTBALL_TEAM_PROFILE_TABLE);
			contentPanel.add(tablePanel, BorderLayout.CENTER);
			customTableModel = ((FootballTeamProfileTable) tablePanel.getTblModel());
			customTableModel.setTeam(t);
			customTableModel.getData();
			customTableModel.setEnable(false);
			txtTeamName.setEnabled(false);
			txtStadium.setEnabled(false);
			if (team != null) {
				txtTeamName.setText(team.getName());
				txtStadium.setText(team.getHome_stadium());
			}

			btnAdd.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					tablePanel.addEmptyRow(1);
					int lastIndex = tablePanel.getTable().getRowCount() - 1;
					tablePanel.getTable().setRowSelectionInterval(lastIndex, lastIndex);
					tablePanel.scrollToEnd();
				}
			});
			btnSave.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (isTeamInfoEmpty()) {
						JOptionPane.showMessageDialog(null,
								"Vui lòng không để trống trường thông tin nào");
						return;
					}
					if (!checkValid()) {
						return;
					}
					customTableModel.setEnable(false);
					customTableModel.changeDateAfterEdit();
					btnEdit.setVisible(true);
					btnAdd.setVisible(false);
					btnSave.setVisible(false);
					txtTeamName.setEnabled(false);
					txtStadium.setEnabled(false);
					// HashMap<String, Integer> idList = customTableModel.getIdList();
					saveTeam();
					// JTable table = tablePanel.getTable();
					// for (int i = 0; i < table.getRowCount(); i++) {
					// if(!isEmptyRow(i)){
					// String typeOfPlayerStr = (String) table.getValueAt(i, 3);
					// System.out.println(table.getValueAt(i, 1) + " "
					// + table.getValueAt(i, 2) + " ;"
					// + table.getValueAt(i, 3) + "; "
					// + idList.get(typeOfPlayerStr));
					// }
					//
					// }
					((ReportPanel) (UpdateTabData.panelList
							.get(StartProgram.REPORT_TAB))).getLeaderboardPanel();
					((ReportPanel) (UpdateTabData.panelList
							.get(StartProgram.REPORT_TAB))).getPlayerListPanel();
				}
			});
			btnEdit.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					customTableModel.setEnable(true);
					customTableModel.changeDateAfterEdit();
					btnAdd.setVisible(true);
					btnSave.setVisible(true);
					btnEdit.setVisible(false);
					txtTeamName.setEnabled(true);
					txtStadium.setEnabled(true);

				}
			});

		}
		setLocationRelativeTo(null);
	}

	private boolean isEmptyRow(int i) {
		JTable table = tablePanel.getTable();
		if (table.getValueAt(i, 1).toString().trim().isEmpty()
				|| table.getValueAt(i, 3).toString().isEmpty()
				|| tablePanel.getTable().getValueAt(i, 2) == null) {
			return true;
		}
		return false;
	}

	private void updateTableModelTeamList() {
		int selectedIndex = tableModelTeamList.getTable().getSelectedRow();

		if (team == null && customTableModel.getTeamOriginal() == null) {
			tableModelTeamList.getDtm().removeRow(
					selectedIndex);
			// System.out.println("229: null FootbalTeamInfoDialog");
		} else {
			team = customTableModel.getTeamOriginal();
			tableModelTeamList.getDtm().setValueAt(
					team.getName(), selectedIndex, 1);
			tableModelTeamList.getDtm().setValueAt(
					team.getHome_stadium(), selectedIndex, 2);
			// System.out.println("237: FootbalTeamInfoDialog");
		}
	}

	private void saveTeam() {
		customTableModel.saveData(txtTeamName.getText(),
				txtStadium.getText(),
				tableModelTeamList);
	}

	private boolean isTeamInfoEmpty() {
		validInput();
		if (txtStadium.getText().length() <= 0 ||
				txtTeamName.getText().length() <= 0 ||
				isPlayerInfoEmpty()) {
			return true;
		}
		return false;
	}

	private boolean isPlayerInfoEmpty() {
		int last = tablePanel.getTable().getRowCount();
		for (int i = 0; i < last; i++) {
			if (isEmptyRow(i)) {
				return true;
			}
		}
		return false;
	}

	private void validInput() {
		txtStadium.setText(txtStadium.getText().toString().trim());
		txtTeamName.setText(txtTeamName.getText().toString().trim());
		for (int i = 0; i < tablePanel.getTable().getRowCount(); i++) {
			tablePanel.getTable().setValueAt(
					tablePanel.getTable().getValueAt(i, 1).toString().trim(),
					i, 1);
			tablePanel.getTable().setValueAt(
					tablePanel.getTable().getValueAt(i, 4).toString().trim(),
					i, 4);
		}
	}
}
