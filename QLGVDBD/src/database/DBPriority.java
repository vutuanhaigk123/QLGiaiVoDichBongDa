package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;

import model.Match_Schedule;
import model.Priority;
import model.Result;
import model.Team;

public class DBPriority {

	public static Vector<Priority> getAllPriority(DBConnector db){
		Vector<Priority> result = new Vector<Priority>();
		PreparedStatement pstmt = null;
		try {
			pstmt = db.getConnection()
					.prepareStatement("select * from priority order by orderPriority");
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				result.add(new Priority(rs.getInt("id"), 
						rs.getInt("orderPriority"), 
						rs.getString("name")));	
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
	
	public static boolean changePriority(DBConnector db, int id, int orderPriority){
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
			pstmt = db.getConnection()
					.prepareStatement("update priority "
							+ " set orderPriority = ? "
							+ " where id = ? ");
			pstmt.setInt(1, orderPriority);
			pstmt.setInt(2, id);
			if(pstmt.executeUpdate() > 0){
				result = true;
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
	
}
