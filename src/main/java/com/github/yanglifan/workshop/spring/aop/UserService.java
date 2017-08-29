package com.github.yanglifan.workshop.spring.aop;

/**
 * @author Yang Lifan
 */
class UserService {
    public void add() {
        System.out.println("This is add service");
        this.doAdd();
    }

    void doAdd() {
        System.out.println("This is doAdd method");
    }

    public void delete(int id) {
        System.out.println("Delete " + id);
    }
}
