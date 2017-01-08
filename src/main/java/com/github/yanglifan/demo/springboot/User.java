package com.github.yanglifan.demo.springboot;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {

    @Id
    String username;

    public User() {
    }

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
