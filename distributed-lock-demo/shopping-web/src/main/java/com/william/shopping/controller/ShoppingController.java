package com.william.shopping.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shopping")
public class ShoppingController {



    @GetMapping
    public String shopping(Integer itemId,Integer num){

        return "";
    }
}
