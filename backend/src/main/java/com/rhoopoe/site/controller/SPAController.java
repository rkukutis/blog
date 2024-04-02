package com.rhoopoe.site.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SPAController implements ErrorController {
    @RequestMapping("/error")
    public Object error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getMethod().equalsIgnoreCase(HttpMethod.GET.name())) {
            response.setStatus(HttpStatus.OK.value());
            return "forward:/index.html";
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}