package com.mbooks.microservicebooks.proxies;

import com.mbooks.microservicebooks.config.FeignConfig;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <h2>Proxy links books to microservice-mailing</h2>
 */
@FeignClient(name = "zuul-server",contextId = "mailingProxy", configuration = FeignConfig.class)
@RibbonClient(name = "microservice-mailing")
public interface MicroserviceMailingProxy {
    @PostMapping(value = "microservice-mailing/Utilisateurs/notification_retour")
    void sendNotifWhenAwaitedBookIsReturned(@RequestParam Integer userId, @RequestParam Integer bookId);
}
