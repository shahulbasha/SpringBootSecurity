package org.security.app.security;

import java.util.Arrays;

import org.modelmapper.ModelMapper;
import org.security.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


	@Autowired
	UserService usersService;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	ModelMapper mapper;
	

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().authorizeRequests().antMatchers("/h2/**").permitAll()
									   .antMatchers("/signup").permitAll()
								       .antMatchers("/login").permitAll()
								       .anyRequest().authenticated()
								       .and().addFilter(getAuthenticationFilter());
		http.csrf().disable();
        http.headers().frameOptions().disable();
	}
	
	private AuthenticationFilter getAuthenticationFilter() throws Exception
	{
		AuthenticationFilter authenticationFilter = new AuthenticationFilter(usersService, authenticationManager(),mapper);
		//authenticationFilter.setAuthenticationManager(authenticationManager()); 
		authenticationFilter.setFilterProcessesUrl("/login");
		return authenticationFilter;
	}
	
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usersService).passwordEncoder(bCryptPasswordEncoder);
    }
    

    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("token"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    


}
