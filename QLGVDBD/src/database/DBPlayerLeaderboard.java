package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;

import model.Player;

public class DBPlayerLeaderboard {
	public static Vector<Player> getAllPlayer(DBConnector db) {
		Vector<Player> playerList = new Vector<Player>();
		PreparedStatement pstmt = null;
		try {
			pstmt = db.getConnection()
					.prepareStatement("select * "
							+ "from player p join team t on (p.id_team = t.id) "
							+ "order by p.total_goal DESC ");
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
}
