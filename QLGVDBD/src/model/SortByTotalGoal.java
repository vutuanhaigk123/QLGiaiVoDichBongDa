package model;

import java.sql.Date;

public class SortByTotalGoal implements SortStrategy{

	@Override
	public int sort(TeamLeaderboardDetail o1, TeamLeaderboardDetail o2, Date time) {
//		System.out.println("Sắp xếp theo tổng số bàn thắng");
		if(o1.getTotalGoal() > o2.getTotalGoal()){
			return 1;
		}
		else if(o1.getTotalGoal() < o2.getTotalGoal()){
			return -1;
		}
		return 0;
	}

}
