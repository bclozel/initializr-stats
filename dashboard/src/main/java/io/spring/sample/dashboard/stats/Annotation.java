package io.spring.sample.dashboard.stats;

public class Annotation {

	private String text;

	private String date;

	public Annotation(String text, String date) {
		this.text = text;
		this.date = date;
	}

	public String getText() {
		return text;
	}

	public String getDate() {
		return date;
	}

}
