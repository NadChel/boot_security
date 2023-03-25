package ru.kata.spring.boot_security.demo.service;

import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserRoleService {
    List<User> findAll();

    User getById(long id);

    void save(User user);

    @Transactional
    void deleteUserById(long id);
}
