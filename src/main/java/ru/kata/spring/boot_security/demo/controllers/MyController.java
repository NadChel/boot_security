package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserRoleService;

@Controller
public class MyController {
    private final UserRoleService service;

    private final PasswordEncoder passwordEncoder;

    public MyController(UserRoleService service, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/user")
    public String userInfo(Model model, Authentication authentication) {
        String username = getUsername(authentication);
        model.addAttribute("user", service.getByUsername(username));
        return "user-info";
    }

    @GetMapping("/user/update-info")
    public String updateYourInfo(Model model, Authentication authentication) {
        String username = getUsername(authentication);
        model.addAttribute("user", service.getByUsername(username));
        return "user-update-info";
    }

    @GetMapping("/user/update-password")
    public String updateYourPassword(Model model, Authentication authentication) {
        String username = getUsername(authentication);
        model.addAttribute("user", service.getByUsername(username));
        return "user-update-password";
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

    @GetMapping("/admin/update-user")
    public String updateUser(Model model, @RequestParam String username) {
        model.addAttribute("user", service.getByUsername(username));
        return "admin-update-user";
    }

    @DeleteMapping("/admin/delete-user")
    public String deleteUser(@RequestParam String username) {
        service.deleteUserByUsername(username);
        return "redirect:/admin";
    }

    @PostMapping("/save-user")
    public String saveUser(@ModelAttribute User user,
                           @RequestParam(required = false) boolean password,
                           Authentication authentication) {
        System.out.println("Logged user: " + user);

        if (password) {
            System.out.println("Encoding...");
            encodePassword(user);
            System.out.println("Encoding complete!");
        }

        service.save(user);

        return isAdmin(authentication) ?
                "redirect:/admin" :
                "redirect:/user";
    }

    private String getUsername(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }

    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"));
    }

    private void encodePassword(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }
}
