package view;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JButton;

import model.Match_Schedule;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import viewmodel.CompetitionScheduleTable;
import viewmodel.MatchResultTable;

@SuppressWarnings("serial")
public class MatchResultDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JButton btnAdd, btnSave, btnEdit, btnDelete;
	private Match_Schedule ms; 
	private JTextField txtFTeamScore;
	private JTextField txtSTeamScore;
	private TablePanel tablePanel;

	public MatchResultDialog(Match_Schedule ms) {
		this.ms = ms;
		setTitle("Kết quả trận đấu " + ms.getFirstTeam().getName() 
				+ " - " + ms.getSecondTeam().getName());
		setBounds(100, 100, 704, 350);
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
						JLabel lblNewLabel_1 = new JLabel(ms.getFirstTeam().getName());
						panel_2.add(lblNewLabel_1);
					}
				}
				{
					JPanel panel_2 = new JPanel();
					panel_1.add(panel_2);
					{
						txtFTeamScore = new JTextField();
						panel_2.add(txtFTeamScore);
						txtFTeamScore.setColumns(2);
					}
					{
						JLabel label = new JLabel("-");
						panel_2.add(label);
					}
					{
						txtSTeamScore = new JTextField();
						panel_2.add(txtSTeamScore);
						txtSTeamScore.setColumns(2);
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
						JLabel label = new JLabel(ms.getSecondTeam().getName() );
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
						JLabel label = new JLabel(ms.getStadium());
						panel_2.add(label);
					}
				}
				String[] time = ms.getTime()
						.toString().split("T");
				{
					JPanel panel_2 = new JPanel();
					panel_1.add(panel_2);
					{
						JLabel lblNgy = new JLabel("Ngày:");
						panel_2.add(lblNgy);
					}
					{
						JLabel label = new JLabel(time[0]);
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
						JLabel label = new JLabel(time[1]);
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
					btnAdd.setFocusPainted(false);
					panel_1.add(btnAdd);
				}
				{
					btnSave = new JButton(new ImageIcon("resources/save.png"));
					btnSave.setToolTipText("Save changes");
					btnSave.setFocusPainted(false);
					panel_1.add(btnSave);
				}
				{
					btnEdit = new JButton("Edit",
							new ImageIcon("resources/edit.png"));
					btnEdit.setToolTipText("Edit result match");
					btnEdit.setFocusPainted(false);
					panel_1.add(btnEdit);
				}
				{
					btnDelete = new JButton(new ImageIcon("resources/delete.png"));
					btnDelete.setToolTipText("Delete result match detail");
					btnDelete.setFocusPainted(false);
					panel_1.add(btnDelete);

					if(ms.getMatchResult() == null ){
						btnDelete.setVisible(false);
					}
				}
			}
		}
		{
			tablePanel = new TablePanel(TablePanel.MATCH_RESULT_TABLE);
			contentPanel.add(tablePanel, BorderLayout.CENTER);
		}
		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setEnable(false);
			}
		});
		btnEdit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setEnable(true);
			}
		});
		((MatchResultTable)tablePanel.getTblModel())
			.showData(ms.getMatchResult(), ms, txtFTeamScore, txtSTeamScore);
		setEnable(false);
		setLocationRelativeTo(null);
		
		btnAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				((MatchResultTable)tablePanel.getTblModel()).addEmptyRow(1);
			}
		});
		
		btnSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(checkValid()){
					((MatchResultTable)tablePanel.getTblModel())
					.saveData(
							Integer.parseInt(txtFTeamScore.getText()), 
							Integer.parseInt(txtSTeamScore.getText()));

					tablePanel.revalidate();
					tablePanel.repaint();
				}
			}
		});
		btnDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				((MatchResultTable)tablePanel.getTblModel()).deleteResultDetail();
				ms.setMatchResult(null);
				dispose();
			}
		});
	
	}

	private boolean checkValid() {
		String s = ((MatchResultTable)tablePanel.getTblModel()).isValid();
		if(txtFTeamScore.getText().trim().length() == 0 ||
				txtSTeamScore.getText().trim().length() == 0){
			return false;
		}
		if(s != null){
			JOptionPane.showMessageDialog(null, s);
			return false;
		}
		return true;
	}
	
	public void setEnable(boolean b){
		((MatchResultTable)tablePanel.getTblModel()).setEnable(b);
		if(b){
			btnEdit.setVisible(false);
			btnAdd.setVisible(true);
			btnSave.setVisible(true);
			if(txtFTeamScore.getText().equals("?")
					|| txtSTeamScore.getText().equals("?")){
				txtFTeamScore.setText("");
				txtSTeamScore.setText("");
			}
			txtFTeamScore.setEnabled(true);
			txtSTeamScore.setEnabled(true);
		}
		else{
			btnEdit.setVisible(true);
			btnAdd.setVisible(false);
			btnSave.setVisible(false);
			if(txtFTeamScore.getText().trim().length() == 0
					|| txtSTeamScore.getText().trim().length() == 0){
				txtFTeamScore.setText("?");
				txtSTeamScore.setText("?");
			}
			txtFTeamScore.setEnabled(false);
			txtSTeamScore.setEnabled(false);
		}
	}
}
