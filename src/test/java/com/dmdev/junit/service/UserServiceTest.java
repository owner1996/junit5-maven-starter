package com.dmdev.junit.service;

import com.dmdev.junit.model.User;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {

    private static final User ALEX = User.of(1, "Alex", "123");
    private static final User PETR = User.of(2, "Petr", "111");
    private UserService userService;

    @BeforeAll
    void init() {
        System.out.println("Before all: " + this);
    }

    @BeforeEach
    void prepare() {
        System.out.println("Before each: " + this);
        userService = new UserService();
    }

    @Test
    void usersEmptyIfNoUserAdded() {
        System.out.println("Test 1: " + this);
        var users = userService.getAll();
        assertTrue(users.isEmpty(), "User list should be empty");
    }

    @Test
    void userSizeIfUserAdded() {
        System.out.println("Test 2: " + this);
        userService.add(ALEX);
        userService.add(PETR);

        var users = userService.getAll();
        assertEquals(2, users.size());
    }

    @Test
    void loginSuccessIfUserExists() {
        userService.add(ALEX);
        Optional<User> maybeUser = userService.login(ALEX.getUsername(), ALEX.getPassword());
        assertTrue(maybeUser.isPresent());
        maybeUser.ifPresent(user -> assertEquals(ALEX, user));
    }

    @Test
    void loginFailIfPasswordIsNotCorrect(){
        userService.add(ALEX);
        Optional<User> maybeUser = userService.login(ALEX.getUsername(),"12345");
        assertTrue(maybeUser.isEmpty());
    }

    @Test
    void loginFailIfUserDoesNotExist() {
        userService.add(ALEX);
        Optional<User> maybeUser = userService.login("Bob", ALEX.getPassword());
        assertTrue(maybeUser.isEmpty());
    }

    @AfterEach
    void deleteDateFromDataBase() {
        System.out.println("After each: " + this);
    }

    @AfterAll
    void destroy() {
        System.out.println("After all: " + this);
    }
}
