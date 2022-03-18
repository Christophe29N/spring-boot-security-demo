package com.example.demo.registration.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;
import org.thymeleaf.spring5.SpringTemplateEngine;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;

@Configuration
public class AppConfig {

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
 
    
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new CustomUserDetailsService();
//    }
    

    
    
    public static final String BAD_CREDENTIALS_URL = "/login?bad_credentials";

    public static final String LOCKED_URL = "/login?locked";
    public static final String DISABLED_URL = "/login?disabled";
    public static final String ACCOUNT_EXPIRED_URL = "/login?account_expired";
    public static final String CREDENTIALS_EXPIRED_URL = "/login?credentials_expired";    

    
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        ExceptionMappingAuthenticationFailureHandler failureHandler = new ExceptionMappingAuthenticationFailureHandler();

        Map<String, String> failureUrlMap = new HashMap<>();        
        failureUrlMap.put(BadCredentialsException.class.getName(), BAD_CREDENTIALS_URL); // no user found
        failureUrlMap.put(AccountExpiredException.class.getName(), ACCOUNT_EXPIRED_URL);
        failureUrlMap.put(CredentialsExpiredException.class.getName(), CREDENTIALS_EXPIRED_URL);
        failureUrlMap.put(DisabledException.class.getName(), DISABLED_URL);
        failureUrlMap.put(LockedException.class.getName(), LOCKED_URL);
        
        failureHandler.setExceptionMappings(failureUrlMap);
        
        return failureHandler;
    }

    /**
     *  add an additional dialect to our template engine for layout processing
     * @return
     */
//    @Bean
//    public SpringTemplateEngine templateEngine() {
//        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//        templateEngine.addDialect(new LayoutDialect());
//        return templateEngine;
//    }
    
    @Bean
    public LayoutDialect layoutDialect() {
      return new LayoutDialect();
    }
}