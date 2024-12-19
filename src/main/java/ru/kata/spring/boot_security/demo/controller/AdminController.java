package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String adminPage(ModelMap modelMap) {
        modelMap.addAttribute("users", userService.getAllUsers());
        modelMap.addAttribute("user", userService.getAuthUser());
        modelMap.addAttribute("newUser", new User());
        modelMap.addAttribute("allRoles", roleService.getAllRole());
        return "/admin/admin";
    }


    @PostMapping(value = "/add")
    public String createNewUser(@ModelAttribute("newUser") User user, BindingResult bindingResult) {

        userService.addNewUserFromForm(user);
        return "redirect:/admin/";
    }


    @PostMapping(value = "/edit")
    public String updateUser(@ModelAttribute("usEdit") @Valid User user, BindingResult bindingResult) {
        userService.editUser(user);
        return "redirect:/admin/";
    }

    @DeleteMapping("/delete")
    public String deleteUserById(@RequestParam("id") String id) {
        userService.deleteUser(Long.parseLong(id));
        return "redirect:/admin/";
    }

    @GetMapping("/users/{id}")
    public String showUserPage(@PathVariable("id") Long id, ModelMap modelMap) {
        modelMap.addAttribute("user", userService.getUserById(id));
        return "admin/show";
    }
}
