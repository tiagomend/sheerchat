package dev.tiagomendonca.sheerchat.entities;

public class UserBuilder {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;

    public UserBuilder firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserBuilder lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserBuilder username(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder password(String password) {
        this.password = password;
        return this;
    }

    public User build() {
        return new User(firstName, lastName, username, password, email);
    }
}
