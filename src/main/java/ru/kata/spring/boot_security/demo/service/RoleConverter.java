package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repositories.RolesRepository;

@Component
public class RoleConverter implements Converter<String, Role> {

    @Autowired
    private RolesRepository roleRepository;

    @Override
    public Role convert(String source) {
        Long roleId = Long.valueOf(source);
        return roleRepository.findById(roleId).orElse(null);
    }
}