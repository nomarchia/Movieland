package org.nomarch.movieland.entity;

public enum UserRole {
    USER(0),
    ADMIN(1);

    private int roleNum;

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
