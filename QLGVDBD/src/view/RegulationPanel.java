package view;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.BoxLayout;

import java.awt.BorderLayout;

import javax.swing.JButton;

import java.awt.FlowLayout;

import javax.swing.JLabel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class RegulationPanel extends JPanel {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JButton btnEdit, btnSave;

	/**
	 * Create the panel.
	 */
	public RegulationPanel() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.TRAILING);
		add(panel, BorderLayout.NORTH);
		
		btnEdit = new JButton("Edit regulations");
		btnEdit.setIcon(new ImageIcon("resources/edit.png"));
		btnEdit.setFocusPainted( false );
		btnEdit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				btnEdit.setVisible(false);
				btnSave.setVisible(true);
				setEditable(true);
			}
		});
		panel.add(btnEdit);
		
		btnSave = new JButton("Save changes");
		btnSave.setIcon(new ImageIcon("resources/save.png"));
		btnSave.setFocusPainted( false );
		btnSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				btnSave.setVisible(false);
				btnEdit.setVisible(true);
				setEditable(false);
			}
		});
		panel.add(btnSave);
		btnSave.setVisible(false);
		
		JPanel panel_16 = new JPanel();
		add(panel_16, BorderLayout.CENTER);
		panel_16.setLayout(new BoxLayout(panel_16, BoxLayout.Y_AXIS));
		
		JPanel panel_1 = new JPanel();
		panel_16.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 4, 0, 0));
		
		JPanel panel_3 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_3.getLayout();
		flowLayout_1.setAlignment(FlowLayout.TRAILING);
		panel_1.add(panel_3);
		
		JLabel lblNewLabel = new JLabel("Tuổi tối thiểu của cầu thủ:");
		panel_3.add(lblNewLabel);
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout_8 = (FlowLayout) panel_2.getLayout();
		flowLayout_8.setAlignment(FlowLayout.LEADING);
		panel_1.add(panel_2);
		
		textField = new JTextField();
		panel_2.add(textField);
		textField.setColumns(10);
		
		JPanel panel_4 = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panel_4.getLayout();
		flowLayout_2.setAlignment(FlowLayout.TRAILING);
		panel_1.add(panel_4);
		
		JLabel lblSLngCu_1 = new JLabel("Số lượng cầu thủ tối đa:");
		panel_4.add(lblSLngCu_1);
		
		JPanel panel_5 = new JPanel();
		FlowLayout flowLayout_9 = (FlowLayout) panel_5.getLayout();
		flowLayout_9.setAlignment(FlowLayout.LEADING);
		panel_1.add(panel_5);
		
		textField_6 = new JTextField();
		panel_5.add(textField_6);
		textField_6.setColumns(10);
		
		JPanel panel_6 = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) panel_6.getLayout();
		flowLayout_3.setAlignment(FlowLayout.TRAILING);
		panel_1.add(panel_6);
		
		JLabel lblSLngCu = new JLabel("Số lượng cầu thủ tối thiểu:");
		panel_6.add(lblSLngCu);
		
		JPanel panel_7 = new JPanel();
		FlowLayout flowLayout_10 = (FlowLayout) panel_7.getLayout();
		flowLayout_10.setAlignment(FlowLayout.LEADING);
		panel_1.add(panel_7);
		
		textField_5 = new JTextField();
		panel_7.add(textField_5);
		textField_5.setColumns(10);
		
		JPanel panel_8 = new JPanel();
		FlowLayout flowLayout_4 = (FlowLayout) panel_8.getLayout();
		flowLayout_4.setAlignment(FlowLayout.TRAILING);
		panel_1.add(panel_8);
		
		JLabel lblSLngCc = new JLabel("Số lượng các loại bàn thắng:");
		panel_8.add(lblSLngCc);
		
		JPanel panel_9 = new JPanel();
		FlowLayout flowLayout_11 = (FlowLayout) panel_9.getLayout();
		flowLayout_11.setAlignment(FlowLayout.LEADING);
		panel_1.add(panel_9);
		
		textField_8 = new JTextField();
		panel_9.add(textField_8);
		textField_8.setColumns(10);
		
		JPanel panel_10 = new JPanel();
		FlowLayout flowLayout_5 = (FlowLayout) panel_10.getLayout();
		flowLayout_5.setAlignment(FlowLayout.TRAILING);
		panel_1.add(panel_10);
		
		JLabel lblSCuTh = new JLabel("Số cầu thủ nước ngoài tối đa:");
		panel_10.add(lblSCuTh);
		
		JPanel panel_11 = new JPanel();
		FlowLayout flowLayout_12 = (FlowLayout) panel_11.getLayout();
		flowLayout_12.setAlignment(FlowLayout.LEADING);
		panel_1.add(panel_11);
		
		textField_7 = new JTextField();
		panel_11.add(textField_7);
		textField_7.setColumns(10);
		
		JPanel panel_12 = new JPanel();
		FlowLayout flowLayout_6 = (FlowLayout) panel_12.getLayout();
		flowLayout_6.setAlignment(FlowLayout.TRAILING);
		panel_1.add(panel_12);
		
		JLabel lblimThng = new JLabel("Điểm thắng:");
		panel_12.add(lblimThng);
		
		JPanel panel_13 = new JPanel();
		FlowLayout flowLayout_13 = (FlowLayout) panel_13.getLayout();
		flowLayout_13.setAlignment(FlowLayout.LEADING);
		panel_1.add(panel_13);
		
		textField_2 = new JTextField();
		panel_13.add(textField_2);
		textField_2.setColumns(10);
		
		JPanel panel_14 = new JPanel();
		FlowLayout flowLayout_7 = (FlowLayout) panel_14.getLayout();
		flowLayout_7.setAlignment(FlowLayout.TRAILING);
		panel_1.add(panel_14);
		
		JLabel lblThiimGhi = new JLabel("Thời điểm ghi bàn tối đa:");
		panel_14.add(lblThiimGhi);
		
		JPanel panel_15 = new JPanel();
		FlowLayout flowLayout_14 = (FlowLayout) panel_15.getLayout();
		flowLayout_14.setAlignment(FlowLayout.LEADING);
		panel_1.add(panel_15);
		
		textField_9 = new JTextField();
		panel_15.add(textField_9);
		textField_9.setColumns(10);
		
		JPanel panel_20 = new JPanel();
		FlowLayout flowLayout_16 = (FlowLayout) panel_20.getLayout();
		flowLayout_16.setAlignment(FlowLayout.TRAILING);
		panel_1.add(panel_20);
		
		JLabel lblimHo = new JLabel("Điểm hoà:");
		panel_20.add(lblimHo);
		
		JPanel panel_22 = new JPanel();
		FlowLayout flowLayout_18 = (FlowLayout) panel_22.getLayout();
		flowLayout_18.setAlignment(FlowLayout.LEADING);
		panel_1.add(panel_22);
		
		textField_3 = new JTextField();
		panel_22.add(textField_3);
		textField_3.setColumns(10);
		
		JPanel panel_24 = new JPanel();
		panel_1.add(panel_24);
		
		JLabel lblTuiTia = new JLabel("Tuổi tối đa của cầu thủ:");
		panel_24.add(lblTuiTia);
		
		JPanel panel_25 = new JPanel();
		FlowLayout flowLayout_15 = (FlowLayout) panel_25.getLayout();
		flowLayout_15.setAlignment(FlowLayout.LEADING);
		panel_1.add(panel_25);
		
		textField_1 = new JTextField();
		panel_25.add(textField_1);
		textField_1.setColumns(10);
		
		JPanel panel_26 = new JPanel();
		FlowLayout flowLayout_17 = (FlowLayout) panel_26.getLayout();
		flowLayout_17.setAlignment(FlowLayout.TRAILING);
		panel_1.add(panel_26);
		
		JLabel lblimThua = new JLabel("Điểm thua:");
		panel_26.add(lblimThua);
		
		JPanel panel_27 = new JPanel();
		FlowLayout flowLayout_19 = (FlowLayout) panel_27.getLayout();
		flowLayout_19.setAlignment(FlowLayout.LEADING);
		panel_1.add(panel_27);
		
		textField_4 = new JTextField();
		panel_27.add(textField_4);
		textField_4.setColumns(10);
		
		JPanel panel_18 = new JPanel();
		panel_16.add(panel_18);
		
		JLabel lblThTu = new JLabel("Thứ tự ưu tiên khi xếp hạng:");
		panel_18.add(lblThTu);
		setEditable(false);
	}
	
	private void setEditable(boolean b){
		textField.setEnabled(b);
		textField_1.setEnabled(b);
		textField_2.setEnabled(b);
		textField_3.setEnabled(b);
		textField_4.setEnabled(b);
		textField_5.setEnabled(b);
		textField_6.setEnabled(b);
		textField_7.setEnabled(b);
		textField_8.setEnabled(b);
		textField_9.setEnabled(b);
	}

}
