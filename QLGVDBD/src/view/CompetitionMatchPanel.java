package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import viewmodel.CompetitionScheduleListTable;
import viewmodel.CompetitionScheduleTable;

import javax.swing.JLabel;
import javax.swing.JTextField;

import model.Match_Schedule;
import model.Round;

@SuppressWarnings("serial")
public class CompetitionMatchPanel extends JPanel 
implements ActionListener{

	private JButton btnInfo, btnSaveChanges, btnAdd, btnEdit,
	btnRoundInfo, btnBack, btnEditRound, btnSaveRound, 
	btnAddRound;
	private CompetitionScheduleTable customTableModelDetail;
	private TablePanel tablePanelDetail; 
	private RoundListPanel roundPanel;
	private JPanel currentPanel;
	private JPanel panel;
	private JPanel panel_1;

	public CompetitionMatchPanel() {
		setLayout(new GridLayout(1, 0, 0, 50));

		currentPanel = new JPanel();
		add(currentPanel);
		currentPanel.setLayout(new BorderLayout());

		tablePanelDetail = new TablePanel(TablePanel.COMPETITION_SCHEDULE_TABLE);
		roundPanel = new RoundListPanel();
		currentPanel.add(roundPanel, BorderLayout.CENTER);
		customTableModelDetail = ((CompetitionScheduleTable)tablePanelDetail.getTblModel());

		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		tablePanelDetail.add(panel_2, BorderLayout.NORTH);

		JPanel panel_21 = new JPanel();
		currentPanel.add(panel_21, BorderLayout.NORTH);
		panel_21.setLayout(new GridLayout(0, 2, 0, 0));

		panel_1 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_1.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEADING);
		panel_21.add(panel_1);

		btnBack = new JButton("Back", new ImageIcon("resources/back.png"));
		btnBack.setFocusPainted( false );
		btnBack.setToolTipText("Back to round list");
		panel_1.add(btnBack);
		btnBack.setVisible(false);
		btnBack.addActionListener(this);

		panel = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panel.getLayout();
		flowLayout_2.setAlignment(FlowLayout.TRAILING);
		panel_21.add(panel);

		btnInfo = new JButton(new ImageIcon("resources/trophy.png"));
		panel.add(btnInfo);
		btnInfo.setFocusPainted( false );
		btnInfo.setToolTipText("Add result match");

		btnSaveChanges = new JButton(new ImageIcon("resources/save.png"));
		panel.add(btnSaveChanges);
		btnSaveChanges.setFocusPainted( false );
		btnSaveChanges.setToolTipText("Save changes");

		btnAdd = new JButton(new ImageIcon("resources/add.png"));
		panel.add(btnAdd);
		btnAdd.setFocusPainted( false );
		btnAdd.setToolTipText("Add a new competition match");

		btnEdit = new JButton("Edit", new ImageIcon("resources/edit.png"));
		panel.add(btnEdit);
		btnEdit.setFocusPainted( false );
		btnEdit.setToolTipText("Edit this match");

		btnRoundInfo = new JButton(new ImageIcon("resources/info.png"));
		panel.add(btnRoundInfo);
		btnRoundInfo.setFocusPainted( false );
		btnRoundInfo.setToolTipText("Round Information");
		btnRoundInfo.addActionListener(this);
		btnRoundInfo.setVisible(false);
		btnEdit.addActionListener(this);
		btnAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tablePanelDetail.addEmptyRow(1);
				int lastIndex = tablePanelDetail.getTable().getRowCount() - 1;
				tablePanelDetail.getTable().setRowSelectionInterval(lastIndex, lastIndex);
				tablePanelDetail.scrollToEnd();
			}
		});
		btnSaveChanges.addActionListener(this);
		btnInfo.addActionListener(this);
		btnInfo.setVisible(false);
		clickSave();

		tablePanelDetail.getTable().getSelectionModel()
		.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				int selectedRowCount = tablePanelDetail
						.getTable().getSelectedRowCount();
				Vector<Match_Schedule> msList = 
						((CompetitionScheduleTable)tablePanelDetail
								.getTblModel()).getMatchScheduleList();

				int maxRow = msList.size();
				if(selectedRowCount == 1 && 
						tablePanelDetail.getTable()
						.getSelectedRow() <  maxRow &&
						msList.get(tablePanelDetail.getTable()
								.getSelectedRow()) != null){
					btnInfo.setVisible(true);
				}
				else {
					btnInfo.setVisible(false);
				}


			}
		});

		roundPanel.getDtm().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {				
				int selectedRowCount = roundPanel
						.getTable().getSelectedRowCount();
				Vector<Round> roundList = ((CompetitionScheduleListTable)roundPanel
						.getTblModel()).getRoundList();
				int maxRow = roundList.size();
				if(selectedRowCount == 1 && 
						roundPanel.getTable()
						.getSelectedRow() <  maxRow &&
						roundList.get(roundPanel.getTable()
								.getSelectedRow()) != null){
					btnRoundInfo.setVisible(true);
				}
				else {
					btnRoundInfo.setVisible(false);
				}
			}
		});

		btnEdit.setVisible(false);
		btnEditRound = new JButton("Edit", 
				new ImageIcon("resources/edit.png"));
		panel.add(btnEditRound);
		btnEditRound.setFocusPainted( false );
		btnEditRound.setToolTipText("Edit rounds");
		btnEditRound.addActionListener(this);
		btnEditRound.setVisible(true);
		btnSaveRound = new JButton(new ImageIcon("resources/save.png"));
		panel.add(btnSaveRound);
		btnSaveRound.setFocusPainted( false );
		btnSaveRound.setToolTipText("Save rounds");
		btnSaveRound.addActionListener(this);
		btnSaveRound.setVisible(false);
		btnAddRound = new JButton(new ImageIcon("resources/add.png"));
		panel.add(btnAddRound);
		btnAddRound.setFocusPainted( false );
		btnAddRound.setToolTipText("Add a round");
		btnAddRound.addActionListener(this);
		btnAddRound.setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if((JButton)e.getSource() == btnInfo){
			btnAddRound.setVisible(false);
			btnSaveRound.setVisible(false);
			Vector<Match_Schedule> msList = 
					((CompetitionScheduleTable)tablePanelDetail
							.getTblModel()).getMatchScheduleList();
			MatchResultDialog f = new MatchResultDialog(
					msList.get(tablePanelDetail.getTable().getSelectedRow()));
			f.setModal(true);
			f.setVisible(true);
		}
		else if((JButton)e.getSource() == btnEdit){
			clickEdit();
		}
		else if((JButton)e.getSource() == btnSaveChanges){
			((CompetitionScheduleTable)tablePanelDetail.getTblModel())
			.saveData();
			clickSave();
		}
		else if((JButton)e.getSource() == btnAdd){
			tablePanelDetail.addEmptyRow(1);
		} 
		else if((JButton)e.getSource() == btnRoundInfo){
			switchToPanel(tablePanelDetail);
			((CompetitionScheduleTable)tablePanelDetail
					.getTblModel()).setRound(roundPanel.getRoundList()
							.get(roundPanel.getTable().getSelectedRow()));
		}
		else if((JButton)e.getSource() == btnBack){
			customTableModelDetail.setEnable(false);
			switchToPanel(roundPanel);
		}
		else if((JButton)e.getSource() == btnEditRound){
			btnEditRound.setVisible(false);
			btnSaveRound.setVisible(true);
			btnAddRound.setVisible(true);
			((CompetitionScheduleListTable)roundPanel.getTblModel())
				.setEnable(true);

		}
		else if((JButton)e.getSource() == btnSaveRound){
			btnEditRound.setVisible(true);
			btnSaveRound.setVisible(false);
			btnAddRound.setVisible(false);
			((CompetitionScheduleListTable)roundPanel.getTblModel())
				.setEnable(false);

		}
		else if((JButton)e.getSource() == btnAddRound){
			roundPanel.addEmptyRow(1);
		}
	}

	public void switchToPanel(JPanel p){
		if(p == roundPanel){
			currentPanel.remove(tablePanelDetail);
			btnEditRound.setVisible(true);
			btnBack.setVisible(false);
			btnEdit.setVisible(false);
		}
		else{
			currentPanel.remove(roundPanel);
			btnEditRound.setVisible(false);
			btnSaveRound.setVisible(false);
			btnEdit.setVisible(true);
			btnBack.setVisible(true);
		}
		btnRoundInfo.setVisible(false);
		btnInfo.setVisible(false);
		btnAdd.setVisible(false);
		btnSaveChanges.setVisible(false);
		currentPanel.add(p, BorderLayout.CENTER);
		currentPanel.revalidate();
		currentPanel.repaint();
		currentPanel.requestFocus();
	}

	public void clickEdit(){
		customTableModelDetail.setEnable(true);
		btnEdit.setVisible(false);
		btnSaveChanges.setVisible(true);
		btnAdd.setVisible(true);
	}

	public void clickSave(){
		customTableModelDetail.setEnable(false);
		btnEdit.setVisible(true);
		btnSaveChanges.setVisible(false);
		btnAdd.setVisible(false);
	}

}
