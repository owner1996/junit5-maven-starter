package com.dmdev.junit.model;

import lombok.Data;
import lombok.Value;

@Value(staticConstructor = "of")
public class User {
    Integer id;
    String username;
    String password;
}
