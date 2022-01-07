package uk.sky.authorisation.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import uk.sky.authorisation.enums.ApplicationUserPermission;
import uk.sky.authorisation.enums.ApplicationUserRole;
import uk.sky.authorisation.models.AuthEntryPoint;

// We need to add the @EnableWebSecurity annotation this class
// Also need to extends abstract class WebSecurityConfigureAdapter
@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthEntryPoint authEntryPoint;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

//     we can choose to overide a number of method from web security configurer adapter
//     authorizeRequests() : authorises requests
//     anyRequest() : all requests must be authenticated
//     httpBasic() : mechanism used to check the autheticity of a client is basic authenticaton
//     antMatchers().permitAll() : allows us to exclude authenticity check on subset of API's
//     added a second ant matcher  .antMatchers("/private/**").hasRole(ApplicationUserRole.ADMIN.name())
//     the above has assigned a role for a url matching the pattern in the antMatcher
//     spring security will check if the user and pass match up to user with role of admin
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable() // TODO: this will be explained in later section
                // pemitting requests matching this pattern
                .authorizeRequests().antMatchers("/").permitAll()
                // to access get method you need to have specific role
                .antMatchers(HttpMethod.GET,"/patient/**").hasAnyRole(ApplicationUserRole.ADMIN.name(), ApplicationUserRole.TRAINEE_ADMIN.name())
                // to access delete, post and put you need specific authority
                .antMatchers(HttpMethod.DELETE, "/patient/**").hasAuthority(ApplicationUserPermission.PATIENT_WRITE.getPermission())
                .antMatchers(HttpMethod.POST, "/patient/**").hasAuthority(ApplicationUserPermission.PATIENT_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT, "/patient/**").hasAuthority(ApplicationUserPermission.PATIENT_WRITE.getPermission())
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
                .roles(ApplicationUserRole.STUDENT.name()).build();

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
