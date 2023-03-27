package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserRoleService {
    List<User> findAll();

    User getById(long id);

    User getByUsername(String username);

    void save(User user);

    void deleteUserByUsername(String username);
}
