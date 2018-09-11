package io.spring.sample.generator;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class DataSet {

	private DateRange range;

	private final Data data = new Data();

	public int getBase(LocalDate localDate) {
		return 100; // TODO
	}

	public DateRange getRange() {
		return this.range;
	}

	public void setRange(DateRange range) {
		this.range = range;
	}

	public Data getData() {
		return this.data;
	}

	public static class Data {

		private int monday;

		private int tuesday;

		private int wednesday;

		private int thursday;

		private int friday;

		private int saturday;

		private int sunday;

		public int get(DayOfWeek dayOfWeek) {
			switch (dayOfWeek) {
				case MONDAY: return this.monday;
				case TUESDAY: return this.tuesday;
				case WEDNESDAY: return this.wednesday;
				case THURSDAY: return this.thursday;
				case FRIDAY: return this.friday;
				case SATURDAY: return this.saturday;
				case SUNDAY: return this.sunday;
				default: throw new IllegalArgumentException();
			}
		}

		public int getMonday() {
			return this.monday;
		}

		public void setMonday(int monday) {
			this.monday = monday;
		}

		public int getTuesday() {
			return this.tuesday;
		}

		public void setTuesday(int tuesday) {
			this.tuesday = tuesday;
		}

		public int getWednesday() {
			return this.wednesday;
		}

		public void setWednesday(int wednesday) {
			this.wednesday = wednesday;
		}

		public int getThursday() {
			return this.thursday;
		}

		public void setThursday(int thursday) {
			this.thursday = thursday;
		}

		public int getFriday() {
			return this.friday;
		}

		public void setFriday(int friday) {
			this.friday = friday;
		}

		public int getSaturday() {
			return this.saturday;
		}

		public void setSaturday(int saturday) {
			this.saturday = saturday;
		}

		public int getSunday() {
			return this.sunday;
		}

		public void setSunday(int sunday) {
			this.sunday = sunday;
		}

	}

	@Override
	public String toString() {
		return "DataSet{" + "range=" + range + '}';
	}
}
