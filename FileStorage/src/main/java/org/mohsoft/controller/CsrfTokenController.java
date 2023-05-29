package org.mohsoft.controller;

import org.mohsoft.model.CsrfTokenDto;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CsrfTokenController {

    @GetMapping("/csrf")
    public CsrfTokenDto getCsrfToken(CsrfToken csrfToken) {
        return new CsrfTokenDto(csrfToken.getToken());
    }
}

