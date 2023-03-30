package ru.kata.spring.boot_security.demo.models;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column
    private String password;
    @Column
    private String name;
    @Column(name = "last_name")
    private String lastName;
    @Column
    private String department;
    @Column
    private int salary;
    @Column
    private byte age;
    @Column
    private String email;
    @Column
    private byte enabled;
    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> authorities;

    public User() {
        this.enabled = 1;
        this.authorities = new HashSet<>(List.of(new Role("USER")));
    }

    public User(String username, String password, String name, String lastName,
                String department, int salary, byte age, String email, byte enabled) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.department = department;
        this.salary = salary;
        this.age = age;
        this.email = email;
        this.enabled = enabled;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public byte getAge() {
        return age;
    }

    public void setAge(byte age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Role> roleList) {
        this.authorities = roleList;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("username='" + username + "'")
                .add("password='" + password + "'")
                .add("name='" + name + "'")
                .add("lastName='" + lastName + "'")
                .add("department='" + department + "'")
                .add("salary=" + salary)
                .add("age=" + age)
                .add("email='" + email + "'")
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && salary == user.salary && age == user.age && username.equals(user.username) && password.equals(user.password) && Objects.equals(name, user.name) && Objects.equals(lastName, user.lastName) && Objects.equals(department, user.department) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, name, lastName, department, salary, age, email);
    }

    @Override
    public boolean isEnabled() {
        return enabled == 1;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }
}
