package com.dmdev.junit.service;

import com.dmdev.junit.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserService {

    private List<User> users = new ArrayList<>();

    public List<User> getAll() {
        return users;
    }

    public boolean add(User user) {
        return users.add(user);
    }

    public Optional<User> login(String username, String password) {
        return users.stream()
                .filter(u -> username.equals(u.getUsername()))
                .filter(u -> password.equals(u.getPassword()))
                .findFirst();
    }
}
