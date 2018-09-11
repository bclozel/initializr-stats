package io.spring.sample.generator;

public class GenerationStatisticsItem {

	private final DateTimeRange range;

	private final int projectsCount;

	GenerationStatisticsItem(DateTimeRange range, int projectsCount) {
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
