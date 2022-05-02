package model;

import java.sql.Date;

public interface SortStrategy {
	public int sort(TeamLeaderboardDetail o1, TeamLeaderboardDetail o2, Date time);
}
