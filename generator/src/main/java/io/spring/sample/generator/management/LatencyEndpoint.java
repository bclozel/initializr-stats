package io.spring.sample.generator.management;


import io.spring.sample.generator.Generator;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Custom {@link Endpoint} to control the latency of reverse lookups.
 *
 * @author Stephane Nicoll
 */
@Component
@Endpoint(id = "latency")
public class LatencyEndpoint {

	private final Generator generator;

	public LatencyEndpoint(Generator generator) {
		this.generator = generator;
	}

	@ReadOperation
	public Generator.Latency currentLatency() {
		return this.generator.getLatency();
	}

	@WriteOperation
	public void updateLatency(@Nullable Float ratio, @Nullable Integer latencyMin,
			@Nullable Integer latencyMax) {
		this.generator.updateLatency(ratio, latencyMin, latencyMax);
	}

}
