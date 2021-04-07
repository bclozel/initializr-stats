package io.spring.sample.generator;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class DateRange {

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate from;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate to;

	public DateRange(LocalDate from, LocalDate to) {
		this.from = from;
		this.to = to;
	}

	public DateRange() {
	}

	public LocalDate getFrom() {
		return this.from;
	}

	public void setFrom(LocalDate from) {
		this.from = from;
	}

	public LocalDate getTo() {
		return this.to;
	}

	public void setTo(LocalDate to) {
		this.to = to;
	}


	public boolean match(DateRange anotherRange) {
		boolean rangeIncluded = isRangeIncluded(anotherRange);
		boolean withinRange = isWithinRange(anotherRange);
		return rangeIncluded || withinRange;
	}

	public boolean match(LocalDate date) {
		return isAfterOrEqual(date, from)
				&& isBeforeOrEqual(date, to);
	}

	private boolean isRangeIncluded(DateRange anotherRange) {
		return isBeforeOrEqual(anotherRange.getFrom(), getFrom())
				&& isAfterOrEqual(anotherRange.getTo(), getTo());
	}

	private boolean isWithinRange(DateRange anotherRange) {
		return isWithinRange(anotherRange.getFrom())
				|| (isWithinRange(anotherRange.getTo()));
	}

	private boolean isWithinRange(LocalDate date) {
		return isAfterOrEqual(date, getFrom())
				&& isBeforeOrEqual(date, getTo());
	}

	private boolean isAfterOrEqual(LocalDate left, LocalDate right) {
		return left.isEqual(right) || left.isAfter(right);
	}

	private boolean isBeforeOrEqual(LocalDate left, LocalDate right) {
		return left.isEqual(right) || left.isBefore(right);
	}

	@Override
	public String toString() {
		return "DateRange{" + "from=" + from
				+ ", to=" + to
				+ '}';
	}

}
