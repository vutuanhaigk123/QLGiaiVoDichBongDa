package model;

import java.sql.Date;

public class Priority {

	private int id, orderPriority;
	private String name;
	private SortStrategy strategy;

	// CODE theo du lieu trong DB
	public static final int BY_RANK_SCORE = 1;
	public static final int BY_DIFFERENCE = 2;
	public static final int BY_TOTAL_GOAL = 3;
	public static final int BY_PAIR_COMPETITIVE = 4;

	public Priority(int id, int orderPriority, String name) {
		super();
		setStrategy(id);
		this.id = id;
		this.orderPriority = orderPriority;
		this.name = name;
	}

	private void setStrategy(int strategyId){
		switch (strategyId) {
			case BY_RANK_SCORE:
				strategy = new SortByRankScore();
				break;
			case BY_DIFFERENCE:
				strategy = new SortByDifference();
				break;
			case BY_TOTAL_GOAL:
				strategy = new SortByTotalGoal();
				break;
			case BY_PAIR_COMPETITIVE:
				strategy = new SortByPairCompetitive();
				break;
			default:
				break;
		}
	}

	public int compare(TeamLeaderboardDetail o1, TeamLeaderboardDetail o2, Date time){
		return strategy.sort(o1, o2, time);
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrderPriority() {
		return orderPriority;
	}
	public void setOrderPriority(int orderPriority) {
		this.orderPriority = orderPriority;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
