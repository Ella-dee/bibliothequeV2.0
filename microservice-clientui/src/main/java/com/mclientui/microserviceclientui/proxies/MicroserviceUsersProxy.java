package com.mclientui.microserviceclientui.proxies;

import com.mclientui.microserviceclientui.beans.UserBean;
import com.mclientui.microserviceclientui.config.FeignConfig;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/**
 * <h2>Proxy links clientui to microservice-users</h2>
 */
@FeignClient(name = "zuul-server", url = "localhost:9004", contextId = "usersProxy", configuration = FeignConfig.class)
@RibbonClient(name = "microservice-users")
public interface MicroserviceUsersProxy {
    @GetMapping(value = "microservice-users/Utilisateurs")
    List<UserBean> listUsers();

    @PostMapping(value = "microservice-users/Utilisateurs/add-user")
    UserBean addUser(@RequestBody UserBean userBean);

    @PostMapping(value = "microservice-users/Utilisateurs/log-user")
    UserBean logUser(@RequestParam String userName, @RequestParam String password);

    @PostMapping(value = "microservice-users/Utilisateurs/forgot-password")
    UserBean findUserForPassword(@RequestParam String email);

    @GetMapping(value = "microservice-users/Utilisateurs/MotDePasseReset")
    UserBean findUserByToken(@RequestParam String token);

    @PostMapping(value = "microservice-users/Utilisateurs/MotDePasseReset")
    UserBean findUserByTokenAndSetsNewPassword(@RequestParam String token, @RequestParam String password);

    @GetMapping( value = "microservice-users/Utilisateurs/MonProfil/{id}")
    UserBean showUser(@PathVariable("id") Integer id);

}
