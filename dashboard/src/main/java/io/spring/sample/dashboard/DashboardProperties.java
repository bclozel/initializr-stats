package io.spring.sample.dashboard;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("dashboard")
public class DashboardProperties {

	private ReverseLookup reverseLookup = new ReverseLookup();

	public ReverseLookup getReverseLookup() {
		return reverseLookup;
	}

	public static class ReverseLookup {

		/**
		 * Read timeout for the IP resolver API.
		 */
		private Duration timeout = Duration.ofMillis(500);

		public Duration getTimeout() {
			return timeout;
		}

		public void setTimeout(Duration timeout) {
			this.timeout = timeout;
		}
	}

}
