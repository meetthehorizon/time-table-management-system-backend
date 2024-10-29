package com.dbms.dbms_project_backend.model;

import com.dbms.dbms_project_backend.model.enumerations.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Set;
import java.util.HashSet;
import java.util.Collection;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class User implements UserDetails {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String password;
    private Set<Role> roles = new HashSet<>();

    private static final Logger logger = LoggerFactory.getLogger(User.class);

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public User addRole(Role role) {
        Set<Role> mutableRoles = new HashSet<>(this.roles);
        mutableRoles.add(role);
        this.roles = mutableRoles;

        return this;
    }

    public User deleteRole(Role role) {
        if (roles.contains(role)) {
            Set<Role> mutableRoles = new HashSet<>(this.roles);
            mutableRoles.remove(role);
            this.roles = mutableRoles;
            return this;
        } else {
            logger.warn("[WARN] Role {} not found for user {}", role, this.id);
            return this;
        }
    }
}
