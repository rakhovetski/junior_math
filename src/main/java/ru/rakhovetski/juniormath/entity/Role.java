package ru.rakhovetski.juniormath.entity;

import lombok.Getter;

@Getter
public enum Role {
    STUDENT("student"),
    TEACHER("teacher"),
    ADMIN("admin");

    private final String name;

    Role(String name) {
        this.name = name;
    }

}
