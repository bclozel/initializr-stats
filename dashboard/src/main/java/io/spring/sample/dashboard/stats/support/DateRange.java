package io.spring.sample.dashboard.stats.support;

import java.time.LocalDate;

public class DateRange {

	private LocalDate from;

	private LocalDate to;

	public DateRange() {
	}

	public LocalDate getFrom() {
		return from;
	}

	public void setFrom(LocalDate from) {
		this.from = from;
	}

	public LocalDate getTo() {
		return to;
	}

	public void setTo(LocalDate to) {
		this.to = to;
	}

}
