package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import viewmodel.CompetitionScheduleTable;

@SuppressWarnings("serial")
public class CompetitionMatchPanel extends JPanel 
			implements ActionListener{

	private JButton btnInfo, btnSaveChanges, btnAdd, btnEdit;
	private CompetitionScheduleTable customTableModel;
	private TablePanel tablePanel;
	
	public CompetitionMatchPanel() {
		setLayout(new GridLayout(1, 0, 0, 50));

		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));

		tablePanel = new TablePanel(TablePanel.COMPETITION_SCHEDULE_TABLE);
		panel.add(tablePanel);
		customTableModel = ((CompetitionScheduleTable)tablePanel.getTblModel());

		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		tablePanel.add(panel_2, BorderLayout.NORTH);
		
		JPanel panel_21 = new JPanel();
		FlowLayout flowLayout1 = (FlowLayout) panel_21.getLayout();
		flowLayout1.setAlignment(FlowLayout.RIGHT);
		tablePanel.add(panel_21, BorderLayout.NORTH);

		btnSaveChanges = new JButton(new ImageIcon("resources/save.png"));
		btnSaveChanges.setFocusPainted( false );
		btnSaveChanges.setToolTipText("Save changes");
		btnSaveChanges.addActionListener(this);

		btnAdd = new JButton(new ImageIcon("resources/add.png"));
		btnAdd.setFocusPainted( false );
		btnAdd.setToolTipText("Add a new competition match");
		btnAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tablePanel.addEmptyRow(1);
				int lastIndex = tablePanel.getTable().getRowCount() - 1;
				tablePanel.getTable().setRowSelectionInterval(lastIndex, lastIndex);
				tablePanel.scrollToEnd();
			}
		});
		
		btnInfo = new JButton(new ImageIcon("resources/trophy.png"));
		btnInfo.setFocusPainted( false );
		btnInfo.setToolTipText("Add result match");
		btnInfo.addActionListener(this);
		btnInfo.setVisible(false);
		
		btnEdit = new JButton("Edit", new ImageIcon("resources/edit.png"));
		btnEdit.setFocusPainted( false );
		btnEdit.setToolTipText("Edit this match");
		btnEdit.addActionListener(this);

		panel_21.add(btnInfo);
		panel_21.add(btnSaveChanges);
		panel_21.add(btnAdd);
		panel_21.add(btnEdit);
		clickSave();
		
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if((JButton)e.getSource() == btnInfo){
			MatchResultDialog f = new MatchResultDialog();
			f.setModal(true);
			f.setVisible(true);
		}
		else if((JButton)e.getSource() == btnEdit){
			clickEdit();
		}
		else if((JButton)e.getSource() == btnSaveChanges){
			clickSave();
			int selectedRowIndex = tablePanel.getTable().getSelectedRow();
			for(int i = 0;  i < tablePanel.getTable().getColumnCount() - 1; i++){
				System.out.println(i + ": " + tablePanel.getDtm().getValueAt(selectedRowIndex, i));
			}
		}
		else if((JButton)e.getSource() == btnAdd){
			tablePanel.addEmptyRow(1);
		}
	}
	
	public void clickEdit(){
		customTableModel.setEnable(true);
		btnEdit.setVisible(false);
		btnSaveChanges.setVisible(true);
		btnAdd.setVisible(true);
	}
	
	public void clickSave(){
		customTableModel.setEnable(false);
		btnEdit.setVisible(true);
		btnSaveChanges.setVisible(false);
		btnAdd.setVisible(false);
	}

}
