package io.spring.sample.dashboard.stats.support;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GenerationStatisticsItem {

	private final DateTimeRange range;

	private final int projectsCount;

	@JsonCreator
	public GenerationStatisticsItem(@JsonProperty("range") DateTimeRange range, @JsonProperty("projectsCount") int projectsCount) {
		this.range = range;
		this.projectsCount = projectsCount;
	}

	public DateTimeRange getRange() {
		return this.range;
	}

	public int getProjectsCount() {
		return this.projectsCount;
	}

}
