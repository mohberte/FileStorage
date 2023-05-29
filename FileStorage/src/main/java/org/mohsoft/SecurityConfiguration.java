package org.mohsoft;

import java.util.Arrays;
import org.mohsoft.repository.UserRepository;
import org.mohsoft.util.CustomAuthenticationFailureHandler;
import org.mohsoft.util.CustomAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired
	UserRepository userRepository;
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity)
      throws Exception { 
		httpSecurity.sessionManagement()
	    .sessionFixation().migrateSession()
	    .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
	    .invalidSessionUrl("/login?error=sessionExpired")
	    .maximumSessions(1)
	    .maxSessionsPreventsLogin(false)
	    .expiredUrl("/login?error=sessionExpired")
		  .and()
		  .sessionFixation().migrateSession().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
		.and()
        .authorizeRequests()
            .requestMatchers("/logout","/about","/customLogout",
            		"/registration","/","/index","/login",
            		"/accessDenied","/navbar.html","/navbar","/css/**", "/js/**").permitAll()
            .requestMatchers("/lockedAccounts").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/files/**").permitAll()
            .requestMatchers("/userContent").hasRole("USER")
            .requestMatchers("/customLogin","/logoutSuccess","/shared/**").hasAnyRole("USER", "ADMIN")
            .anyRequest().authenticated()
            .and()
        .formLogin()
            .loginPage("/login")
            .loginProcessingUrl("/customLogin")
            .successHandler(new CustomAuthenticationSuccessHandler(userRepository))
            .failureHandler( new CustomAuthenticationFailureHandler(userRepository))
            .permitAll()
            .and()
        .logout()
        .logoutUrl("/customLogout") // specify the logout URL
        .invalidateHttpSession(true) // invalidate the HTTP session
        .deleteCookies("JSESSIONID") // delete the session cookie
        .logoutSuccessUrl("/logoutSuccess") // redirect to login page with a logout parameter
            
            
  //          .defaultSuccessUrl("/oauth2LoginSuccess")a
            .permitAll()
            .and()
            .exceptionHandling()
                .accessDeniedPage("/accessDenied.html");
	return httpSecurity.build();
	
	}
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	return authenticationConfiguration.getAuthenticationManager();
	}
	public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
        .allowedOrigins("http://localhost:8080/");
    }
   @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        configuration.setAllowCredentials(true);
        configuration.addAllowedHeader("Authorization");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);
        return source;
    }
}