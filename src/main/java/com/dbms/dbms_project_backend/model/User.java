package com.dbms.dbms_project_backend.model;

import com.dbms.dbms_project_backend.model.enumerations.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Set;
import java.util.HashSet;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class User implements UserDetails {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String password;
    private Set<Role> roles = new HashSet<>();

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
        this.roles.add(role);
        return this;
    }

    public User deleteRole(Role role) {
        this.roles.remove(role);
        return this;
    }

    public User setRoles(Set<Role> roles) {
        this.roles = roles;
        if (roles.isEmpty()) {
            this.roles.add(Role.ROLE_USER);
        }
        return this;
    }
}
