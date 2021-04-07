package io.spring.sample.generator;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

public class DateTimeRange {

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime from;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	private LocalDateTime to;

	public DateTimeRange(LocalDateTime from, LocalDateTime to) {
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
