package com.springsecuritypractice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
	
	@Bean
	public UserDetailsService userDetailsService() {
		
		UserDetails normalUser = User.withUsername("xavier")
		    .password(passwordEncoder().encode("xavier"))
		    .roles("NORMAL")
		    .build();
		
		UserDetails adminUser = User.withUsername("francis")
	    .password(passwordEncoder().encode("francis"))
	    .roles("ADMIN")
	    .build();
		
		InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager(normalUser,adminUser);
		return inMemoryUserDetailsManager;
	}
	
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable()
		.authorizeHttpRequests((requests)->requests.requestMatchers("/home/normal").permitAll()
				.requestMatchers("/home/admin").authenticated()
				
//				.requestMatchers("/home/admin")
//				.hasRole("ADMIN")
//				.requestMatchers("/home/normal")
//				.hasRole("NORMAL")
				
				).formLogin(Customizer.withDefaults())
		         .formLogin(Customizer.withDefaults());

		return httpSecurity.build();
		
	}

}
