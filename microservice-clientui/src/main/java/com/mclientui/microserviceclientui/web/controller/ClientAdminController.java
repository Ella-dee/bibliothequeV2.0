package com.mclientui.microserviceclientui.web.controller;

import com.mclientui.microserviceclientui.beans.BorrowingBean;
import com.mclientui.microserviceclientui.beans.UserBean;
import com.mclientui.microserviceclientui.proxies.MicroserviceBooksProxy;
import com.mclientui.microserviceclientui.proxies.MicroserviceUsersProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * <h2>Controller linking with microservice-users</h2>
 */
@Controller
public class ClientAdminController {

    @Autowired
    private MicroserviceUsersProxy usersProxy;
    @Autowired
    private MicroserviceBooksProxy booksProxy;

    /**
     * <p>Lists all users</p>
     * @param model
     * @return admin-infos.html
     */
    @RequestMapping("/Admin")
    public String adminListUsers(Model model){
        List<UserBean> users =  usersProxy.listUsers();
        model.addAttribute("users", users);

        List<BorrowingBean> borrowingBeans = booksProxy.listBorrowings();
        List<BorrowingBean> lates = new ArrayList<>();
        for(BorrowingBean b: borrowingBeans){
            if (b.getBorrowingType().getId() == 4){
                lates.add(b);
            }
        }
        model.addAttribute("borrowings", lates);

        return "admin-infos";
    }

}