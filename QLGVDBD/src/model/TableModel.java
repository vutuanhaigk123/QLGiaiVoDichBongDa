package model;

import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public abstract class TableModel {

	protected DefaultTableModel dtm;
	protected JTable table;

	public TableModel(){
		dtm = new DefaultTableModel();
		table = new JTable(dtm);		
	}
	
	@SuppressWarnings("serial")
	public void bindingDeleteBtn(String msg){
		Action delete = new AbstractAction()
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				int dialogResult = JOptionPane.showConfirmDialog (null, 
						msg, "Delete", 2);
				if(dialogResult == JOptionPane.YES_OPTION){
					JTable table = (JTable)e.getSource();
					int modelRow = Integer.valueOf( e.getActionCommand() );
					((DefaultTableModel)table.getModel()).removeRow(modelRow);
					int startCheckIndex = modelRow + 1;
					for(int i = modelRow; i < table.getRowCount(); i++){
						table.setValueAt(startCheckIndex, i, 0);
						startCheckIndex++;
					}
				}				
			}
		};

		new ButtonColumn(table, delete, table.getColumnCount() - 1);
	}

	public DefaultTableModel getDtm() {
		return dtm;
	}

	public JTable getTable() {
		return table;
	}
	
	public void addEmptyRow(int quantity){
		for(int j = 0; j < quantity; j++){
			Vector<Object> row = new Vector<>();
			int n = table.getColumnCount() - 1;
			row.add((table.getRowCount() + 1) + "");
			for(int i = 1; i < n; i++){
				if(i == n){
					row.add(new ImageIcon("/imgs/blank.png"));
				}
				else{
					row.add("");
				}
			}
			dtm.addRow(row);
		}
		
	}

}
