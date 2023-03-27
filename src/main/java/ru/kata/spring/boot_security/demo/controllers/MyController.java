package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
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
        model.addAttribute("user", service.getByUsername(username))
             .addAttribute("defaultPassword",
                        Character.toLowerCase(username.charAt(0)) + username.substring(1));
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
    public String saveUser(@ModelAttribute User user, Authentication authentication) {
        System.out.println("logged user: " + user);

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        service.save(user);

        return
                authentication.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN")) ?
                        "redirect:/admin" :
                        "redirect:/user";
    }

    private String getUsername(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}
