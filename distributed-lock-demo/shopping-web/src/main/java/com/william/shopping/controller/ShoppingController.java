package com.william.shopping.controller;


import com.william.shopping.service.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shopping")
public class ShoppingController {

    @Autowired
    private ShoppingService service;

    @PostMapping("/order")
    public String shopping(Integer userId, Integer itemId,Integer num){
        return service.shopping(userId,itemId,num);
    }
}
