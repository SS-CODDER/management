package com.school.management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.school.management.service.CustomUserDetailsService;

@Configuration
public class SecurityConfig {

	private final CustomUserDetailsService userDetailsService;

	public SecurityConfig(CustomUserDetailsService userDetailsService) {

		this.userDetailsService = userDetailsService;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http

				.authorizeHttpRequests(auth -> auth

						.requestMatchers("/", "/login", "/contact", "/contact/save", "/css/**", "/js/**", "/images/**")
						.permitAll()

						.requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")

						.requestMatchers("/teacher/**").hasAuthority("ROLE_TEACHER")

						.requestMatchers("/student/**").hasAuthority("ROLE_STUDENT")

						.anyRequest().authenticated())

				.formLogin(form -> form

						.loginPage("/login")

						.loginProcessingUrl("/login")

						.defaultSuccessUrl("/dashboard", true)

						.failureUrl("/login?error=true")

						.permitAll())

				.logout(logout -> logout

						.logoutUrl("/logout")

						.logoutSuccessUrl("/login?logout")

						.invalidateHttpSession(true)

						.deleteCookies("JSESSIONID")

						.permitAll())

				.exceptionHandling(ex ->

				ex.accessDeniedPage("/403"));

		return http.build();
	}
}