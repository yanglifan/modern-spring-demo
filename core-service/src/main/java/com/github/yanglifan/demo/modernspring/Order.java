package com.github.yanglifan.demo.modernspring;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_orders")
@Entity
public class Order {
    @Id
    private String code;

    private Long userId;

    private Order() {
    }

    public Order(String code, Long userId) {
        this.code = code;
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
