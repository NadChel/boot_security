package ru.kata.spring.boot_security.demo.models;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "")
public class Role implements GrantedAuthority {
    @Id
    @Column
    private String name;
    @ManyToMany
    @JoinTable(name = "",
            joinColumns = @JoinColumn(name = ""),
            inverseJoinColumns = @JoinColumn(name = ""))
    private List<User> userList;

    public Role() {

    }

    public Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return this.getName();
    }
}
