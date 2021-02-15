package org.nomarch.movieland.security;

import org.nomarch.movieland.entity.User;

public class UserHolder {

    private static final ThreadLocal<User> threadLocalScope = new ThreadLocal<>();

    public static User getUser() {
        return threadLocalScope.get();
    }

    public static void setUser(User user) {
        threadLocalScope.set(user);
    }
}
