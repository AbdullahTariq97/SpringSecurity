package uk.sky.annotations.enums;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

// When ApplicationUserRole.User -> constructor gets called. Values in () are passed as arguments to the constructor and set
public enum ApplicationUserRole {

    STUDENT(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(ApplicationUserPermission.PATIENT_WRITE, ApplicationUserPermission.PATIENT_READ)),
    TRAINEE_ADMIN(Set.of(ApplicationUserPermission.PATIENT_READ));

    // We are going to assign permissions to a role by nesting enum inside enum
    private Set<ApplicationUserPermission> permissionSet;

    // private constructor by default
    ApplicationUserRole(Set<ApplicationUserPermission> permissionSet){
        this.permissionSet = permissionSet;
    }

    public Set<ApplicationUserPermission> getPermissionSet() {
        return permissionSet;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> permissions = getPermissionSet()
                .stream().
                        map((permission) -> new SimpleGrantedAuthority(permission.getPermission())).collect(Collectors.toSet());

        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
