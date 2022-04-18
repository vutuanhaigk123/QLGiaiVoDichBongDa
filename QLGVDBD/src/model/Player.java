package model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import database.DBConnector;

public class Player {

	private int id, total_goal, typeOfPlayer;
	private String name;
	private Date dob;
	private String teamName, note;
	
	public Player(int total_goal, String note,
			String name, int typeOfPlayer, Date dob) {
		super();
		this.id = -1;
		this.note = note;
		this.total_goal = total_goal;
		this.name = name;
		this.dob = dob;
		this.typeOfPlayer = typeOfPlayer;
		this.teamName = null;
	}
	
	public Player(int id, int total_goal, String note,
			String name, int typeOfPlayer, Date dob) {
		super();
		this.note = note;
		this.id = id;
		this.total_goal = total_goal;
		this.name = name;
		this.dob = dob;
		this.typeOfPlayer = typeOfPlayer;
		this.teamName = null;
	}
	
	public Player(int id, int total_goal, String name,  String note,
			int typeOfPlayer, Date dob, String teamName) {
		super();
		this.note = note;
		this.id = id;
		this.total_goal = total_goal;
		this.name = name;
		this.dob = dob;
		this.typeOfPlayer = typeOfPlayer;
		this.teamName = teamName;
	}	
	
	public boolean isEqual(Player p){
		if(p.getId() != id || p.getDob() != dob 
				|| p.getName() != name || p.getNote() != note
				|| p.getTeamName() != teamName 
				|| p.getTotal_goal() != total_goal
				|| p.getTypeOfPlayer() != typeOfPlayer){
			return false;
		}
		return true;
	}

	public String getNote() {
		return note;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTotal_goal() {
		return total_goal;
	}
	public void setTotal_goal(int total_goal) {
		this.total_goal = total_goal;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public int getTypeOfPlayer() {
		return typeOfPlayer;
	}
	public void setTypeOfPlayer(int typeOfPlayer) {
		this.typeOfPlayer = typeOfPlayer;
	}

	public String getTeamName() {
		return teamName;
	}
	
	
}
