package com.wolkabout.exam.api.dto;

import java.util.StringJoiner;

public class UserCreationDetails {

    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserCreationDetails.class.getSimpleName() + "[", "]")
                .add("email='" + getEmail() + "'")
                .add("password='" + getPassword() + "'")
                .toString();
    }
}
