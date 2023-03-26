package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserRoleService;

@Controller
public class MyController {
    private final UserRoleService service;

    public MyController(UserRoleService service) {
        this.service = service;
    }

    @GetMapping("/user/{username}")
    public String userInfo(Model model, @PathVariable String username) {
        model.addAttribute("user", service.getByUsername(username));
        return "user-info";
    }

    @GetMapping("/user/update-user/{id}")
    public String updateYourself(Model model, @PathVariable long id) {
        model.addAttribute("user", service.getById(id));
        return "user-update-user";
    }

    @GetMapping("/admin")
    public String allUsers(Model model) {
        model.addAttribute("users", service.findAll());
        return "all-users";
    }

    @GetMapping("/admin/add-user")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "add-user";
    }

    @GetMapping("/admin/update-user/{id}")
    public String updateUser(Model model, @PathVariable long id) {
        model.addAttribute("user", service.getById(id));
        return "admin-update-user";
    }

    @DeleteMapping("/admin/deleteUser/{id}")
    public String deleteUser(@PathVariable long id) {
        service.deleteUserById(id);
        return "redirect:/admin";
    }

    @PostMapping("/save-user")
    public String saveUser(@ModelAttribute User user) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(String.format("{bcrypt}%s", encodedPassword));
        service.save(user);
        return "redirect:/admin";
    }
}
