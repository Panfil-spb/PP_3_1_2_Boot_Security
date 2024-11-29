package ru.kata.spring.boot_security.demo.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "username")
    @NotEmpty(message = "Поле 'Имя' не может быть пустым")
    @Size(min = 2, max = 20, message = "Поле 'Имя' должно быть от 2 до 20 символов")
    private String name;

    @Column(name = "last_name")
    @NotEmpty(message = "Поле 'Фамилия' не может быть пустым")
    @Size(min = 2, max = 20, message = "Поле 'Фамилия' должно быть от 2 до 20 символов")
    private String last_name;

    @Column(name = "email", unique = true)
    @NotEmpty(message = "Поле 'email' не может быть пустым")
    @Email(message = "некорректный email")
    private String email;

    @Column(name = "password")
    @NotEmpty(message = "Поле 'Пароль' не может быть пустым")
    private String password;

    // Новое поле для хранения ролей
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "userroles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
    }


    public User() {
    }

    public User(String name, String last_name, String email, String password) {
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}