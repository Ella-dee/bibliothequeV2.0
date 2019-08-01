package com.mclientui.microserviceclientui.proxies;

import com.mclientui.microserviceclientui.beans.UserBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
/**
 * <h2>Proxy links clientui to microservice-users</h2>
 */
@FeignClient(name = "microservice-users", url = "localhost:9002")
public interface MicroserviceUsersProxy {
    @GetMapping(value = "/Utilisateurs")
    List<UserBean> listUsers();

    @PostMapping(value = "/Utilisateurs/add-user")
    UserBean addUser(@RequestBody UserBean userBean);

    @PostMapping(value = "/Utilisateurs/log-user")
    UserBean logUser(@RequestParam String userName, @RequestParam String password);

    @GetMapping( value = "/Utilisateurs/MonProfil/{id}")
    UserBean showUser(@PathVariable("id") Integer id);

}
