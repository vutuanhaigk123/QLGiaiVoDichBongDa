package model;

public class Priority {

	private int id, orderPriority;
	private String name;
	
	public Priority(int id, int orderPriority, String name) {
		super();
		this.id = id;
		this.orderPriority = orderPriority;
		this.name = name;
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
