package uk.sky.annotations.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import uk.sky.annotations.enums.ApplicationUserPermission;
import uk.sky.annotations.enums.ApplicationUserRole;
import uk.sky.annotations.models.AuthEntryPoint;

// We need to add the @EnableWebSecurity annotation this class
// Also need to extends abstract class WebSecurityConfigureAdapter
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthEntryPoint authEntryPoint;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable() // TODO: this will be explained in later section
                // pemitting requests matching this pattern
                .authorizeRequests().antMatchers("/").permitAll()
                // to access get method you need to have specific role
//                .antMatchers(HttpMethod.GET,"/patient/**").hasAnyRole(ApplicationUserRole.ADMIN.name(), ApplicationUserRole.TRAINEE_ADMIN.name())
//                .antMatchers(HttpMethod.DELETE,"/patient/**").hasAnyRole(ApplicationUserRole.ADMIN.name(), ApplicationUserRole.TRAINEE_ADMIN.name())
//                .antMatchers(HttpMethod.POST,"/patient/**").hasAnyRole(ApplicationUserRole.ADMIN.name(), ApplicationUserRole.TRAINEE_ADMIN.name())
//                .antMatchers(HttpMethod.PUT,"/patient/**").hasAnyRole(ApplicationUserRole.ADMIN.name(), ApplicationUserRole.TRAINEE_ADMIN.name())
//                // to access delete, post and put you need specific authority
//                .antMatchers(HttpMethod.DELETE, "/patient/**").hasAuthority(ApplicationUserPermission.PATIENT_WRITE.getPermission())
//                .antMatchers(HttpMethod.POST, "/patient/**").hasAuthority(ApplicationUserPermission.PATIENT_WRITE.getPermission())
//                .antMatchers(HttpMethod.PUT, "/patient/**").hasAuthority(ApplicationUserPermission.PATIENT_WRITE.getPermission())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                .authenticationEntryPoint(authEntryPoint);
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        // These users can be retrieved from a database
        UserDetails annaSmithUser = User.builder()
                .username("annasmith")
                .password(passwordEncoder.encode("password"))
                .roles(ApplicationUserRole.STUDENT.name())
                .build();

        UserDetails  admin = User.builder().username("linda")
                .password(passwordEncoder.encode("password123"))
                .roles(ApplicationUserRole.ADMIN.name())
                .authorities(ApplicationUserRole.ADMIN.getGrantedAuthorities())
                .build();

        UserDetails  traineeAdmin = User.builder().username("traineeAdmin").password(passwordEncoder.encode("traineeAdminPass"))
                .roles(ApplicationUserRole.TRAINEE_ADMIN.name())
                .authorities(ApplicationUserRole.TRAINEE_ADMIN.getGrantedAuthorities())
                .build(); //ROLE_ADMINTRAINEE

        return new InMemoryUserDetailsManager(annaSmithUser,admin, traineeAdmin);
    }
}
