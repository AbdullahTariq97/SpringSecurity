package uk.sky.security.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;
import uk.sky.security.models.User;

@Repository
public interface UserRespository extends CassandraRepository<User,String> {

    @Query("SELECT * FROM security.user WHERE username = ?0")
    User findByUsername(String name);
}
