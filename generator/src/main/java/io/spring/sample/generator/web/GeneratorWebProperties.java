package io.spring.sample.generator.web;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Stephane Nicoll
 */
@ConfigurationProperties("initializr.stats.generator.web")
public class GeneratorWebProperties {

	private final Reverselookup reverselookup = new Reverselookup();

	public Reverselookup getReverselookup() {
		return this.reverselookup;
	}

	public static class Reverselookup {

		/**
		 * Number of calls per time window.
		 */
		private int capacity = 25;

		/**
		 * Duration of the time window.
		 */
		private Duration period = Duration.ofSeconds(10);

		public int getCapacity() {
			return this.capacity;
		}

		public void setCapacity(int capacity) {
			this.capacity = capacity;
		}

		public Duration getPeriod() {
			return this.period;
		}

		public void setPeriod(Duration period) {
			this.period = period;
		}

	}

}
