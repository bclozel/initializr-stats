package io.spring.sample.dashboard.stats;

import io.spring.sample.dashboard.stats.support.ReverseLookupDescriptor;
import reactor.core.publisher.Mono;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ReverseLookupClient {

	private final WebClient client;

	public ReverseLookupClient(WebClient.Builder builder) {
		this.client = builder.build();
	}

	public Mono<ReverseLookupDescriptor> freeReverseLookup(String ip) {
		return this.client.get().uri("http://localhost:8081/reverse-lookup/free/{ip}", ip)
				.retrieve().bodyToMono(ReverseLookupDescriptor.class);
	}

	public Mono<ReverseLookupDescriptor> payingReverseLookup(String ip) {
		return this.client.get().uri("http://localhost:8081/reverse-lookup/costly/{ip}", ip)
				.retrieve().bodyToMono(ReverseLookupDescriptor.class);
	}
}
