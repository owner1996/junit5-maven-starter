package com.dmdev.junit.service;

import com.dmdev.junit.model.User;
import org.junit.jupiter.api.*;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
        assertThat(users).isEmpty();
//        assertTrue(users.isEmpty(), "User list should be empty");
    }

    @Test
    void userSizeIfUserAdded() {
        System.out.println("Test 2: " + this);
        userService.add(ALEX, PETR);

        var users = userService.getAll();

        assertThat(users).hasSize(2);
//        assertEquals(2, users.size());
    }

    @Test
    void loginSuccessIfUserExists() {
        userService.add(ALEX);
        Optional<User> maybeUser = userService.login(ALEX.getUsername(), ALEX.getPassword());
        assertThat(maybeUser).isPresent();
//        assertTrue(maybeUser.isPresent());
        maybeUser.ifPresent(user -> assertThat(user).isEqualTo(ALEX));
    }

    @Test
    void usersConvertedToMapById() {
        userService.add(ALEX, PETR);

        Map<Integer, User> users = userService.getAllConvertedById();

        assertAll(
                () -> assertThat(users).containsKeys(ALEX.getId(), PETR.getId()),
                () -> assertThat(users).containsValues(ALEX, PETR)
        );
    }

    @Test
    void loginFailIfPasswordIsNotCorrect() {
        userService.add(ALEX);
        Optional<User> maybeUser = userService.login(ALEX.getUsername(), "12345");
        assertThat(maybeUser).isEmpty();
//        assertTrue(maybeUser.isEmpty());
    }

    @Test
    void loginFailIfUserDoesNotExist() {
        userService.add(ALEX);
        Optional<User> maybeUser = userService.login("Bob", ALEX.getPassword());
        assertTrue(maybeUser.isEmpty());
    }

    @Test
    void throwExceptionIfUsernameOrPasswordIsNull() {
        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> userService.login(null, "qwerty123"),
                        "login should throw exception on null username"),
                () -> assertThrows(IllegalArgumentException.class, () -> userService.login(ALEX.getUsername(), null),
                        "login should throw exception on null password")
        );
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
