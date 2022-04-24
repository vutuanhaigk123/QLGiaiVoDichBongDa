package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;

import model.Priority;

public class DBPriority {

    public static Vector<Priority> getAllPriorities(DBConnector db) {
        Vector<Priority> priorityList = new Vector<Priority>();
        PreparedStatement pstmt = null;
        try {
            pstmt = db.getConnection()
                    .prepareStatement("SELECT * FROM priority ORDER BY orderPriority");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Priority p = new Priority(rs.getInt("id"),
                        rs.getInt("orderPriority"),
                        rs.getString("name"));
                priorityList.add(p);
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
        return priorityList;
    }
}
