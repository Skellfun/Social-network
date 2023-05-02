package ru.itgroup.intouch.aggregator.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.itgroup.intouch.client.AuthServiceClient;
import ru.itgroup.intouch.dto.AuthenticateDto;
import ru.itgroup.intouch.dto.AuthenticateResponseDto;
import ru.itgroup.intouch.dto.CaptchaDto;
import ru.itgroup.intouch.dto.EmailDto;
import ru.itgroup.intouch.dto.PasswordDto;
import ru.itgroup.intouch.dto.RegistrationDto;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Slf4j
public class AuthorizationController {
    private final AuthServiceClient client;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    AuthenticateResponseDto login(@RequestBody AuthenticateDto authenticateDto, HttpServletRequest request) {
        log.info("../auth/login AUTHORIZATION request header value: " + request.getHeader("AUTHORIZATION"));
        return client.login(authenticateDto);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    void register(@RequestBody RegistrationDto registrationDto) {
        client.register(registrationDto);
    }

    @PostMapping("/password/recovery/")
    @ResponseStatus(HttpStatus.OK)
    void recoverPassword(@RequestBody EmailDto emailDto) {
        client.recoverPassword(emailDto);
    }

    @PostMapping("/password/recovery/{linkId}")
    @ResponseStatus(HttpStatus.OK)
    void setNewPassword(@PathVariable String linkId,
                        @RequestBody PasswordDto passwordDto) {
        client.setNewPassword(linkId, passwordDto);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    void logout() {
        client.logout();
        log.info("logging out");
    }

    @GetMapping("/captcha")
    @ResponseStatus(HttpStatus.OK)
    CaptchaDto captcha() {
        return client.captcha();
    }

}