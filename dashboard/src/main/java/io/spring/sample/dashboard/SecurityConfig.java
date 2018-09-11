package io.spring.sample.dashboard;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.anyRequest().authenticated()
				.and().formLogin().loginPage("/login").permitAll()
				.and().httpBasic();
	}

	@Bean
	public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
		return new InMemoryUserDetailsManager(
				User.builder().username("user").password("user").roles("USER").build(),
				User.builder().username("admin").password("admin").roles("USER", "ADMIN").build()
		);
	}

}	