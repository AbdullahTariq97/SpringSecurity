package uk.sky.security.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("user")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @PrimaryKeyColumn(
            name="username",
            ordinal = 0,
            type = PrimaryKeyType.PARTITIONED
    )
    private String username;

    @Column
    private String password;
}
