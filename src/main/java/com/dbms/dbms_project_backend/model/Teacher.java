package com.dbms.dbms_project_backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.dbms.dbms_project_backend.model.enumerations.Position;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Teacher {
    private Long id;
    private Long subjectId;
    private Set<Position> positions = new HashSet<>();

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return positions.stream()
                .map(position -> new SimpleGrantedAuthority(position.name()))
                .collect(Collectors.toList());
    }

    public Teacher addPosition(Position position) {
        this.positions.add(position);
        return this;
    }

    public Teacher removePosition(Position position) {
        this.positions.remove(position);
        return this;
    }


    public Teacher setPositions(Set<Position> positions) {
        this.positions = positions;
        return this;
    }

   
}
