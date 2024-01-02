package com.codeword.springboot.myfirstwebapp.security;

import static org.springframework.security.config.Customizer.withDefaults;

import java.beans.Customizer;
import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguration {

    @Bean
    public InMemoryUserDetailsManager createUserDetailsManager() {
        UserDetails userDetails1 = createNewUser("Medha", "2345");
        UserDetails userDetails2 = createNewUser("Shreyashi", "dummy");
        return new InMemoryUserDetailsManager(userDetails1, userDetails2);
    }

    private UserDetails createNewUser(String username, String password) {
        Function<String, String> passwordEncoder = input -> passwordEncoder().encode(input);

        UserDetails userDetails = User.builder()
                .passwordEncoder(passwordEncoder)
                .username(username)
                .password(password)
                .roles("USER", "ADMIN")
                .build();
        return userDetails;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

   
@Bean
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	   http.authorizeHttpRequests(
			   auth -> auth.anyRequest().authenticated());
//	   			http.formLogin(withDefaults());
//	   			http.csrf().disable();
//	   			http.headers().frameOptions().disable();
	   
			   http.formLogin(withDefaults());
			   http.csrf(csrf -> csrf.disable());
			   http.headers(header -> header.
			    		frameOptions(frameOptions -> frameOptions.disable()));
	   			return http.build();
   }

    
    
}
