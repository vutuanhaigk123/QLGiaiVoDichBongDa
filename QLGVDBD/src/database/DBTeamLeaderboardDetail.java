package database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Vector;

import javax.swing.JOptionPane;

import model.Priority;
import model.Regulation;
import model.Team;
import model.TeamLeaderboard;
import model.TeamLeaderboardDetail;

public class DBTeamLeaderboardDetail {
    final static private int WIN_SCORE_IDX = 6;
    final static private int TIRE_SCORE_IDX = 7;
    final static private int LOSE_SCORE_IDX = 8;

    public static void getAllTeam(DBConnector db) {
        Vector<Team> TeamList = DBTeam.getAllTeam(db);
        PreparedStatement pstmt = null;
        TeamLeaderboard.teamLeaderboardList = new Vector<>();

        try {
            pstmt = db.getConnection()
                    .prepareStatement("select MAX(id) as MAX_ID from leaderboard");
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            int id_leaderboard = rs.getInt("MAX_ID") + 1;
            PreparedStatement tStatement = db.getConnection()
                    .prepareStatement("INSERT INTO leaderboard VALUES (? , ?)");
            java.util.Date utilDate = new java.util.Date();
            tStatement.setInt(1, id_leaderboard);
            tStatement.setDate(2, new Date(utilDate.getTime()));
            tStatement.executeUpdate();

            for (Team team : TeamList)
                TeamLeaderboard.teamLeaderboardList
                        .add(new TeamLeaderboardDetail(team, id_leaderboard, 0, 0, 0, 0, 0, 0, 0));
            loadAllWin(db);
            loadAllDefeat(db);
            loadAllTire(db);
            loadTotalGoal(db);

            Regulation.regulationList = DBRegulationList.getAllRegulation(db);
            loadDif_And_RankScore();

            Vector<Priority> priorityList = DBPriority.getAllPriority(db);
            while (!priorityList.isEmpty()) {
                Priority tmp = priorityList.remove(0);
                switch (tmp.getName()) {
                    case "Điểm":
                        swapByScore();
                        break;

                    case "Hiệu số":
                        swapByDifference();
                        break;

                    case "Tổng số bàn thắng":
                        swapByTotalGoal();
                        break;

                    case "Tổng số bàn đối kháng":
                        swapByVersus();
                        break;
                }
            }
            saveData(db);

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

    private static void loadDif_And_RankScore() {
        for (TeamLeaderboardDetail detail : TeamLeaderboard.teamLeaderboardList) {
            detail.setDifference(detail.getTotalWin() - detail.getTotalDefeat());
            detail.setRankScore(
                    detail.getTotalWin() * Integer.parseInt(Regulation.regulationList.get(WIN_SCORE_IDX).getValue())
                            + detail.getTotalTire()
                                    * Integer.parseInt(Regulation.regulationList.get(TIRE_SCORE_IDX).getValue()));
        }
    }

    private static void loadTotalGoal(DBConnector db) {
        PreparedStatement pstmt = null;
        try {
            pstmt = db.getConnection()
                    .prepareStatement(
                            "select sum(total_goal) as TOTAL from player where id_team = ? ");
            for (TeamLeaderboardDetail detail : TeamLeaderboard.teamLeaderboardList) {
                pstmt.setInt(1, detail.getTeam().getId());
                ResultSet rs = pstmt.executeQuery();
                if (rs.next())
                    detail.setTotalGoal(detail.getTotalGoal() + rs.getInt("TOTAL"));
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

    private static void loadAllTire(DBConnector db) {
        PreparedStatement pstmt = null;
        try {
            pstmt = db.getConnection()
                    .prepareStatement(
                            "Select count(*) as allWin from team t, match_schedule ms, result r "
                                    + " where (t.id = ms.id_first_team or t.id = ms.id_second_team) and ms.id_result = r.id and r.first_team_score = r.second_team_score and t.id = ?");
            for (TeamLeaderboardDetail detail : TeamLeaderboard.teamLeaderboardList) {
                pstmt.setInt(1, detail.getTeam().getId());
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    detail.setTotalTire(detail.getTotalTire() + rs.getInt("allWin"));
                    // System.out.println("TIRE COUNT: " + rs.getInt("allWin"));
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

    private static void loadAllDefeat(DBConnector db) {
        PreparedStatement pstmt = null;
        try {
            pstmt = db.getConnection()
                    .prepareStatement(
                            "Select count(*) as allWin from team t, match_schedule ms, result r "
                                    + " where (t.id = ms.id_first_team and ms.id_result = r.id and r.first_team_score < r.second_team_score and t.id = ?) "
                                    + "OR (t.id = ms.id_second_team and ms.id_result = r.id and r.first_team_score > r.second_team_score and t.id = ?)");
            for (TeamLeaderboardDetail detail : TeamLeaderboard.teamLeaderboardList) {
                pstmt.setInt(1, detail.getTeam().getId());
                pstmt.setInt(2, detail.getTeam().getId());
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    detail.setTotalDefeat(detail.getTotalDefeat() + rs.getInt("allWin"));
                    // System.out.println("LOSE COUNT: " + rs.getInt("allWin"));
                }
            }

            // PreparedStatement statement = db.getConnection()
            // .prepareStatement(
            // "Select count(*) as allWin from team t, match_schedule ms,
            // result r "
            // + " where t.id = ms.id_second_team and ms.id_result = r.id and
            // r.first_team_score > r.second_team_score and t.id = ?");
            // for (TeamLeaderboardDetail detail : TeamLeaderboard.teamLeaderboardList) {
            // statement.setInt(1, detail.getTeam().getId());
            // ResultSet rs = statement.executeQuery();
            // if (rs.next())
            // detail.setTotalDefeat(detail.getTotalDefeat() + rs.getInt("allWin"));
            // }
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

    private static void loadAllWin(DBConnector db) {
        PreparedStatement pstmt = null;
        try {
            pstmt = db.getConnection()
                    .prepareStatement(
                            "Select count(*) as allWin from team t, match_schedule ms, result r "
                                    + " where (t.id = ms.id_first_team and ms.id_result = r.id and r.first_team_score > r.second_team_score and t.id = ?) "
                                    + "OR (t.id = ms.id_second_team and ms.id_result = r.id and r.first_team_score < r.second_team_score and t.id = ?)");
            for (TeamLeaderboardDetail detail : TeamLeaderboard.teamLeaderboardList) {
                pstmt.setInt(1, detail.getTeam().getId());
                pstmt.setInt(2, detail.getTeam().getId());
                ResultSet rs = pstmt.executeQuery();
                if (rs.next())
                    detail.setTotalWin(detail.getTotalWin() + rs.getInt("allWin"));
            }

            // PreparedStatement statement = db.getConnection()
            // .prepareStatement(
            // "Select count(*) as allWin from team t, match_schedule ms,
            // result r "
            // + " where t.id = ms.id_second_team and ms.id_result = r.id and
            // r.first_team_score < r.second_team_score and t.id = ?");
            // for (TeamLeaderboardDetail detail : TeamLeaderboard.teamLeaderboardList) {
            // statement.setInt(1, detail.getTeam().getId());
            // ResultSet rs = statement.executeQuery();
            // if (rs.next())
            // detail.setTotalWin(detail.getTotalWin() + rs.getInt("allWin"));
            // }
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

    private static boolean versusCompare(Team i, Team j, DBConnector db) {
        int team_i_count = 0;
        int team_j_count = 0;
        PreparedStatement pstmt = null;
        try {
            pstmt = db.getConnection()
                    .prepareStatement(
                            "Select * from match_schedule ms, result r "
                                    + " where (ms.id_first_team = ? and ms.id_second_team = ? and ms.id_result = r.id) "
                                    + "OR (ms.id_first_team = ? and ms.id_second_team = ? and ms.id_result = r.id)");
            pstmt.setInt(1, i.getId());
            pstmt.setInt(2, j.getId());
            pstmt.setInt(3, j.getId());
            pstmt.setInt(4, i.getId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                if ((rs.getString("id_first_team").equals(i.getId() + "")
                        && Integer.parseInt(rs.getString("first_team_score")) > Integer
                                .parseInt(rs.getString("second_team_score")))
                        || (rs.getString("id_second_team").equals(i.getId() + "")
                                && Integer.parseInt(rs.getString("first_team_score")) < Integer
                                        .parseInt(rs.getString("second_team_score"))))
                    team_i_count++;

                else if ((rs.getString("id_first_team").equals(j.getId() + "")
                        && Integer.parseInt(rs.getString("first_team_score")) > Integer
                                .parseInt(rs.getString("second_team_score")))
                        || (rs.getString("id_second_team").equals(j.getId() + "")
                                && Integer.parseInt(rs.getString("first_team_score")) < Integer
                                        .parseInt(rs.getString("second_team_score"))))
                    team_j_count++;
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
        if (team_j_count > team_i_count)
            return true;
        return false;
    }

    private static void swapByVersus() {
        // swap by versus
        for (int i = 0; i < TeamLeaderboard.teamLeaderboardList.size() - 1; i++)
            for (int j = i + 1; j < TeamLeaderboard.teamLeaderboardList.size(); j++) {
                try {
                    if (TeamLeaderboard.teamLeaderboardList.get(i).getRank() == TeamLeaderboard.teamLeaderboardList
                            .get(j).getRank()
                            && versusCompare(TeamLeaderboard.teamLeaderboardList.get(i).getTeam(),
                                    TeamLeaderboard.teamLeaderboardList.get(j).getTeam(), DBConnector.getInstance()))
                        Collections.swap(TeamLeaderboard.teamLeaderboardList, i, j);
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        int tmpRank = 1;
        for (int i = 0; i < TeamLeaderboard.teamLeaderboardList.size(); i++)
            try {
                if (i == 0 || (!versusCompare(TeamLeaderboard.teamLeaderboardList.get(i).getTeam(),
                        TeamLeaderboard.teamLeaderboardList.get(i - 1).getTeam(), DBConnector.getInstance())
                        && (TeamLeaderboard.teamLeaderboardList.get(i).getRank() == TeamLeaderboard.teamLeaderboardList
                                .get(i - 1).getRank()
                                || TeamLeaderboard.teamLeaderboardList.get(i).getRank() == 0)))
                    TeamLeaderboard.teamLeaderboardList.get(i).setRank(tmpRank);
                else
                    TeamLeaderboard.teamLeaderboardList.get(i).setRank(++tmpRank);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }

    private static void swapByTotalGoal() {
        // swap by totalGoal
        for (int i = 0; i < TeamLeaderboard.teamLeaderboardList.size() - 1; i++)
            for (int j = i + 1; j < TeamLeaderboard.teamLeaderboardList.size(); j++) {
                if (TeamLeaderboard.teamLeaderboardList.get(i).getTotalGoal() < TeamLeaderboard.teamLeaderboardList
                        .get(j).getTotalGoal()
                        && TeamLeaderboard.teamLeaderboardList.get(i).getRank() == TeamLeaderboard.teamLeaderboardList
                                .get(j).getRank())
                    Collections.swap(TeamLeaderboard.teamLeaderboardList, i, j);
            }

        int tmpRank = 1;
        for (int i = 0; i < TeamLeaderboard.teamLeaderboardList.size(); i++)
            if (i == 0 || (TeamLeaderboard.teamLeaderboardList.get(i)
                    .getTotalGoal() == TeamLeaderboard.teamLeaderboardList.get(i - 1).getTotalGoal()
                    && (TeamLeaderboard.teamLeaderboardList.get(i).getRank() == TeamLeaderboard.teamLeaderboardList
                            .get(i - 1).getRank()
                            || TeamLeaderboard.teamLeaderboardList.get(i).getRank() == 0)))
                TeamLeaderboard.teamLeaderboardList.get(i).setRank(tmpRank);
            else
                TeamLeaderboard.teamLeaderboardList.get(i).setRank(++tmpRank);
    }

    private static void swapByDifference() {
        // swap by difference
        for (int i = 0; i < TeamLeaderboard.teamLeaderboardList.size() - 1; i++)
            for (int j = i + 1; j < TeamLeaderboard.teamLeaderboardList.size(); j++) {
                if (TeamLeaderboard.teamLeaderboardList.get(i)
                        .getDifference() < TeamLeaderboard.teamLeaderboardList.get(j).getDifference()
                        && TeamLeaderboard.teamLeaderboardList.get(i).getRank() == TeamLeaderboard.teamLeaderboardList
                                .get(j).getRank())
                    Collections.swap(TeamLeaderboard.teamLeaderboardList, i, j);
            }

        int tmpRank = 1;
        for (int i = 0; i < TeamLeaderboard.teamLeaderboardList.size(); i++)
            if (i == 0 || (TeamLeaderboard.teamLeaderboardList.get(i)
                    .getDifference() == TeamLeaderboard.teamLeaderboardList.get(i - 1).getDifference()
                    && (TeamLeaderboard.teamLeaderboardList.get(i).getRank() == TeamLeaderboard.teamLeaderboardList
                            .get(i - 1).getRank()
                            || TeamLeaderboard.teamLeaderboardList.get(i).getRank() == 0)))
                TeamLeaderboard.teamLeaderboardList.get(i).setRank(tmpRank);
            else
                TeamLeaderboard.teamLeaderboardList.get(i).setRank(++tmpRank);
    }

    private static void swapByScore() {
        // swap by score
        for (int i = 0; i < TeamLeaderboard.teamLeaderboardList.size() - 1; i++)
            for (int j = i + 1; j < TeamLeaderboard.teamLeaderboardList.size(); j++) {
                if (TeamLeaderboard.teamLeaderboardList.get(i).getRankScore() < TeamLeaderboard.teamLeaderboardList
                        .get(j).getRankScore()
                        && TeamLeaderboard.teamLeaderboardList.get(i).getRank() == TeamLeaderboard.teamLeaderboardList
                                .get(j).getRank())
                    Collections.swap(TeamLeaderboard.teamLeaderboardList, i, j);
            }

        int tmpRank = 1;
        for (int i = 0; i < TeamLeaderboard.teamLeaderboardList.size(); i++)
            if (i == 0 || (TeamLeaderboard.teamLeaderboardList.get(i)
                    .getRankScore() == TeamLeaderboard.teamLeaderboardList.get(i - 1).getRankScore()
                    && (TeamLeaderboard.teamLeaderboardList.get(i).getRank() == TeamLeaderboard.teamLeaderboardList
                            .get(i - 1).getRank()
                            || TeamLeaderboard.teamLeaderboardList.get(i).getRank() == 0)))
                TeamLeaderboard.teamLeaderboardList.get(i).setRank(tmpRank);
            else
                TeamLeaderboard.teamLeaderboardList.get(i).setRank(++tmpRank);
    }

    private static void saveData(DBConnector db) {
        PreparedStatement pstmt = null;
        try {
            pstmt = db.getConnection()
                    .prepareStatement("INSERT INTO team_leaderboard VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
            for (TeamLeaderboardDetail detail : TeamLeaderboard.teamLeaderboardList) {
                pstmt.setInt(1, detail.getIdLeaderboard());
                pstmt.setInt(2, detail.getTeam().getId());
                pstmt.setInt(3, detail.getTotalWin());
                pstmt.setInt(4, detail.getTotalDefeat());
                pstmt.setInt(5, detail.getTotalTire());
                pstmt.setInt(6, detail.getDifference());
                pstmt.setInt(7, detail.getRank());
                pstmt.setInt(8, detail.getRankScore());
                pstmt.setInt(9, detail.getTotalGoal());

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
