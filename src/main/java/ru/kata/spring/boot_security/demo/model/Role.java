package ru.kata.spring.boot_security.demo.model;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;

    public Role( String name) {
        this.name = name;
    }

    public Role() {

    }

    public Role(Role name) {
    }

    // Constructors, getters, and setters

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }



    public String getName() { return name; }

    public void setName(String role) { this.name = role; }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
