package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;

import viewmodel.CompetitionScheduleTable;
import model.Match_Schedule;
import model.Player;
import model.Result;
import model.ResultDetail;
import model.Round;
import model.Team;

public class DBMatchSchedule {
	
	public static Vector<Round> getAllRound(DBConnector db){
		PreparedStatement pstmt = null;
		Vector<Round> roundList = new Vector<Round>();
		try {
			pstmt = db.getConnection()
					.prepareStatement("select * from round ");
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				Round r = new Round(rs.getInt("id"), 
						rs.getString("name"));
				roundList.add(r);
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
		return roundList;
	}
	
	public static String getData(DBConnector db, int id_round){
		PreparedStatement pstmt = null;
		String result = null;
		try {
			pstmt = db.getConnection()
					.prepareStatement("select name from type_of_player "
							+ "where id = ?");
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

	public static Vector<Match_Schedule> getAllMatchSchedule(DBConnector db, int id_round){
		PreparedStatement pstmt = null;
		Vector<Match_Schedule> msList = new Vector<Match_Schedule>();
		try {
			pstmt = db.getConnection()
					.prepareStatement("select * from match_schedule "
							+ " where round = ? ");
			pstmt.setInt(1, id_round);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				Team t1 = DBTeam.getTeamByID(DBConnector.getInstance(), 
						rs.getInt("id_first_team"));
				Team t2 = DBTeam.getTeamByID(DBConnector.getInstance(), 
						rs.getInt("id_second_team"));
				Result result = DBResultDetail.getResultByID(db, rs.getInt("id_result"));
				Match_Schedule ms = new Match_Schedule(
						rs.getInt("id"),
						id_round,
						rs.getTimestamp("time").toLocalDateTime(),
						t1, t2, 
						rs.getString("stadium"),
						result);
//				System.out.println(rs.getTimestamp("time"));
				msList.add(ms);
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
		return msList;
	}
	
	public static Match_Schedule addMatchSchedule(DBConnector db, 
			int id_round, Match_Schedule ms){
		Match_Schedule result = null;
		int id = createMatchScheduleInDb(db, id_round, ms);
		if(id > 0){
			result = new Match_Schedule(id, ms.getTime(), 
					ms.getFirstTeam(), ms.getSecondTeam(), 
					ms.getFirstTeam().getHome_stadium(), 
					null);
		}
		return result;
	}
	
	public static int createMatchScheduleInDb(DBConnector db, 
			int id_round, Match_Schedule ms){
		int result = -1;
		PreparedStatement pstmt = null, pstmtLastId = null;
		try {
			String sql = "insert into "
					+ " match_schedule(round, id_first_team, "
					+ " id_second_team, time, stadium, id_result) "
					+ " values (?, ?, ?, ?, ?, null) ";
			pstmtLastId = db.getConnection()
					.prepareStatement("SELECT LAST_INSERT_ID();");
			pstmt = db.getConnection()
					.prepareStatement(sql);
			pstmt.setInt(1, id_round);
			pstmt.setInt(2, ms.getFirstTeam().getId());
			pstmt.setInt(3, ms.getSecondTeam().getId());
			String time = ms.getTime().toString().replace("T", " ");
			if(time.split(" ")[1].length() < 6){
				time += ":00";
			}
			pstmt.setString(4, time);
			pstmt.setString(5, ms.getFirstTeam().getHome_stadium());
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

	public static boolean updateMatchSchedule(DBConnector db,
			Match_Schedule ms) {
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
			System.out.println(ms.getFirstTeam().getName() + " "
					+ ms.getSecondTeam().getName() + " ");
			String sql = "update match_schedule set "
					+ " id_first_team = ?, id_second_team = ?, time = ?, stadium = ? "
					+ " where id = ? ";
			pstmt = db.getConnection()
					.prepareStatement(sql);
			pstmt.setInt(1, ms.getFirstTeam().getId());
			pstmt.setInt(2, ms.getSecondTeam().getId());
			pstmt.setString(3, ms.getTime()
					.toString().replace("T", " ") + ":00");
			pstmt.setString(4, ms.getStadium());
			pstmt.setInt(5, ms.getId());
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
	
	public static boolean updateMatchScheduleResultToNull(DBConnector db, int id) {
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
			String sql = "update match_schedule set "
					+ " id_result = null "
					+ " where id = ? ";
			pstmt = db.getConnection()
					.prepareStatement(sql);
			pstmt.setInt(1, id);
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

	
	public static boolean deleteMatch(DBConnector db,
			Match_Schedule ms) {
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
			if(ms.getMatchResult() != null){
				System.out.println("chay toi day 221 DBMatchSchedule");
				DBResultDetail.deleteResult(db, ms.getMatchResult().getId());
			}
			
			String sql = "delete from match_schedule where id = ? ";
			pstmt = db.getConnection()
					.prepareStatement(sql);
			pstmt.setInt(1, ms.getId());
			System.out.println("chay toi day 229 DBMatchSchedule");
			int num = pstmt.executeUpdate();
			if(num > 0){

				System.out.println("chay toi day 232 DBMatchSchedule");
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

	public static boolean deleteResultDetail(DBConnector db,
			ResultDetail resultDetail) {
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
			String sql = "delete from result_detail where id = ? ";
			pstmt = db.getConnection()
					.prepareStatement(sql);
			pstmt.setInt(1, resultDetail.getId());
			int num = pstmt.executeUpdate();
			if(num > 0){
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

	public static boolean updateResultScore(DBConnector db, int id_result,
			int first_team_score, int second_team_score) {
		PreparedStatement pstmt = null;
		boolean result = false;
		try {			
			String sql = "update result "
					+ " set first_team_score = " + first_team_score
					+ " , second_team_score = " + second_team_score
					+ " where id = ? ";
			pstmt = db.getConnection()
					.prepareStatement(sql);
			pstmt.setInt(1, id_result);
			int num = pstmt.executeUpdate();
			if(num > 0){
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
