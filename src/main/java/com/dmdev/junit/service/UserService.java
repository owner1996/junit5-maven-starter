package com.dmdev.junit.service;

import com.dmdev.junit.model.User;

import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public class UserService {

    private List<User> users = new ArrayList<>();

    public List<User> getAll() {
        return users;
    }

    public void add(User... users) {
        this.users.addAll(Arrays.stream(users).toList());
    }

    public Optional<User> login(String username, String password) {
        if (username == null || password == null){
            throw new IllegalArgumentException("username or password is null");
        }
        return users.stream()
                .filter(u -> username.equals(u.getUsername()))
                .filter(u -> password.equals(u.getPassword()))
                .findFirst();
    }

    public Map<Integer, User> getAllConvertedById() {
        return users.stream()
                .collect(toMap(User::getId, Function.identity()));
    }
}
