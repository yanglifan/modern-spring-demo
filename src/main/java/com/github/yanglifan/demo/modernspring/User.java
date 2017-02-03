package com.github.yanglifan.demo.modernspring;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@DynamicUpdate
@Table(name = "t_users")
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private int age;

    /**
     * Default constructor is needed by Hibernate
     */
    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
