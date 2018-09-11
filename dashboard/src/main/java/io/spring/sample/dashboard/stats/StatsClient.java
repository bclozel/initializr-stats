package io.spring.sample.dashboard.stats;

import io.spring.sample.dashboard.stats.support.Event;
import io.spring.sample.dashboard.stats.support.GenerationStatistics;
import io.spring.sample.dashboard.stats.support.GenerationStatisticsItem;
import io.spring.sample.dashboard.stats.support.GeneratorClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class StatsClient {

	private final WebClient client;

	public StatsClient(WebClient.Builder builder) {
		this.client = builder.baseUrl("http://localhost:8081").build();
	}

	public Mono<GenerationStatistics> fetchGenerationStats(String fromDate, String toDate) {
		return this.client.get().uri("/statistics/{from}/{to}", fromDate, toDate)
				.retrieve().bodyToMono(GenerationStatistics.class);
	}

	public Flux<Event> fetchEvents(String fromDate, String toDate) {
		return this.client.get().uri("/events/{from}/{to}", fromDate, toDate)
				.retrieve().bodyToFlux(Event.class);
	}

	public Flux<GeneratorClient> fetchGeneratorClients(String fromDate, String toDate) {
		return this.client.get().uri("/top-ips/{from}/{to}", fromDate, toDate)
				.retrieve().bodyToFlux(GeneratorClient.class);
	}

	public Flux<GenerationStatisticsItem> liveStats() {
		return this.client.get().uri("/live-statistics")
				.retrieve().bodyToFlux(GenerationStatisticsItem.class);
	}

}
