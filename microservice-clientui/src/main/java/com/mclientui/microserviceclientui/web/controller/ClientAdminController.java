package com.mclientui.microserviceclientui.web.controller;

import com.mclientui.microserviceclientui.beans.UserBean;
import com.mclientui.microserviceclientui.exceptions.BadLoginPasswordException;
import com.mclientui.microserviceclientui.proxies.MicroserviceUsersProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <h2>Controller linking with microservice-users</h2>
 */
@Controller
public class ClientAdminController {

    @Autowired
    private MicroserviceUsersProxy usersProxy;

    /**
     * <p>Lists all users</p>
     * @param model
     * @return users.html
     */
    @RequestMapping("/admin/Utilisateurs")
    public String adminListUsers(Model model){
        List<UserBean> users =  usersProxy.listUsers();
        model.addAttribute("users", users);
        return "users";
    }

}