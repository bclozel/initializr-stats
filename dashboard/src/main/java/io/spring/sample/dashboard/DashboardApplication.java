package io.spring.sample.dashboard;

import io.micrometer.core.instrument.MeterRegistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DashboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(DashboardApplication.class, args);
	}

	@Bean
	public MeterRegistryCustomizer<MeterRegistry> commonTags() {
	   return registry -> registry.config()
			   .commonTags("application", "initializr-dashboard");
	}
}
