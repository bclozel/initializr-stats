package io.spring.sample.generator;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(GeneratorProperties.class)
class GeneratorConfiguration {

	private final GeneratorProperties properties;

	GeneratorConfiguration(GeneratorProperties properties) {
		this.properties = properties;
	}

	@Bean
	public Generator generatorMetadata() {
		return new Generator(this.properties.getDataSets(),
				this.properties.getReleases(), this.properties.getEvents());
	}

}
