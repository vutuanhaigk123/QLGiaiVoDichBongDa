package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JOptionPane;

import model.Match_Schedule;
import model.Player;
import model.Result;
import model.ResultDetail;
import model.Team;

public class DBResultDetail {
	public static Result getResultByID(DBConnector db, int id){
		PreparedStatement pstmt = null;
		Result result = null;
		try {
			pstmt = db.getConnection()
					.prepareStatement("select * from result "
							+ " where id = ? ");
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				System.out.println("tao doi tuong " + rs.getInt("first_team_score"));
				Vector<ResultDetail> scoreList = getResultDetailByID(db, id);
				result = new Result(rs.getInt("id"), 
						rs.getInt("first_team_score"), 
						rs.getInt("second_team_score"), 
						scoreList);
				
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
	
	public static Vector<ResultDetail> getResultDetailByID(DBConnector db, int id){
		PreparedStatement pstmt = null;
		Vector<ResultDetail> scoreList = new Vector<ResultDetail>();
		try {
			pstmt = db.getConnection()
					.prepareStatement("select * from result_detail rd "
							+ " join type_of_goal tg on tg.id = rd.id_type_of_goal "
							+ " where rd.id_result = ? and tg.status = 1 ");
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				Player p = DBPlayer.getPlayerByID(
						DBConnector.getInstance(), 
						rs.getInt("rd.id_player"));
				ResultDetail rd = new ResultDetail(rs.getInt("rd.id"), p, 
						rs.getString("tg.name"), 
						rs.getInt("rd.time"));
				scoreList.add(rd);
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
		return scoreList;
	}
	
	public static boolean deleteResultDetail(
			DBConnector db, int id_result){
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
			String sql = "delete from result_detail where id_result = ? ";
			pstmt = db.getConnection()
					.prepareStatement(sql);
			pstmt.setInt(1, id_result);
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

	public static boolean deleteResult(DBConnector db, int id_result){
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
			deleteResultDetail(db, id_result);
			String sql = "delete from result where id = ? ";
			pstmt = db.getConnection()
					.prepareStatement(sql);
			pstmt.setInt(1, id_result);
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

	public static Result addResult(DBConnector db, Result r, int id_match_schedule){
		Result result = null;
		int id = createResultInDB(db, r.getFirstTeamScore(), r.getSecondTeamScore());
		HashMap<String, Integer> idTypeOfGoalMap = getAllTypeOfGoalMap(db);
		result = new Result(id, r.getFirstTeamScore(),
				r.getSecondTeamScore(), r.getScoredList());
		for (int i = 0; i < r.getScoredList().size(); i++) {
			r.getScoredList().set(i, createResultDetailInDB(
					db, r.getScoredList().get(i), id, idTypeOfGoalMap));
		}
		result.setScoredList(r.getScoredList());
		
		PreparedStatement pstmt = null;
		try {
			String sql = "update match_schedule "
					+ " set id_result = " + id 
					+ " where id = " + id_match_schedule;
			pstmt = db.getConnection()
					.prepareStatement(sql);
			pstmt.executeUpdate();
			
			
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
	
	public static int createResultInDB(DBConnector db, int first_team_score, 
			int second_team_score){
		int result = -1;
		PreparedStatement pstmt = null, pstmtLastId = null;
		try {
			String sql = "insert into "
					+ " result(first_team_score, second_team_score) "
					+ " values (?, ?) ";
			pstmtLastId = db.getConnection()
					.prepareStatement("SELECT LAST_INSERT_ID();");
			pstmt = db.getConnection()
					.prepareStatement(sql);
			pstmt.setInt(1, first_team_score);
			pstmt.setInt(2, second_team_score);
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
	
	public static ResultDetail createResultDetailInDB
					(DBConnector db, ResultDetail rd, int id_result, 
					HashMap<String, Integer> idTypeOfGoalMap){
		PreparedStatement pstmt = null, pstmtLastId = null;
		ResultDetail result = null;
		try {
			String sql = "insert into "
					+ " result_detail(id_result, id_player, id_type_of_goal, time) "
					+ " values (?, ?, ?, ?) ";
			pstmtLastId = db.getConnection()
					.prepareStatement("SELECT LAST_INSERT_ID();");
			pstmt = db.getConnection()
					.prepareStatement(sql);
			pstmt.setInt(1, id_result);
			pstmt.setInt(2, rd.getPlayer().getId());
			pstmt.setInt(3, idTypeOfGoalMap.get(rd.getTypeOfGoal()));
			pstmt.setInt(4, rd.getTime());
			pstmt.executeUpdate();
			ResultSet rs = pstmtLastId.executeQuery();
			if(rs.isBeforeFirst()){
				rs.next();
				result = new ResultDetail(rs.getInt(1), 
						rd.getPlayer(), rd.getTypeOfGoal(), rd.getTime());
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
	
	public static HashMap<String, Integer> getAllTypeOfGoalMap(DBConnector db){
		PreparedStatement pstmt = null;
		HashMap<String, Integer> result = new HashMap<String, Integer>();
		try {
			
			String sql = "select * from type_of_goal where status = 1 ";
			pstmt = db.getConnection()
					.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				result.put(rs.getString("name"), rs.getInt("id"));
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
	
	public static boolean updateResultDetail(DBConnector db, 
			ResultDetail rd, int id_type_of_goal){
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
			String sql = "update result_detail "
					+ " set id_player = ?, id_type_of_goal = ?, time = ? "
					+ " where id = ? ";
			pstmt = db.getConnection()
					.prepareStatement(sql);
			pstmt.setInt(1, rd.getPlayer().getId());
			pstmt.setInt(2, id_type_of_goal);
			pstmt.setInt(3, rd.getTime());
			pstmt.setInt(4, rd.getId());
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
	
}
