package com.mclientui.microserviceclientui.proxies;

import com.mclientui.microserviceclientui.beans.UserBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping( value = "/Utilisateurs/{id}")
    UserBean showUser(@PathVariable("id") Integer id);

}
