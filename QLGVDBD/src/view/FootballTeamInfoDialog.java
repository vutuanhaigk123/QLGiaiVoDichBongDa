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
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JTextField;

import viewmodel.FootballTeamProfileTable;

@SuppressWarnings("serial")
public class FootballTeamInfoDialog extends JDialog {

	@Override
	public void dispose() {
		if(btnSave.isVisible()){
    		int dialogResult = JOptionPane.showConfirmDialog (null, 
					"Data have been changed. Do you want to save changes?", 
					"Do you want to save changes?", 
					JOptionPane.YES_NO_CANCEL_OPTION);
    		if(dialogResult == JOptionPane.CANCEL_OPTION){
				return;
			}
    		else if(dialogResult == JOptionPane.YES_OPTION){
				System.out.println("Saved Successful");
			}
    	}
		btnSave.setVisible(false);
		super.dispose();
	}

	private final JPanel contentPanel = new JPanel();
	private JTextField txtTeamName;
	private JTextField txtStadium;
	private JButton btnAdd, btnSave, btnEdit;
	private FootballTeamProfileTable customTableModel;
	private TablePanel tablePanel;

	public FootballTeamInfoDialog() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("<Team> Profile");
		setBounds(100, 100, 626, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
//		this.addWindowListener(new WindowAdapter() {
//		    @Override
//			public void windowClosing(WindowEvent e) {
//				// TODO Auto-generated method stub
//		    	super.windowClosing(e);
//				
//				
//			}
//
//			@Override
//		    public void windowClosed(WindowEvent e) {
//		    	
//		    }
//		});
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
					btnAdd.setFocusPainted( false );
					panel_1.add(btnAdd);
				}
				{
					btnSave = new JButton(new ImageIcon("resources/save.png"));
					btnSave.setToolTipText("Save changes");
					btnSave.setFocusPainted( false );
					panel_1.add(btnSave);
				}
				btnEdit = new JButton("Edit", new ImageIcon("resources/edit.png"));
				btnEdit.setToolTipText("Edit Football team information");
				btnEdit.setFocusPainted( false );
				panel_1.add(btnEdit);
				btnAdd.setVisible(false);
				btnSave.setVisible(false);
			}
		}
		{
			tablePanel = new TablePanel(TablePanel.FOOTBALL_TEAM_PROFILE_TABLE);
			contentPanel.add(tablePanel, BorderLayout.CENTER);
			customTableModel = ((FootballTeamProfileTable)tablePanel.getTblModel());
			customTableModel.setEnable(false);
			txtTeamName.setEnabled(false);
			txtStadium.setEnabled(false);
			
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
					customTableModel.setEnable(false);	
					btnEdit.setVisible(true);
					btnAdd.setVisible(false);
					btnSave.setVisible(false);
					txtTeamName.setEnabled(false);
					txtStadium.setEnabled(false);
					HashMap<String, Integer> idList = customTableModel.getIdList();
					JTable table = tablePanel.getTable();
					for (int i = 0; i < table.getRowCount(); i++) {
						if(!isEmptyRow(i)){
							String typeOfPlayerStr = (String) table.getValueAt(i, 3);
							System.out.println(table.getValueAt(i, 1) + " "
									+ table.getValueAt(i, 2) + " ;"
									+ table.getValueAt(i, 3) + "; "
									+ idList.get(typeOfPlayerStr));
						}
						
					}
				}
			});
			btnEdit.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					customTableModel.setEnable(true);
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
	
	private boolean isEmptyRow(int i){
		JTable table = tablePanel.getTable();
		if(table.getValueAt(i, 1).toString().trim().isEmpty() 
				|| table.getValueAt(i, 3).toString().trim().isEmpty()
				|| table.getValueAt(i, 2).toString().trim().isEmpty()){
			return true;
		}
		return false;
	}

}
