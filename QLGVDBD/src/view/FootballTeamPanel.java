package view;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class FootballTeamPanel extends JPanel
	implements ActionListener{

	private JButton btnInfo;
	
	public FootballTeamPanel() {
		setLayout(new GridLayout(1, 0, 0, 50));

		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));

		TablePanel panel_1 = new TablePanel(TablePanel.FOOTBALL_TEAM_LIST_TABLE);
		panel.add(panel_1);
										
										JPanel panel_3 = new JPanel();
										panel_1.add(panel_3, BorderLayout.NORTH);
										FlowLayout flowLayout = (FlowLayout) panel_3.getLayout();
										flowLayout.setAlignment(FlowLayout.TRAILING);
										
										btnInfo = new JButton(new ImageIcon("resources/info.png"));
										panel_3.add(btnInfo);
										btnInfo.setFocusPainted( false );
										btnInfo.setToolTipText("Information");
										
												JButton btnSaveChanges = new JButton(new ImageIcon("resources/save.png"));
												panel_3.add(btnSaveChanges);
												btnSaveChanges.setFocusPainted( false );
												btnSaveChanges.setToolTipText("Save changes");
												
														JButton btnRegister = new JButton(new ImageIcon("resources/add.png"));
														panel_3.add(btnRegister);
														btnRegister.setFocusPainted( false );
														btnRegister.setToolTipText("Register a football team");
		btnInfo.addActionListener(this);
		
		
		
		

	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if((JButton)e.getSource() == btnInfo){
			FootballTeamInfoDialog f = new FootballTeamInfoDialog();
			f.setModal(true);
			f.setVisible(true);
		}
	}

}
