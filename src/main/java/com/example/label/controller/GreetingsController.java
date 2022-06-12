package com.example.label.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class GreetingsController {

    @GetMapping("greetings")
    public Object greetings(){
        Map<String, Object> map = new HashMap<>();
        map.put("msg", "greetings");
        map.put("time", new Date());
        return map;
    }
}
