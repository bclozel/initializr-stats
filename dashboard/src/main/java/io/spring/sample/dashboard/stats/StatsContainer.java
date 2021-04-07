package io.spring.sample.dashboard.stats;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import io.spring.sample.dashboard.stats.support.Event;
import io.spring.sample.dashboard.stats.support.GenerationStatistics;
import io.spring.sample.dashboard.stats.support.ReverseLookupDescriptor;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class StatsContainer {

	private String fromDate;

	private String toDate;

	private MultiValueMap<String, ProjectCreations> creations;

	private List<Annotation> annotations;

	private List<Client> topClients;

	StatsContainer(String fromDate, String toDate, MultiValueMap<String, ProjectCreations> creations,
			List<Annotation> annotations, List<Client> topClients) {
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.creations = creations;
		this.annotations = annotations;
		this.topClients = topClients;
	}

	public String getFromDate() {
		return fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public MultiValueMap<String, ProjectCreations> getCreations() {
		return creations;
	}

	public List<Annotation> getAnnotations() {
		return annotations;
	}

	public List<Client> getTopClients() {
		return topClients;
	}

	public static Builder range(LocalDate from , LocalDate to) {
		return new Builder(from, to);
	}

	public static Builder range(String from , String to) {
		return new Builder(from, to);
	}

	public static class Builder {

		private String fromDate;

		private String toDate;

		private MultiValueMap<String, ProjectCreations> creations = new LinkedMultiValueMap<>();

		private List<Annotation> annotations = new ArrayList<>();

		private List<Client> topClients = new ArrayList<>();

		Builder(LocalDate fromDate, LocalDate toDate) {
			this.fromDate = fromDate.toString();
			this.toDate = toDate.toString();
		}

		Builder(String fromDate, String toDate) {
			this.fromDate = fromDate;
			this.toDate = toDate;
		}

		public Builder addSeries(MultiValueMap<String, GenerationStatistics.Record> series) {
			series.forEach((name, records) -> {
				records.stream()
						.map(record -> new ProjectCreations(record.getDate().toString(), record.getProjectsCount()))
						.forEach(creation -> this.creations.add(name, creation));
			});
			return this;
		}

		public StatsContainer build() {
			return new StatsContainer(this.fromDate, this.toDate, this.creations, this.annotations, this.topClients);
		}

		public Builder addAnnotations(List<Event> events) {
			this.annotations.addAll(events.stream()
					.map(event -> new Annotation(event.getName(), event.getDate().toString()))
					.collect(Collectors.toList()));
			return this;
		}

		public Builder addTopClients(List<ReverseLookupDescriptor> resolvedIps) {
			this.topClients.addAll(resolvedIps.stream()
					.map(lookup -> new Client(lookup.getIp(), lookup.getDomainName()))
			.collect(Collectors.toList()));
			return this;
		}
	}
}
