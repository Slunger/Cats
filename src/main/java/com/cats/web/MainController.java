package com.cats.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.enterprise.inject.Model;

/**
 * Created by andrey on 07.02.17.
 */
@Controller
public class MainController {

    @RequestMapping("/main")
    public String main(){
        return "main";
    }
}

