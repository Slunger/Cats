package com.cats.web;

import com.cats.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

/**
 * Created by andrey on 07.02.17.
 */
@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @RequestMapping("/main")
    public String main(Model model, Principal principal) {
        model.addAttribute("userId", userService.loadUser(principal.getName()).getId());
        return "main";
    }

    @RequestMapping("/")
    public String welcome() {
        return "welcome";
    }

    @RequestMapping(value = "/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "welcome";
    }
}
