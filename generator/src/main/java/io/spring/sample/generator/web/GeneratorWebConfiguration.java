package io.spring.sample.generator.web;

import java.time.Duration;
import java.util.function.Function;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableConfigurationProperties(GeneratorWebProperties.class)
public class GeneratorWebConfiguration implements WebMvcConfigurer {

	private final GeneratorWebProperties properties;

	public GeneratorWebConfiguration(GeneratorWebProperties properties) {
		this.properties = properties;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new RateLimiterHandlerInterceptor(
				bucketFactory(this.properties.getReverselookup().getCapacity(),
						this.properties.getReverselookup().getPeriod())));
	}

	private Function<String, Bucket> bucketFactory(int capacity, Duration period) {
		return (remoteAddr) -> {
			Bandwidth limit = Bandwidth.simple(capacity, period);
			return Bucket4j.builder().addLimit(limit).build();
		};
	}

}
