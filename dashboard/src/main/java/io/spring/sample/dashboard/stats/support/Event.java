package io.spring.sample.dashboard.stats.support;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Event {

	private String name;

	private LocalDate date;

	private Type type;

	@JsonCreator
	public Event(@JsonProperty("name") String name, @JsonProperty("date") LocalDate date, @JsonProperty("type") Type type) {
		this.name = name;
		this.date = date;
		this.type = type;
	}

	public Event() {
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDate() {
		return this.date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Type getType() {
		return this.type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public enum Type {

		RELEASE,

		CONFERENCE,

		SOCIAL_OUTAGE,

		TOOLS_OUTAGE

	}

}
