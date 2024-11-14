package com.rishabhtech.aop.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {

    @GetMapping("/home")
    public ResponseEntity<String> getHome(String str)
    {
        System.out.println("getHome Method");
        return ResponseEntity.ok("Hello World From Home Spring AOP");
    }

    @GetMapping(value = "/contact")
    public ResponseEntity<String> getContact(String name)
    {
        if(name == null)
            throw new RuntimeException("Name is not available");
        else
            return ResponseEntity.ok("Hello World From Contact Spring AOP "+name);
    }
}
