package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserDetailsServiceImp;

@Component
public class UserValidator implements Validator {

    private final UserDetailsServiceImp userDetailsServiceImp;

    @Autowired
    public UserValidator(UserDetailsServiceImp userDetailsServiceImp) {
        this.userDetailsServiceImp = userDetailsServiceImp;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        try {
            userDetailsServiceImp.loadUserByUsername(user.getName());
        }
        catch (UsernameNotFoundException ignored) {
            return;
        }

        errors.reject("username", new String[]{" "}, "Человек с таким именем етсь");
    }
}
