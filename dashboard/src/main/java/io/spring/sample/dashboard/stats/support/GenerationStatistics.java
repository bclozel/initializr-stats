package io.spring.sample.dashboard.stats.support;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class GenerationStatistics {

	private DateRange range;

	private LinkedMultiValueMap<String, Record> records;

	@JsonCreator
	public GenerationStatistics(@JsonProperty("range") DateRange range,
			@JsonProperty("entries") LinkedMultiValueMap<String, Record> records) {
		this.range = range;
		this.records = records;
	}

	public DateRange getRange() {
		return this.range;
	}

	public MultiValueMap<String, Record> getRecords() {
		return this.records;
	}

	public static class Record {

		private final LocalDate date;

		private final int projectsCount;

		@JsonCreator
		public Record(@JsonProperty("date") LocalDate date, @JsonProperty("projectsCount") int projectsCount) {
			this.date = date;
			this.projectsCount = projectsCount;
		}

		public LocalDate getDate() {
			return this.date;
		}

		public int getProjectsCount() {
			return this.projectsCount;
		}

	}

}
