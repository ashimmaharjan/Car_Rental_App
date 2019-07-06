package com.example.carrental.Model;

public class AuthToken {

    private String token;

    private Users users;

    public AuthToken(String token, Users users) {
        this.token = token;
        this.users = users;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
