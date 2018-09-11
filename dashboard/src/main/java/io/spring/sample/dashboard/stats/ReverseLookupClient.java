package io.spring.sample.dashboard.stats;

import io.spring.sample.dashboard.stats.support.ReverseLookupDescriptor;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ReverseLookupClient {

	private final RestTemplate client;

	public ReverseLookupClient(RestTemplateBuilder builder) {
		this.client = builder.build();
	}

	public ReverseLookupDescriptor freeReverseLookup(String ip) {
		return this.client.getForObject("http://localhost:8081/reverse-lookup/free/{ip}", ReverseLookupDescriptor.class, ip);
	}

	public ReverseLookupDescriptor payingReverseLookup(String ip) {
		return this.client.getForObject("http://localhost:8081/reverse-lookup/costly/{ip}", ReverseLookupDescriptor.class, ip);
	}
}
