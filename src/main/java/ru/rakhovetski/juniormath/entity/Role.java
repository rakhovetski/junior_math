package ru.rakhovetski.juniormath.entity;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public enum Role implements GrantedAuthority {
    STUDENT("student"),
    TEACHER("teacher"),
    ADMIN("admin");

    private final String name;

    Role(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name();
    }
}
