package com.example.demo.registration.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.registration.service.CustomUserDetailsService;
import com.example.demo.registration.service.UserService;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService; // solution 1

    @Autowired
    private CustomUserDetailsService userDetailsService; // solution 2
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	AuthenticationFailureHandler authenticationFailureHandler;
	
//	@Bean
//	public DaoAuthenticationProvider authenticationProvider() {
//		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
//		auth.setUserDetailsService(userService);
//		auth.setPasswordEncoder(passwordEncoder);
//		return auth;
//	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//auth.authenticationProvider(authenticationProvider());
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
			.antMatchers("/webjars/**");     // Spring Security should completely ignore URLs starting with /resources/
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				//the order of the antMatchers() elements is significant; the more specific rules need to come first, followed by the more general ones.
				.antMatchers("/welcome").authenticated()
				.antMatchers("/admin").hasAuthority("ADMIN")
				.antMatchers("/manager").hasAuthority("Manager")
				.antMatchers("/employee").hasAuthority("Employee")
				.antMatchers("/common").hasAnyAuthority("Employee,Manager,Admin")
				.antMatchers("/js/**","/css/**","/img/**", "/favicon.ico").permitAll()
                .antMatchers("/","/register","/saveUser","/login").permitAll()
				.anyRequest().authenticated()
			.and()
				.formLogin()
				.loginPage("/login")
				//.usernameParameter("username")   // specifies the HTTP parameter to look for the username when performing authentication. Default is "username".
				//.loginProcessingUrl("/perform_login")
			    .defaultSuccessUrl("/welcome",true)
			    .failureHandler(authenticationFailureHandler)
			     //.failureUrl("/login.html?error=true")
			    .permitAll()
			.and()
				.logout()
				//.logoutUrl("/perform_logout")
				.invalidateHttpSession(true)
				.clearAuthentication(true)
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login?logout")
				.permitAll()
			.and()	
				.exceptionHandling()
				.accessDeniedPage("/accessDenied");
	}
}