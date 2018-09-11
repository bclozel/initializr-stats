package io.spring.sample.generator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("initializr.stats.generator")
public class GeneratorProperties {

	private List<DataSet> dataSets = new ArrayList<>();

	private List<Release> releases = new ArrayList<>();

	private List<Event> events = new ArrayList<>();

	public List<DataSet> getDataSets() {
		return this.dataSets;
	}

	public void setDataSets(List<DataSet> dataSets) {
		this.dataSets = dataSets;
	}

	public List<Release> getReleases() {
		return this.releases;
	}

	public void setReleases(List<Release> releases) {
		this.releases = releases;
	}

	public List<Event> getEvents() {
		return this.events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}
	
}
