package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class UserRoleServiceImpl implements UserRoleService {
    private final UserDao userDao;

    private final RoleDao roleDao;

    public UserRoleServiceImpl(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
    }

    @Override
    public List<User> getAll() {
        return userDao.findAll();
    }

    @Override
    public User getByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    @Transactional
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    @Transactional
    public void disableUserByUsername(String username) {
        User user = userDao.findByUsername(username);
        user.setEnabledByte((byte) 0);
    }

    @Override
    @Transactional
    public void enableUserByUsername(String username) {
        User user = userDao.findByUsername(username);
        user.setEnabledByte((byte) 1);
    }

    @Override
    @Transactional
    public void deleteUserByUsername(String username) {
        userDao.deleteByUsername(username);
    }

    @Override
    public Role getRoleByName(String name) {
        return roleDao.findByAuthority(name);
    }

    @Override
    public Set<Role> getAdminRoleSet() {
        return new HashSet<>(List.of(roleDao.findByAuthority("USER"),
                roleDao.findByAuthority("ADMIN")));
    }

    @Override
    public String getUsername(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

    @Override
    public boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
    }

    @Override
    public void encodePassword(User user, PasswordEncoder passwordEncoder) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }
}
