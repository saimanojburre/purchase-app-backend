package com.newproject.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/config")
public class configController{
    @Value("API_URL")
    private String apiUrl;

    @GetMapping
    public Map<String, String> getConfig(){
        return Map.of("apiUrl", apiUrl);
    }
}