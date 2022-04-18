package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import model.Player;
import model.Team;

public class DBTeam {
	
	public static Vector<Team> getAllTeam(DBConnector db){
		Vector<Team> teamList = new Vector<Team>();
		PreparedStatement pstmt = null;
		try {
			pstmt = db.getConnection()
					.prepareStatement("select * from team ");
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				Vector<Player> playerList = DBPlayer.getAllPlayer(db, rs.getInt("id"));
				Team t = new Team(rs.getInt("id"),
						rs.getString("name"), 
						rs.getString("home_stadium"), 
						playerList);
				teamList.add(t);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi thực hiện câu truy vấn");
		} finally {
			if(pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return teamList;
	}

	public static Team addTeam(DBConnector db, String name, 
			String home_stadium, Vector<Player> list){
		Team t = null;
		int id = DBTeam.createTeamInDb(db, name, home_stadium);
		if(id > 0){
			t =  new Team(id, name, home_stadium, null);
			Vector<Player> playerList = new Vector<Player>();
			for (int i = 0; i < list.size(); i++) {
				Player p = DBPlayer.addPlayer(db, list.get(i), id, t.getName());
				if(p != null){
					playerList.add(p);
				}
			}
			t.setPlayerList(playerList);
		}
		return t;
	}
	
	public static boolean updateTeam(DBConnector db, Team team){
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
			pstmt = db.getConnection()
					.prepareStatement("update team "
							+ " set name = ?, home_stadium = ? "
							+ " where id = ?");
			pstmt.setString(1, team.getName());
			pstmt.setString(2, team.getHome_stadium());
			pstmt.setInt(3, team.getId());
			if(pstmt.executeUpdate() > 0){
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi thực hiện câu truy vấn");
		} finally {
				try {
					if(pstmt != null){
						pstmt.close();						
					} 
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return result;
	}
	
	synchronized public static int createTeamInDb(DBConnector db, 
			String name, String home_stadium){
		PreparedStatement pstmt = null, pstmtLastId = null;
		int result = -1;
		try {
			pstmt = db.getConnection()
					.prepareStatement("insert into team(name, home_stadium) "
							+ "values (?, ?); ");
			pstmt.setString(1, name);
			pstmt.setString(2, home_stadium);
			pstmtLastId = db.getConnection()
					.prepareStatement("SELECT LAST_INSERT_ID();");
			pstmt.executeUpdate();
			ResultSet rs = pstmtLastId.executeQuery();
			if(rs.isBeforeFirst()){
				rs.next();
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi thực hiện câu truy vấn");
		} finally {
				try {
					if(pstmt != null){
						pstmt.close();						
					} 
					if(pstmtLastId != null){
						pstmtLastId.close();
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return result;
	}
	
	public static int findTeamID(DBConnector db, String name){
		PreparedStatement pstmt = null;
		int result = -1;
		try {
			pstmt = db.getConnection()
					.prepareStatement("select id from team "
							+ "where name = ?");
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			if(rs.isBeforeFirst()){
				rs.next();
				result = rs.getInt(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi thực hiện câu truy vấn");
		} finally {
			if(pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return result;
	}

	public static Vector<Team> updateTeamCombobox(DBConnector db, 
			JComboBox<String> cb, JComboBox<String> cb2){
		PreparedStatement pstmt = null;
		Vector<Team> teamList = new Vector<Team>();
		try {
			pstmt = db.getConnection()
					.prepareStatement("select * from team");
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				cb.addItem(rs.getString("name"));
				cb2.addItem(rs.getString("name"));
				teamList.add(new Team(rs.getInt("id"), 
						rs.getString("name"), 
						rs.getString("home_stadium"), null));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi thực hiện câu truy vấn");
		} finally {
			if(pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return teamList;
	}
}
