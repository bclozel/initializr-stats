package io.spring.sample.dashboard;

import io.spring.sample.dashboard.stats.ReverseLookupClient;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

@Component
class ReverseLookupHealthIndicator extends AbstractHealthIndicator {

	private final ReverseLookupClient client;

	public ReverseLookupHealthIndicator(ReverseLookupClient client) {
		this.client = client;
	}

	@Override
	protected void doHealthCheck(Health.Builder builder) {
		client.freeReverseLookup("10.10.10.10");
		builder.up();
	}

}
