package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import model.Player;

public class DBPlayer {

	public static Player addPlayer(
			DBConnector db, Player player, int id_team, String teamName) {
		Player result = null;
		int id = createPlayerInDb(db, player, id_team);
		if (id > 0) {
			result = new Player(id, player.getTotal_goal(),
					player.getName(), player.getNote(),
					player.getTypeOfPlayer(), player.getDob(), teamName);
		}
		return result;
	}

	synchronized public static int createPlayerInDb(
			DBConnector db, Player player, int id_team) {
		int result = -1;
		PreparedStatement pstmt = null, pstmtLastId = null;
		try {
			String sql = "insert into "
					+ "player(name, dob, id_type, id_team, total_goal, note) "
					+ "values (?, ?, ?, ?, ?, ?) ";
			// for(int i = 0; i< list.size(); i++){
			// sql += " , (?, ?, ?, ?, ?) ";
			// }
			pstmtLastId = db.getConnection()
					.prepareStatement("SELECT LAST_INSERT_ID();");
			pstmt = db.getConnection()
					.prepareStatement(sql);
			// for(int i = 0; i < list.size(); i++){
			pstmt.setString(1, player.getName());
			pstmt.setDate(2, player.getDob());
			pstmt.setInt(3, player.getTypeOfPlayer());
			pstmt.setInt(4, id_team);
			pstmt.setInt(5, 0);
			pstmt.setString(6, player.getNote());
			// }
			pstmt.executeUpdate();
			ResultSet rs = pstmtLastId.executeQuery();
			if (rs.isBeforeFirst()) {
				rs.next();
				result = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi thực hiện câu truy vấn");
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (pstmtLastId != null) {
					pstmtLastId.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	public static String getStringTypeOfPlayer(DBConnector db, int id) {
		PreparedStatement pstmt = null;
		String result = null;
		try {
			pstmt = db.getConnection()
					.prepareStatement("select name from type_of_player "
							+ "where id = ?");
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.isBeforeFirst()) {
				rs.next();
				result = rs.getString(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi thực hiện câu truy vấn");
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return result;
	}

	public static HashMap<Integer, String> getStringTypeOfPlayer(DBConnector db) {
		PreparedStatement pstmt = null;
		HashMap<Integer, String> result = new HashMap<Integer, String>();
		try {
			pstmt = db.getConnection()
					.prepareStatement("select * from type_of_player ");
			ResultSet rs = pstmt.executeQuery();
			if (rs.isBeforeFirst()) {
				rs.next();
				result.put(rs.getInt("id"), rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi thực hiện câu truy vấn");
		} finally {
			if (pstmt != null)
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
			JComboBox<String> combobox, HashMap<String, Integer> idList) {
		PreparedStatement pstmt = null;
		try {
			pstmt = db.getConnection()
					.prepareStatement("select * from type_of_player; ");
			ResultSet rs = pstmt.executeQuery();
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					combobox.addItem(rs.getString("name"));
					idList.put(rs.getString("name"), rs.getInt("id"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi thực hiện câu truy vấn");
		} finally {
			if (pstmt != null)
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
			while (rs.next()) {
				Player p = new Player(rs.getInt("id"),
						rs.getInt("total_goal"),
						rs.getString("note"),
						rs.getString("name"),
						rs.getInt("id_type"),
						rs.getDate("dob"));
				playerList.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi thực hiện câu truy vấn");
		} finally {
			if (pstmt != null)
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
							+ "from player p join team t on (p.id_team = t.id) "
							+ "order by p.id_team ");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {

				Player p = new Player(rs.getInt("p.id"),
						rs.getInt("p.total_goal"),
						rs.getString("p.name"),
						rs.getString("note"),
						rs.getInt("p.id_type"),
						rs.getDate("p.dob"),
						rs.getString("t.name"));
				playerList.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi thực hiện câu truy vấn");
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return playerList;
	}

	public static boolean deletePlayer(DBConnector db, Player player) {
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
			String sql = "delete from player where id = ? ";
			pstmt = db.getConnection()
					.prepareStatement(sql);
			pstmt.setInt(1, player.getId());
			pstmt.executeUpdate();
			if (pstmt.executeUpdate() > 0) {
				result = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi thực hiện câu truy vấn");
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	public static boolean canDelete(DBConnector db, Player player) {
		if (countNumOfOccurrences(db, player, "player_leaderboard")
				+ countNumOfOccurrences(db, player, "result_detail") == 0) {
			return true;
		}
		return false;
	}

	public static int countNumOfOccurrences(DBConnector db,
			Player player, String tableName) {
		int result = 0;
		PreparedStatement pstmt = null;
		try {
			String sql = "select count(*) from " + tableName
					+ " where id_player = ? ";
			pstmt = db.getConnection()
					.prepareStatement(sql);
			pstmt.setInt(1, player.getId());
			pstmt.executeQuery();
			ResultSet rs = pstmt.executeQuery();
			if (rs.isBeforeFirst()) {
				rs.next();
				result = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi thực hiện câu truy vấn");
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	public static boolean updatePlayer(DBConnector db, Player p) {
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
			String sql = "update player set "
					+ " name = ?, dob = ?, id_type = ?, note = ? "
					+ " where id = ? ";
			pstmt = db.getConnection()
					.prepareStatement(sql);
			pstmt.setString(1, p.getName());
			pstmt.setDate(2, p.getDob());
			pstmt.setInt(3, p.getTypeOfPlayer());
			pstmt.setString(4, p.getNote());
			pstmt.setInt(5, p.getId());
			pstmt.executeUpdate();
			if (pstmt.executeUpdate() > 0) {
				result = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi thực hiện câu truy vấn");
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	public static Vector<Player> searchPlayer(DBConnector db, String keyword) {
		PreparedStatement pstmt = null;
		Vector<Player> result = new Vector<>();
		try {
			String sql = "SELECT * FROM player p join team t on (p.id_team = t.id) WHERE p.name LIKE CONCAT('%', CONVERT(?, BINARY), '%')";
			pstmt = db.getConnection().prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Player p = new Player(rs.getInt("p.id"),
				rs.getInt("p.total_goal"),
				rs.getString("p.name"),
				rs.getString("p.note"),
				rs.getInt("p.id_type"),
				rs.getDate("p.dob"),
				rs.getString("t.name"));
				result.add(p);
				System.out.println(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Lỗi khi thực hiện câu truy vấn");
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
}
