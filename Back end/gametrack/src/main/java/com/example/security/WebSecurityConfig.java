package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.security.jwt.AuthEntryPointJwt;
import com.example.security.jwt.AuthTokenFilter;
import com.example.security.services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		// securedEnabled = true,
		// jsr250Enabled = true,
		prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests()
			.antMatchers("/api/auth/**").permitAll()
			
			//Allows the game controller endpoints
			.antMatchers("/games").permitAll()
			.antMatchers("/genres").permitAll()
			
			//websocket endpoints
			.antMatchers("/chat/{username}").permitAll()
			.antMatchers("/chat/**").permitAll()
			.antMatchers("/ws/**").permitAll()
			.antMatchers("/message/**").permitAll()
			.antMatchers("/addUser/**").permitAll()
			.antMatchers("/sendPrivateMessage/**").permitAll()
			.antMatchers("/addPrivateUser/**").permitAll()
			

			.antMatchers("/upload/{token}").permitAll()
			.antMatchers("/files/{id}").permitAll()
			

			.antMatchers("/games/gameRating/{id}").permitAll()
			.antMatchers("/userlist").permitAll()
			.antMatchers("/games/{id}").permitAll()
			.antMatchers("/games/title/{title}").permitAll()
			.antMatchers("/games/genre/{genre}").permitAll()
			.antMatchers("/userlist/**").permitAll()
			.antMatchers("/api/test/**").permitAll()
			
			.antMatchers("/gameapi/**").permitAll()
			
			
			.antMatchers("/addFriend").permitAll()
			.antMatchers("/getFriends/{id}").permitAll()
			.antMatchers("/getRequests/{id}").permitAll()
			.antMatchers("/acceptRequest/{id}").permitAll()
			.antMatchers("/declineRequest/{id}").permitAll()
			
			.antMatchers("/recommendations/{userId}").permitAll()
			
			.anyRequest().authenticated();
		
		
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}
