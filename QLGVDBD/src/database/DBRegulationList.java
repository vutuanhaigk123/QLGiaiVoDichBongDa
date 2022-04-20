package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;

import model.RegulationList;

public class DBRegulationList {
	public static Vector<RegulationList> getAllRegulation(DBConnector db) {
		Vector<RegulationList> regulationList = new Vector<RegulationList>();
		PreparedStatement pstmt = null;
		try {
			pstmt = db.getConnection().prepareStatement("select * from regulation");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {

				RegulationList p = new RegulationList(rs.getInt("id"), rs.getString("name"), rs.getString("type"),
						rs.getString("value"), rs.getBoolean("status"));
				regulationList.add(p);
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
		return regulationList;
	}

	public static void saveData(DBConnector db, Vector<RegulationList> list) {
		PreparedStatement pstmt = null;
		try {
			for (RegulationList item : list) {
				pstmt = db.getConnection().prepareStatement("update regulation set value = ? where id = ?");
				pstmt.setString(1, item.getValue());
				pstmt.setInt(2, item.getID());
				pstmt.executeUpdate();
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
}
