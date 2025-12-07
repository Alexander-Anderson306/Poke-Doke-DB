package com.poke_backend;

import java.util.concurrent.ConcurrentHashMap;
public class UserLockManager {
    private static final ConcurrentHashMap<Integer, Object> userLocks = new ConcurrentHashMap<>();

    public static Object getLock(int userId) {
        return userLocks.computeIfAbsent(userId, id -> new Object());
    }
}