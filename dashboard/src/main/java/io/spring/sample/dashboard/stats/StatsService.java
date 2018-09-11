package io.spring.sample.dashboard.stats;

import java.util.List;
import java.util.stream.Collectors;

import io.spring.sample.dashboard.stats.support.Event;
import io.spring.sample.dashboard.stats.support.GenerationStatistics;
import io.spring.sample.dashboard.stats.support.GeneratorClient;
import io.spring.sample.dashboard.stats.support.ReverseLookupDescriptor;

import org.springframework.stereotype.Service;

@Service
public class StatsService {

	private final StatsClient statsClient;

	private final ReverseLookupClient lookupClient;

	public StatsService(StatsClient statsClient, ReverseLookupClient lookupClient) {
		this.statsClient = statsClient;
		this.lookupClient = lookupClient;
	}

	public StatsContainer fetchStats(String fromDate, String toDate) {

		StatsContainer.Builder builder = StatsContainer.range(fromDate, toDate);

		GenerationStatistics generationStats = statsClient.fetchGenerationStats(fromDate, toDate).block();
		builder.addSeries(generationStats.getRecords());

		List<Event> events = statsClient.fetchEvents(fromDate, toDate).collectList().block();
		builder.addAnnotations(events);

		List<GeneratorClient> clients = statsClient.fetchGeneratorClients(fromDate, toDate).collectList().block();
		List<ReverseLookupDescriptor> resolvedIps = clients
				.parallelStream()
				.map(client -> lookupClient.freeReverseLookup(client.getIp()).block())
				.collect(Collectors.toList());
		builder.addTopClients(resolvedIps);

		return builder.build();
	}

}
