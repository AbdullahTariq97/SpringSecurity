package uk.sky.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// We need to add the @EnableWebSecurity annotation this class
// Also need to extends abstract class WebSecurityConfigureAdapter
@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    // Adding identity store of users using cassandra database
    @Autowired
    private UserDetailsService userDetailsService;


    // This method can be overridden to configure the blacklisting or whitelisting of endpoints
    // and for affecting the Authorization (roles and responsibilities)
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        .authorizeRequests().antMatchers("/private/products").hasRole("USER")
                .and().httpBasic();
    }

    // A bean for a password encoder needs to be provided to spring security
    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }


    // override the configure method that accepts AuthenticationManagerBuilder
    // - Need to affect the AuthenticationManagerBuilder by passing an object of type UserDetailsService to it
    // - UserDetailsService is an interface that needs to be implemented
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
}
