package view;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import viewmodel.FootballTeamListTable;
import model.Team;


@SuppressWarnings("serial")
public class FootballTeamPanel extends JPanel
implements ActionListener{

	private TablePanel tablePanel;
	private JButton btnInfo, btnAdd, btnEdit, btnSaveChanges;

	public FootballTeamPanel() {
		addControls();
	}

	public void addControls(){
		setLayout(new GridLayout(1, 0, 0, 50));

		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));

		tablePanel = new TablePanel(TablePanel.FOOTBALL_TEAM_LIST_TABLE);
		panel.add(tablePanel);

		JPanel panel_3 = new JPanel();
		tablePanel.add(panel_3, BorderLayout.NORTH);
		FlowLayout flowLayout = (FlowLayout) panel_3.getLayout();
		flowLayout.setAlignment(FlowLayout.TRAILING);

		btnInfo = new JButton(new ImageIcon("resources/info.png"));
		panel_3.add(btnInfo);
		btnInfo.setFocusPainted( false );
		btnInfo.setToolTipText("Information");
		btnInfo.setVisible(false);

		btnSaveChanges = new JButton(new ImageIcon("resources/save.png"));
		panel_3.add(btnSaveChanges);
		btnSaveChanges.setFocusPainted( false );
		btnSaveChanges.setToolTipText("Save changes");

		btnAdd = new JButton(new ImageIcon("resources/add.png"));
		panel_3.add(btnAdd);
		btnAdd.setFocusPainted( false );
		btnAdd.setToolTipText("Register a football team");			

		btnEdit = new JButton("Edit",
				new ImageIcon("resources/edit.png"));
		panel_3.add(btnEdit);
		btnEdit.setFocusPainted( false );
		btnEdit.setToolTipText("Edit teams");
		

		btnInfo.addActionListener(this);
		btnAdd.addActionListener(this);
		btnEdit.addActionListener(this);
		btnSaveChanges.addActionListener(this);
		
		setEnable(false);

		tablePanel.getDtm().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {				
				int selectedRow = tablePanel.getTable().getSelectedRowCount();
				if(selectedRow == 1){
					btnInfo.setVisible(true);
				}
				else {
					btnInfo.setVisible(false);
				}
			}
		});

	}
	
	synchronized public void setEnable(boolean b){
		btnEdit.setVisible(!b);
		btnSaveChanges.setVisible(b);
		btnAdd.setVisible(b);
		FootballTeamListTable footballTeamListModel = 
				(FootballTeamListTable)tablePanel.getTblModel();
		footballTeamListModel.setEnable(b);
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		Team team = (Team)(tablePanel.getTblModel().getSelectedItem());
		if((JButton)e.getSource() == btnInfo){
			FootballTeamInfoDialog f = new FootballTeamInfoDialog(team,
					(FootballTeamListTable)tablePanel.getTblModel());
//			System.out.println(team == null);
			f.setModal(true);
			f.setVisible(true);
		}
		else if((JButton)e.getSource() == btnAdd){
			tablePanel.addEmptyRow(1);
			int last = tablePanel.getTable().getRowCount() - 1;
			tablePanel.getTable().setRowSelectionInterval(last, last);
			team = (Team)(tablePanel.getTblModel().getSelectedItem());
			FootballTeamInfoDialog f = new FootballTeamInfoDialog(team, 
					(FootballTeamListTable)tablePanel.getTblModel());
			f.setModal(true);
			f.setVisible(true);
		}
		else if((JButton)e.getSource() == btnEdit){
			
			setEnable(true);
		}
		else if((JButton)e.getSource() == btnSaveChanges){
			setEnable(false);
			((FootballTeamListTable)tablePanel.getTblModel()).saveData();
			
		}
		
	}

}
