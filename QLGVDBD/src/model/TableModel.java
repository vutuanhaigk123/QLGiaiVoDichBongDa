package model;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
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

}
