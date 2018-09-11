package io.spring.sample.dashboard.stats.support;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.format.annotation.DateTimeFormat;

public class DateTimeRange {

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime from;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime to;

	@JsonCreator
	public DateTimeRange(@JsonProperty("from") LocalDateTime from, @JsonProperty("to") LocalDateTime to) {
		this.from = from;
		this.to = to;
	}

	public LocalDateTime getFrom() {
		return this.from;
	}

	public void setFrom(LocalDateTime from) {
		this.from = from;
	}

	public LocalDateTime getTo() {
		return this.to;
	}

	public void setTo(LocalDateTime to) {
		this.to = to;
	}

	@Override
	public String toString() {
		return "DateTimeRange{" + "from=" + from + ", to=" + to + '}';
	}

}
