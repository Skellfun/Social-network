package ru.itgroup.intouch.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.itgroup.intouch.config.FeignConfig;
import ru.itgroup.intouch.dto.AccountDto;


@FeignClient(name = "account-service", url = "${IP}" + ":" + "${server.port}", path = "/api/v1/account", configuration = FeignConfig.class)
public interface AccountServiceClient {
    @GetMapping("/me")
    AccountDto myAccount();

    @PutMapping("/me")
    AccountDto changeProfile(@RequestBody AccountDto accountDto);

    @DeleteMapping("/me")
    void deleteAccount();

}