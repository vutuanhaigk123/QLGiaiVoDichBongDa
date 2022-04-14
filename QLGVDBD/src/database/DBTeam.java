package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

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
				Team t = new Team(rs.getString("name"), 
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

	public static void addTeam(DBConnector db, String name, 
			String home_stadium, Vector<Player> list){
				
	}
	
	private boolean createTeamInDb(DBConnector db, String name, String home_stadium){
		PreparedStatement pstmt = null;
		try {
			pstmt = db.getConnection()
					.prepareStatement("insert into team(name, home_stadium) "
							+ "values (?, ?)");
			pstmt.setString(1, name);
			pstmt.setString(2, home_stadium);
			if( pstmt.executeUpdate() != 0){
				return true;
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
		return false;
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
}
