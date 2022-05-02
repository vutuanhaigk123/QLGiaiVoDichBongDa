package model;

import java.sql.Date;

public class SortByDifference implements SortStrategy {

	@Override
	public int sort(TeamLeaderboardDetail o1, TeamLeaderboardDetail o2, Date time) {
//		System.out.println("Sắp xếp theo hiệu số");
		if(o1.getDifference() > o2.getDifference()){
			return 1;
		}
		else if(o1.getDifference() < o2.getDifference()){
			return -1;
		}
		return 0;
	}

}
