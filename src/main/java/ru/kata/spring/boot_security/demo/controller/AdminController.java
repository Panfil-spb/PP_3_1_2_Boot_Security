package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RolesRepository;
import ru.kata.spring.boot_security.demo.security.UserDetailsImp;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RolesRepository rolesRepository;

    @Autowired
    public AdminController(UserService userService, RolesRepository rolesRepository) {
        this.userService = userService;
        this.rolesRepository = rolesRepository;
    }

    @GetMapping("/")
    public String startURL() {
        return "redirect:/admin/users";
    }



    @GetMapping(value = "/users")
    public String showAllUsers(ModelMap model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImp userDetailsImp = (UserDetailsImp) authentication.getPrincipal();
        User user = userDetailsImp.getUser();
        if (user.getRoles().contains(rolesRepository.findByName("ROLE_ADMIN").get())) {
            model.addAttribute("users", userService.getAllUsers());
            return "admin/users";
        }
        else {
            return "redirect:/";
        }


    }

    @GetMapping(value = "/users/add")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "admin/addUser";
    }

    @PostMapping(value = "/users/add")
    public String createNewUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/addUser";
        }
        userService.addUser(user);
        return "redirect:/";
    }

    @GetMapping(value = "/users/edit/{id}")
    public String editUser(ModelMap model, @PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/editUser";
    }

    @PostMapping(value = "/users/edit")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/editUser";
        }
        userService.editUser(user);
        return "redirect:/";
    }

    @DeleteMapping("/users/delete")
    public String deleteUserById(@RequestParam("id") String id) {
        userService.deleteUser(Long.parseLong(id));
        return "redirect:/";
    }

    @GetMapping("/users/{id}")
    public String showUserPage(@PathVariable("id") Long id, ModelMap modelMap) {
        modelMap.addAttribute("user", userService.getUserById(id));
        return "admin/show";
    }
}
