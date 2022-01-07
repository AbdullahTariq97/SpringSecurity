package uk.sky.security.services;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uk.sky.security.models.User;
import uk.sky.security.models.UserDetailsImpl;
import uk.sky.security.repository.UserRespository;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRespository userRespository;

    // Step 2: Implement the method below.
    //        - Uses the cassandra repository to retrieve a User object with the same username passed inside the header
    //        - If user with username not present, return null
    //        - Method has to return object of type UserDetails. It is an interface that has to be implemented
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userRespository.findByUsername(username));
        if(user.isEmpty()) throw new UsernameNotFoundException("User not found with username:" + username);
        return new UserDetailsImpl(user.get().getUsername(), user.get().getPassword());
    }
}
