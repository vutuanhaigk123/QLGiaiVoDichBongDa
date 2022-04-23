package view;

import javax.swing.JPanel;
import javax.swing.JTable;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import viewmodel.CompetitionScheduleListTable;
import model.Round;

import java.util.Vector;

@SuppressWarnings("serial")
public class RoundListPanel extends JPanel{
	
	private TablePanel tablePanel;
	private JButton btnSave, btnInfo;
	
	public RoundListPanel() {
		setLayout(new BorderLayout(0, 0));
		
//		JPanel panel = new JPanel();
//		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
//		flowLayout.setAlignment(FlowLayout.TRAILING);
//		add(panel, BorderLayout.NORTH);
		
//		btnInfo = new JButton(new ImageIcon("resources/info.png"));
//		btnInfo.setHorizontalAlignment(SwingConstants.TRAILING);
//		panel.add(btnInfo);
		
//		btnSave = new JButton(new ImageIcon("resources/save.png"));
//		btnSave.setHorizontalAlignment(SwingConstants.TRAILING);
//		panel.add(btnSave);
		
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.CENTER);
		tablePanel = new TablePanel(TablePanel.ROUND_LIST_TABLE);
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));
		panel_1.add(tablePanel);
		
		
		
//		tablePanel.getDtm().addTableModelListener(new TableModelListener() {
//
//			@Override
//			public void tableChanged(TableModelEvent e) {				
//				int selectedRow = tablePanel.getTable().getSelectedRowCount();
//				if(selectedRow == 1){
//					btnInfo.setVisible(true);
//				}
//				else {
//					btnInfo.setVisible(false);
//				}
//			}
//		});
		
	}
	
	public Vector<Round> getRoundList(){
		return ((CompetitionScheduleListTable)tablePanel.getTblModel()).getRoundList();
	}
	
	public DefaultTableModel getDtm(){
		return tablePanel.getDtm();
	}
	
	public JTable getTable(){
		return tablePanel.getTable();
	}
	
	public void addEmptyRow(int num){
		tablePanel.addEmptyRow(num);
	}

	public CompetitionScheduleListTable getTblModel() {
		return (CompetitionScheduleListTable) tablePanel.getTblModel();
	}
	
}
