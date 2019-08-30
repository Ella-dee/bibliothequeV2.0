package com.mclientui.microserviceclientui.proxies;

import com.mclientui.microserviceclientui.beans.UserBean;
import com.mclientui.microserviceclientui.config.FeignConfig;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <h2>Proxy links clientui to microservice-mailing</h2>
 */
@FeignClient(name = "zuul-server", contextId = "mailingProxy", configuration = FeignConfig.class)
@RibbonClient(name = "microservice-mailing")
public interface MicroserviceMailingProxy {
    @PostMapping(value = "microservice-mailing/Utilisateurs/forgot-password")
    Boolean sendLinkForPassword(@RequestParam String email,@RequestParam String token, @RequestParam String appUrl);
}
