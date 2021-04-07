package io.spring.sample.dashboard.stats;

public class ProjectCreations {

	public ProjectCreations(String date, int count) {
		this.date = date;
		this.count = count;
	}

	private String date;

	private int count;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
