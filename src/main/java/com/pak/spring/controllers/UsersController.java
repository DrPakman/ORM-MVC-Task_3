package com.pak.spring.controllers;

import com.pak.spring.dao.UserDao;
import com.pak.spring.models.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller
@RequestMapping("/users")
public class UsersController {

    private final UserDao userDao;
    @Autowired
    public UsersController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping
    public String index (Model model) {
        model.addAttribute("users", userDao.index());
        return "users/getUsers";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userDao.show(id));
    return "users/getUser";
}
    @GetMapping("/new")
    public String newUser (Model model) {
        model.addAttribute("user", new User());
        return "users/newUser";
}
    @PostMapping()
    public String create (@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "users/newUser";
        }
        userDao.save(user);
        return "redirect:/users";

    }
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userDao.show(id));
        return "users/editUser";
    }
    @PatchMapping("/{id}")
    public String update (@ModelAttribute("user") @Valid User user, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()) {
            return "users/editUser";
        }
        userDao.update(id, user);
        return "redirect:/users";
    }
    @DeleteMapping("/{id}")
    public String delete (@PathVariable("id") int id) {
        userDao.delete(id);
        return "redirect:/users";
    }
}
