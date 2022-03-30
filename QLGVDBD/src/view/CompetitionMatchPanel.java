package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CompetitionMatchPanel extends JPanel 
			implements ActionListener{

	private JButton btnInfo, btnSaveChanges, btnAdd;
	
	public CompetitionMatchPanel() {
		setLayout(new GridLayout(1, 0, 0, 50));

		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));

		TablePanel panel_1 = new TablePanel(TablePanel.COMPETITION_SCHEDULE_TABLE);
		panel.add(panel_1);

		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		panel_1.add(panel_2, BorderLayout.NORTH);
		
		JPanel panel_21 = new JPanel();
		FlowLayout flowLayout1 = (FlowLayout) panel_21.getLayout();
		flowLayout1.setAlignment(FlowLayout.RIGHT);
		panel_1.add(panel_21, BorderLayout.NORTH);

		btnSaveChanges = new JButton(new ImageIcon("resources/save.png"));
		btnSaveChanges.setFocusPainted( false );
		btnSaveChanges.setToolTipText("Save changes");

		btnAdd = new JButton(new ImageIcon("resources/add.png"));
		btnAdd.setFocusPainted( false );
		btnAdd.setToolTipText("Add a new competition match");
		
		btnInfo = new JButton(new ImageIcon("resources/trophy.png"));
		btnInfo.setFocusPainted( false );
		btnInfo.setToolTipText("Add result match");
		btnInfo.addActionListener(this);

		panel_21.add(btnInfo);
		panel_21.add(btnSaveChanges);
		panel_21.add(btnAdd);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		MatchResultDialog f = new MatchResultDialog();
		f.setModal(true);
		f.setVisible(true);
	}

}
