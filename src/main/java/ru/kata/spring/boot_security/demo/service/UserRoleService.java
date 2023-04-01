package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Set;

public interface UserRoleService {
    List<User> getAll();

    User getByUsername(String username);

    void save(User user);

    void disableUserByUsername(String username);

    void enableUserByUsername(String username);

    void deleteUserByUsername(String username);

    Role getRoleByName(String name);

    Set<Role> getAdminRoleSet();
}
