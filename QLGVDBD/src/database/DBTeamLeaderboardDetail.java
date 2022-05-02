package database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;

import model.Player;
import model.PlayerLeaderboardDetail;
import model.TeamLeaderboard;
import model.TeamLeaderboardDetail;

public class DBTeamLeaderboardDetail {
	
	private static final int SUM_GOAL_CMD_CODE = 1;
	private static final int COUNT_ROW_CMD_CODE = 2;
	private static final int GREATER_OP_CODE = 3;
	private static final int LESSER_OP_CODE = 4;
	private static final int EQUAL_OP_CODE = 5;
	
	public static final int COUNT_TOTAL_WIN = 0;
	public static final int COUNT_TOTAL_TIRE = 1;
	public static final int COUNT_TOTAL_DEFEAT = 2;
	public static final int COUNT_TOTAL_DIFFERENCE = 3;
	
	private static String[] convertToCommand(int code){
		if(code == SUM_GOAL_CMD_CODE){
			return new String[]{" sum(cast(rs.first_team_score as decimal) - "
					+ " cast(rs.second_team_score as decimal)) ", 
					" sum(cast(rs.second_team_score as decimal) - "
					+ " cast(rs.first_team_score as decimal)) "};
		}
		else if(code == COUNT_ROW_CMD_CODE){
			return new String[]{" count(*) ", " count(*) "};
		}
		return null;
	}
	
	private static String convertToOperator(int code){
		if(code == GREATER_OP_CODE){
			return " > ";
		}
		else if(code == LESSER_OP_CODE){
			return " < ";
		}
		else if(code == EQUAL_OP_CODE){
			return " = ";
		}
		return null;
	}

	private static PreparedStatement convertToSQLQuery(DBConnector db, 
			int code, int idTeam, Date time) throws SQLException{
		PreparedStatement pstmt = null;
		String[] cmd = null;
		String operator = "";
		String sql = "";
		if(code != COUNT_TOTAL_DIFFERENCE){
			if(code == COUNT_TOTAL_WIN){
				cmd = convertToCommand(COUNT_ROW_CMD_CODE);
				operator = convertToOperator(GREATER_OP_CODE);
			}
			else if(code == COUNT_TOTAL_TIRE){
				cmd = convertToCommand(COUNT_ROW_CMD_CODE);
				operator = convertToOperator(EQUAL_OP_CODE);
			}
			else if(code == COUNT_TOTAL_DEFEAT){
				cmd = convertToCommand(COUNT_ROW_CMD_CODE);
				operator = convertToOperator(LESSER_OP_CODE);
			}
			sql = "select "
					+ " ((select " + cmd[0] + " from result rs " 
					+ "   join match_schedule ms on ms.id_result = rs.id "
					+ "   where rs.first_team_score" + operator + "rs.second_team_score "
					+ "   and ms.id_first_team = ? "
					+ "   and ms.time <= ?) " 
					+ " +(select " + cmd[1] + " from result rs "
					+ "   join match_schedule ms on ms.id_result = rs.id "
					+ "   where rs.second_team_score" + operator + "rs.first_team_score "
					+ "   and ms.id_second_team = ? "
					+ "   and ms.time <= ?)) as total ";
			
		}
		
		else{
			cmd = convertToCommand(SUM_GOAL_CMD_CODE);
			sql = "select "
					+ " (ifnull((select " + cmd[0] + " from result rs " 
					+ "   join match_schedule ms on ms.id_result = rs.id "
					+ "   where ms.id_first_team = ? "
					+ "   and ms.time <= ?), 0) " 
					+ " + ifnull((select " + cmd[1] + " from result rs "
					+ "   join match_schedule ms on ms.id_result = rs.id "
					+ "   where ms.id_second_team = ? "
					+ "   and ms.time <= ?), 0)) as total ";
			
		}
		pstmt = db.getConnection()
				.prepareStatement(sql);
		pstmt.setInt(1, idTeam);
		pstmt.setDate(2, time);
		pstmt.setInt(3, idTeam);
		pstmt.setDate(4, time);
		
		return pstmt;
	}
	
	public static int compare(DBConnector db, int idTeam1, int idTeam2, Date time){
		int result = 0;
		PreparedStatement pstmt = null;
		try {
			pstmt = db.getConnection()
					.prepareStatement("select * from match_schedule ms "
							+ " join result rs on ms.id_result = rs.id "
							+ " where (ms.id_first_team = ? and "
							+ " ms.id_second_team = ?) or "
							+ " (ms.id_second_team = ? and "
							+ " ms.id_first_team = ?) and "
							+ " ms.time <= ? ");
			pstmt.setInt(1, idTeam1);
			pstmt.setInt(2, idTeam2);
			pstmt.setInt(3, idTeam1);
			pstmt.setInt(4, idTeam2);
			pstmt.setDate(5, time);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				if(rs.getInt("ms.id_first_team") == idTeam1){
					if(rs.getInt("rs.first_team_score") < rs.getInt("rs.second_team_score")){
						result -= 1;
					}
					else if(rs.getInt("rs.first_team_score") > rs.getInt("rs.second_team_score")){
						result += 1;
					}
				}
				else{
					if(rs.getInt("rs.first_team_score") < rs.getInt("rs.second_team_score")){
						result += 1;
					}
					else if(rs.getInt("rs.first_team_score") > rs.getInt("rs.second_team_score")){
						result -= 1;
					}
				}
			}
			if(result > 0){
				result = 1;
			}
			else if(result < 0){
				result = -1;
			}
			else{
				result = 0;
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
	
	public static int countTotalGoal(DBConnector db, int idTeam, Date time){
		int result = 0;
		PreparedStatement pstmt = null;
		try {
			pstmt = db.getConnection()
					.prepareStatement(" SELECT count(*) "
							+ " FROM result_detail rd "
							+ " join match_schedule ms "
							+ " on ms.id_result = rd.id_result "
							+ " where ms.time <= ? and "
							+ " rd.id_player in (select id from player where id_team = ?) ");
			pstmt.setDate(1, time);
			pstmt.setInt(2, idTeam);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			result = rs.getInt("count(*)");
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
	
	public static int countTotal(DBConnector db, int queryCode, int idTeam, Date time){
		int result = 0;
		PreparedStatement pstmt = null;
		try {
			pstmt = convertToSQLQuery(db, queryCode, idTeam, time);
//			pstmt = db.getConnection()
//					.prepareStatement("select "
//						+ " ((select " + cmd[0] + " from result rs " 
//						+ "   join match_schedule ms on ms.id_result = rs.id "
//						+ "   where rs.first_team_score" + operator + "rs.second_team_score " 
//						+ "   and ms.id_first_team = ? "
//						+ "   and ms.time <= ?) " 
//						+ " +(select " + cmd[1] + " from result rs "
//						+ "   join match_schedule ms on ms.id_result = rs.id "
//						+ "   where rs.second_team_score" + operator + "rs.first_team_score "
//						+ "   and ms.id_second_team = ? "
//						+ "   and ms.time <= ?)) as total ");
			
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			result = rs.getInt("total");
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
	
	public static int findIdLeaderboardByTime(DBConnector db, Date time){
		int result = -1;
		PreparedStatement pstmt = null;
		try {
			pstmt = db.getConnection()
					.prepareStatement("select distinct tl.id_leaderboard as id "
							+ " from team_leaderboard tl join leaderboard l "
							+ " on tl.id_leaderboard = l.id "
							+ " where l.time = ? ");
			pstmt.setDate(1, time);
			ResultSet rs = pstmt.executeQuery();
			if(rs.isBeforeFirst()){
				rs.next();
				result = rs.getInt("id");
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

	private static int createLeaderboardInDb(DBConnector db, Date time){
		PreparedStatement pstmt = null, pstmtLastId = null;
		int result = -1;
		try {
			pstmt = db.getConnection()
					.prepareStatement("insert into leaderboard(time) "
							+ "values (?); ");
			pstmt.setDate(1, time);
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

	public static int createTeamLeaderboardInDb(DBConnector db, 
			TeamLeaderboard teamLeaderboard){
		int result = 0;
		int id_leaderboard = createLeaderboardInDb(db, teamLeaderboard.getTime());
		if(id_leaderboard != -1){
			teamLeaderboard.setId_Leaderboard(id_leaderboard);
			for (int i = 0; i < teamLeaderboard.getTeamList().size(); i++) {
				result += createTeamLeaderboardInDb(db, 
						teamLeaderboard.getTeamList().get(i), 
						teamLeaderboard.getId_Leaderboard());
			}
		}
		
		return result;
	}
	
	private static int createTeamLeaderboardInDb(DBConnector db, 
			TeamLeaderboardDetail teamLeaderboard, int id_leaderboard){
		int result = 0;
		PreparedStatement pstmt = null;
		try {
			pstmt = db.getConnection()
					.prepareStatement("insert into team_leaderboard(id_leaderboard, "
							+ " id_team, total_win, total_defeat, total_tire, "
							+ " difference, rank, rank_score, total_goal ) "
							+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?) ");
			pstmt.setInt(1, id_leaderboard);
			pstmt.setInt(2, teamLeaderboard.getIdTeam());
			pstmt.setInt(3, teamLeaderboard.getTotalWin());
			pstmt.setInt(4, teamLeaderboard.getTotalDefeat());
			pstmt.setInt(5, teamLeaderboard.getTotalTire());
			pstmt.setInt(6, teamLeaderboard.getDifference());
			pstmt.setInt(7, teamLeaderboard.getRank());
			pstmt.setInt(8, teamLeaderboard.getRankScore());
			pstmt.setInt(9, teamLeaderboard.getTotalGoal());
			if(pstmt.executeUpdate() > 0){
				result = 1;
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
	
	public static int updateTeamLeaderboardInDb(DBConnector db, 
			TeamLeaderboard teamLeaderboard){
		int result = 0;
		for (int i = 0; i < teamLeaderboard.getTeamList().size(); i++) {
			result += updateTeamLeaderboardInDb(db, 
					teamLeaderboard.getTeamList().get(i), 
					teamLeaderboard.getId_Leaderboard());
		}
		return result;
	}
	
	private static int updateTeamLeaderboardInDb(DBConnector db, 
			TeamLeaderboardDetail teamLeaderboard, int id_leaderboard){
		int result = 0;
		PreparedStatement pstmt = null;
		try {
			pstmt = db.getConnection()
					.prepareStatement("update team_leaderboard "
							+ " set "
							+ " total_win = ?, "
							+ " total_defeat = ?, "
							+ " total_tire = ?, "
							+ " difference = ?, "
							+ " rank = ?, "
							+ " rank_score = ?,"
							+ " total_score = ? "
							+ " where id_leaderboard = ? and "
							+ " id_team = ?");
			pstmt.setInt(8, id_leaderboard);
			pstmt.setInt(9, teamLeaderboard.getIdTeam());
			pstmt.setInt(1, teamLeaderboard.getTotalWin());
			pstmt.setInt(2, teamLeaderboard.getTotalDefeat());
			pstmt.setInt(3, teamLeaderboard.getTotalTire());
			pstmt.setInt(4, teamLeaderboard.getDifference());
			pstmt.setInt(5, teamLeaderboard.getRank());
			pstmt.setInt(6, teamLeaderboard.getRankScore());
			pstmt.setInt(7, teamLeaderboard.getTotalGoal());
			if(pstmt.executeUpdate() > 0){
				result = 1;
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
	
	private static int deleteLeaderboard(DBConnector db, int id_leaderboard){
		int result = 0;
		PreparedStatement pstmt = null;
		try {
			pstmt = db.getConnection()
					.prepareStatement("delete from leaderboard "
							+ " where id_leaderboard = ? ");
			pstmt.setInt(1, id_leaderboard);
			if(pstmt.executeUpdate() > 0){
				result = 1;
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
	
	public static int deleteTeamLeaderboard(DBConnector db, int id_leaderboard){
		int result = 0;
		PreparedStatement pstmt = null;
		try {
			pstmt = db.getConnection()
					.prepareStatement("delete from team_leaderboard "
							+ " where id_leaderboard = ? ");
			pstmt.setInt(1, id_leaderboard);
			int num = pstmt.executeUpdate();
			if( num > 0){
				result = num;
				deleteLeaderboard(db, id_leaderboard);
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
	
	public static Vector<PlayerLeaderboardDetail> getAllPlayers(DBConnector db, Date time){
		Vector<PlayerLeaderboardDetail> players = new Vector<PlayerLeaderboardDetail>();
		PreparedStatement pstmt = null;
		try {
			pstmt = db.getConnection()
					.prepareStatement(" SELECT count(rd.id_player) as totalGoal, "
							+ " p.name, t.name, p.id_type, p.id, p.total_goal, "
							+ " p.note, p.dob "
							+ " FROM result_detail rd "
							+ " right join player p "
							+ " on p.id = rd.id_player "
							+ " left join match_schedule ms "
							+ " on ms.id_result = rd.id_result "
							+ " left join team t "
							+ " on t.id = p.id_team "
							+ " where ms.time <= ? or ms.time is null "
							+ " group by p.id "
							+ " order by totalGoal desc ");
			pstmt.setDate(1, time);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				players.add(new PlayerLeaderboardDetail(
						new Player(rs.getInt("p.id"), 
								rs.getInt("p.total_goal"), 
								rs.getString("p.name"), 
								rs.getString("p.note"), 
								rs.getInt("p.id_type"), 
								rs.getDate("p.dob"), 
								rs.getString("t.name")),
						rs.getInt("totalGoal")));
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
		return players;
	}
	
	
}
