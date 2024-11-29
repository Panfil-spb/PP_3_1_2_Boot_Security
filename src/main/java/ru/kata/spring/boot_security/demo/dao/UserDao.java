package ru.kata.spring.boot_security.demo.dao;




import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {
    void addUser(User user);
    void deleteUser(Long id);
    void editUser(User user);
    User getUserById(Long id);
    List<User> getAllUsers();
}
