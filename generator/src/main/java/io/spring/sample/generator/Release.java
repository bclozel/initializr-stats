package io.spring.sample.generator;

public class Release {

	private DateRange range;

	private final Data data = new Data();

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

		private String maintenance;

		private String current;

		private String next;

		public String getMaintenance() {
			return this.maintenance;
		}

		public void setMaintenance(String maintenance) {
			this.maintenance = maintenance;
		}

		public String getCurrent() {
			return this.current;
		}

		public void setCurrent(String current) {
			this.current = current;
		}

		public String getNext() {
			return this.next;
		}

		public void setNext(String next) {
			this.next = next;
		}

	}

}
