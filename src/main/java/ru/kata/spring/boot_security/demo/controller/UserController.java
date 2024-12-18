package ru.kata.spring.boot_security.demo.controller;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.security.UserDetailsImp;



@Controller
@RequestMapping("/user")
public class UserController {

    @GetMapping("/")
    public String showUserInfo(Model modelMap) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        modelMap.addAttribute("user",  ((UserDetailsImp) authentication.getPrincipal()).getUser());
        return "user/user";
    }

}