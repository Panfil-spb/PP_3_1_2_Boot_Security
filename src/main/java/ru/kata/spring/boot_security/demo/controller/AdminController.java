package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.core.JdbcAggregateOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RolesRepository;
import ru.kata.spring.boot_security.demo.security.UserDetailsImp;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final RolesRepository roleRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService userService, RolesRepository rolesRepository, RolesRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String startURL(ModelMap modelMap) {
        modelMap.addAttribute("users", userService.getAllUsers());
        modelMap.addAttribute("user", userService.getAuthUser());
        modelMap.addAttribute("newUser", new User());
        modelMap.addAttribute("allRoles", userService.getAllRole());
        return "/admin/admin";
    }


    @GetMapping(value = "/users")
    public String showAllUsers(ModelMap model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users";
    }

    @GetMapping(value = "/add")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "admin/addUser";
    }

    @PostMapping(value = "/add")
    public String createNewUser(@ModelAttribute("newUser") @Valid User user, @RequestParam("selectRoles") List<Long> roles, BindingResult bindingResult) {

        Set<Role> role = roles.stream()
                .map(roleId -> roleRepository.findById(roleId).orElseThrow())
                .collect(Collectors.toSet());
        user.setRoles(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.addUser(user);
        return "redirect:/admin/";
    }

    @GetMapping(value = "/edit/{id}")
    public String editUser(ModelMap model, @PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/editUser";
    }

    @PostMapping(value = "/edit")
    public String updateUser(@ModelAttribute("user") @Valid User user, @RequestParam("selectRoles") List<Long> roles, BindingResult bindingResult) {
        Set<Role> role = roles.stream()
                .map(roleId -> roleRepository.findById(roleId).orElseThrow())
                .collect(Collectors.toSet());
        user.setRoles(role);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
