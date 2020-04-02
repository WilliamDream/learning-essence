package com.william.redis.controller;


import com.william.redis.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/booktag")
public class BookController {

    @Autowired
    private BookService bookService;




}
