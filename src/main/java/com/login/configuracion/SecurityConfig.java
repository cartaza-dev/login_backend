package com.login.configuracion;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.login.servicioImpl.UserDetailsServicelmpl;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private UserDetailsServicelmpl customerDetailsService;
	
	@Autowired
	private JwtAuthenticationFilter jwtFilter;
	
	
	  @Bean
	    public PasswordEncoder passwordEncoder(){
	        return NoOpPasswordEncoder.getInstance();
	        
	    }
	 
	  
	  @Bean
	  protected SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)throws Exception{
	  httpSecurity.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
	  .and()
	  .csrf().disable()
	  .authorizeHttpRequests()
	  .requestMatchers("/generate-token","/usuarios", "/usuarios/home","/enviarcorreo","usuarios/guardarUsuario", "validar-cuenta").permitAll()
	  .anyRequest().authenticated()
	  .and()
	  .exceptionHandling()
	  .and()
	  .sessionManagement()
	  .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	  httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	  return httpSecurity.build();

	  }

	  @Bean
	  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
	  return authenticationConfiguration.getAuthenticationManager();
	  }
	

	  @Bean
	  public WebMvcConfigurer corsConfigurer() {
	      return new WebMvcConfigurer() {
	          @Override
	          public void addCorsMappings(CorsRegistry registry) {
	              registry.addMapping("/**").allowedOrigins("*");
	          }
	      };
	  }
}