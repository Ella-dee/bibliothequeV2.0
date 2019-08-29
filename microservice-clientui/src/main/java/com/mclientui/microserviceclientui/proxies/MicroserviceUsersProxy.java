package com.mclientui.microserviceclientui.proxies;

import com.mclientui.microserviceclientui.beans.UserBean;
import com.mclientui.microserviceclientui.config.FeignConfig;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
/**
 * <h2>Proxy links clientui to microservice-users</h2>
 */
@FeignClient(name = "zuul-server", contextId = "usersProxy", configuration = FeignConfig.class)
@RibbonClient(name = "microservice-users")
public interface MicroserviceUsersProxy {
    @GetMapping(value = "microservice-users/Utilisateurs")
    List<UserBean> listUsers();

    @PostMapping(value = "microservice-users/Utilisateurs/add-user")
    UserBean addUser(@RequestBody UserBean userBean);

    @PostMapping(value = "microservice-users/Utilisateurs/log-user")
    UserBean logUser(@RequestParam String userName, @RequestParam String password);

    @GetMapping( value = "microservice-users/Utilisateurs/MonProfil/{id}")
    UserBean showUser(@PathVariable("id") Integer id);

}
