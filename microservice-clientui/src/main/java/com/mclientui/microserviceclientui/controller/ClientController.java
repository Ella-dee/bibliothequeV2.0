package com.mclientui.microserviceclientui.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class ClientController {


    @RequestMapping("/")
    public String home(){
        return "home";
    }

    @RequestMapping("/Accueil")
    public String hometwice(){
        return "home";
    }


}