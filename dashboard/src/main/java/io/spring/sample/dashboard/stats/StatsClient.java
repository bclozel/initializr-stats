package io.spring.sample.dashboard.stats;

import java.util.Arrays;
import java.util.List;

import io.spring.sample.dashboard.stats.support.Event;
import io.spring.sample.dashboard.stats.support.GenerationStatistics;
import io.spring.sample.dashboard.stats.support.GeneratorClient;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class StatsClient {

	private final RestTemplate client;

	public StatsClient(RestTemplateBuilder builder) {
		this.client = builder.rootUri("http://localhost:8081").build();
	}

	public GenerationStatistics fetchGenerationStats(String fromDate, String toDate) {
		return this.client.getForObject("/statistics/{from}/{to}", GenerationStatistics.class, fromDate, toDate);
	}

	public List<Event> fetchEvents(String fromDate, String toDate) {
		return Arrays.asList(this.client.getForObject("/events/{from}/{to}", Event[].class, fromDate, toDate));
	}

	public List<GeneratorClient> fetchGeneratorClients(String fromDate, String toDate) {
		return Arrays.asList(this.client.getForObject("/top-ips/{from}/{to}", GeneratorClient[].class, fromDate, toDate));
	}

}
