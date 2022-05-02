package model;

import java.sql.Date;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class TeamLeaderboard extends Leaderboard {
	
	private Vector<TeamLeaderboardDetail> teamList;

	public Vector<TeamLeaderboardDetail> getTeamList() {
		return teamList;
	}

	public void setTeamList(Vector<TeamLeaderboardDetail> teamList) {
		this.teamList = teamList;
	}


	@Override
	public void createReport() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sort(Date time) {

		Vector<Priority> priorityList = new Vector<Priority>();
		priorityList.addAll(Regulation.priorityList);
		priorityList.sort(new Comparator<Priority>() {

			@Override
			public int compare(Priority o1, Priority o2) {
				if(o1.getOrderPriority() < o2.getOrderPriority()){
					return -1;
				}
				else if(o1.getOrderPriority() > o2.getOrderPriority()){
					return 1;
				}
				return 0;
			}
		});
		
		Collections.sort(teamList, new Comparator<TeamLeaderboardDetail>() {

			@Override
			public int compare(TeamLeaderboardDetail o1, TeamLeaderboardDetail o2) {
				int numOfCriteria = Regulation.priorityList.size();
				
				Regulation.priorityList.sort(new Comparator<Priority>() {

					@Override
					public int compare(Priority o1, Priority o2) {
						if(o1.getOrderPriority() < o2.getOrderPriority()){
							return -1;
						}
						else if(o1.getOrderPriority() > o2.getOrderPriority()){
							return 1;
						}
						return 0;
					}
				});
				for (int i = 0; i < numOfCriteria; i++) {
					int compareResult = Regulation.priorityList.get(i).compare(o1, o2, time);
					if(compareResult != 0){
						return compareResult;
					}
					else {
						if(i == numOfCriteria - 1){
							break;
						}
						continue;
					}
				}
				return 0;
			}
		});
		Collections.reverse(teamList);
		for (int i = 0; i < teamList.size(); i++) {
			teamList.get(i).setRank(i + 1);
		}
	}
	
}
