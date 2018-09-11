package io.spring.sample.dashboard;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.boot.actuate.metrics.export.prometheus.PrometheusScrapeEndpoint;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.requestMatchers(EndpointRequest.to(InfoEndpoint.class, HealthEndpoint.class,
						PrometheusScrapeEndpoint.class)).permitAll()
				.requestMatchers(EndpointRequest.toAnyEndpoint()).hasRole("ADMIN")
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
				.anyRequest().authenticated()
				.and().formLogin().loginPage("/login").permitAll()
				.and().httpBasic();
	}

	@Bean
	@SuppressWarnings("deprecation")
	public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
		return new InMemoryUserDetailsManager(
				User.withDefaultPasswordEncoder().username("user").password("user").roles("USER").build(),
				User.withDefaultPasswordEncoder().username("admin").password("admin").roles("USER", "ADMIN").build()
		);
	}

}	