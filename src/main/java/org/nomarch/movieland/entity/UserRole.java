package org.nomarch.movieland.entity;


import lombok.Getter;

public enum UserRole {
    GUEST(0),
    USER(1),
    ADMIN(2);

    @Getter
    private final int roleNum;

    UserRole(int roleNum) {
        this.roleNum = roleNum;
    }

    public static UserRole getByRoleNum(int roleNum) {
        for (UserRole role : values()) {
            if (role.roleNum == roleNum) {
                return role;
            }
        }

        throw new IllegalArgumentException("No UserRole with this roleNum: " + roleNum);
    }
}
