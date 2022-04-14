package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import model.Player;
import model.Team;

public class DBPlayer {

	public static int addPlayer(DBConnector db, Vector<Player> list, int id_team){
		int result = 0;
		PreparedStatement pstmt = null;
		try {
			String sql = "insert into "
					+ "player(name, dob, id_type, id_team, total_goal) "
					+ "values (?, ?, ?, ?, ?) ";
			for(int i = 0; i< list.size(); i++){
				sql += " , (?, ?, ?, ?, ?) ";
			}
			pstmt = db.getConnection()
					.prepareStatement(sql);
			for(int i = 0; i < list.size(); i++){
				pstmt.setString(1 + i, list.get(i).getName());
				pstmt.setDate(2 + i, list.get(i).getDob());
				pstmt.setInt(3 + i, list.get(i).getTypeOfPlayer());
				pstmt.setInt(4 + i, id_team);
				pstmt.setInt(5 + i, 0);
			}
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi thực hiện câu truy vấn");
		} finally {
			if(pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return result;
	}
	
	public static String getStringTypeOfPlayer(DBConnector db, int id){
		PreparedStatement pstmt = null;
		String result = null;
		try {
			pstmt = db.getConnection()
					.prepareStatement("select name from type_of_player "
							+ "where id = ?");
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.isBeforeFirst()){
				rs.next();
				result = rs.getString(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi thực hiện câu truy vấn");
		}
		finally {
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
	
	public static HashMap<Integer, String> getStringTypeOfPlayer(DBConnector db){
		PreparedStatement pstmt = null;
		HashMap<Integer, String> result = new HashMap<Integer, String>();
		try {
			pstmt = db.getConnection()
					.prepareStatement("select * from type_of_player ");
			ResultSet rs = pstmt.executeQuery();
			if(rs.isBeforeFirst()){
				rs.next();
				result.put(rs.getInt("id"), rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi thực hiện câu truy vấn");
		}
		finally {
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

	public static void getAllTypeOfPlayer(DBConnector db, 
			JComboBox<String> combobox, HashMap<String, Integer> idList){
		PreparedStatement pstmt = null;
		try {
			pstmt = db.getConnection()
					.prepareStatement("select * from type_of_player; ");
			ResultSet rs = pstmt.executeQuery();
			if(rs.isBeforeFirst()){
				while(rs.next()){
					combobox.addItem(rs.getString("name"));
					idList.put(rs.getString("name"), rs.getInt("id"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi thực hiện câu truy vấn");
		}
		finally {
			if(pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	public static Vector<Player> getAllPlayer(DBConnector db, int id_team) {
		Vector<Player> playerList = new Vector<Player>();
		PreparedStatement pstmt = null;
		try {
			pstmt = db.getConnection()
					.prepareStatement("select * from player "
							+ "where id_team = ? ");
			pstmt.setInt(1, id_team);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				Player p = new Player(rs.getInt("id"), 
						rs.getInt("total_goal"),
						rs.getString("name"),
						rs.getInt("id_type"),
						rs.getDate("dob"));
				playerList.add(p);
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
		return playerList;
	}
	
	public static Vector<Player> getAllPlayer(DBConnector db) {
		Vector<Player> playerList = new Vector<Player>();
		PreparedStatement pstmt = null;
		try {
			pstmt = db.getConnection()
					.prepareStatement("select * "
							+ "from player p join team t on (p.id_team = t.id) ");
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				Player p = new Player(rs.getInt("p.id"), 
						rs.getInt("p.total_goal"),
						rs.getString("p.name"),
						rs.getInt("p.id_type"),
						rs.getDate("p.dob"),
						rs.getString("t.name"));
				playerList.add(p);
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
		return playerList;
	}
}
