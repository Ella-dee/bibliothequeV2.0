package com.mclientui.microserviceclientui.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class ClientController {


    @GetMapping("/")
    public String home(){
        return "index";
    }


    @GetMapping("/Accueil")
    public String hometwice(){
        return "index";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "/error/access-denied";
    }

}