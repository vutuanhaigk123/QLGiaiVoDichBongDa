package model;

import java.sql.Date;
import java.sql.SQLException;

import database.DBConnector;
import database.DBTeamLeaderboardDetail;

public class SortByPairCompetitive implements SortStrategy{

	@Override
	public int sort(TeamLeaderboardDetail o1, TeamLeaderboardDetail o2, Date time) {
		try {
//			System.out.println("Sắp xếp theo đối kháng");
			return DBTeamLeaderboardDetail.compare(DBConnector.getInstance(), 
					o1.getIdTeam(), 
					o2.getIdTeam(), time);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

}
