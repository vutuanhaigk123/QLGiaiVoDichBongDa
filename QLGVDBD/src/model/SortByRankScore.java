package model;

import java.sql.Date;

public class SortByRankScore implements SortStrategy {

	@Override
	public int sort(TeamLeaderboardDetail o1, TeamLeaderboardDetail o2, Date time) {
//		System.out.println("Sắp xếp theo điểm");
		if(o1.getRankScore() > o2.getRankScore()){
			return 1;
		}
		else if(o1.getRankScore() < o2.getRankScore()){
			return -1;
		}
		return 0;
	}

}
