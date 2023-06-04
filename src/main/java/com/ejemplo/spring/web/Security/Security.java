package com.ejemplo.spring.web.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled=true)
public class Security {

	@Bean
	AuthenticationManager authManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain filterSecurity(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((ht) -> ht
                .requestMatchers("/productos/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN", "ROLE_EJECUTIVO")
                .requestMatchers("/usuarios/**").hasAuthority("ROLE_ADMIN").anyRequest().authenticated())
                .formLogin((login) -> login
                		.defaultSuccessUrl("/productos/index", true)
                		.failureUrl("/login?error=true"))
                .logout(withDefaults());
		
		return http.build();
	}
	
	
	@Bean
	WebSecurityCustomizer SecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/css/**","/js/**","/images/**");
	}
}
