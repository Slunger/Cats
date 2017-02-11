package com.cats.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by andrey on 07.02.17.
 */
@Controller
public class MainController {

    @RequestMapping("/main")
    public String main() {
        return "main";
    }

    @RequestMapping({"/", "/welcome"})
    public String welcome() {
        return "welcome";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "welcome";
    }
}
