package com.william.redis.service;


import com.alibaba.fastjson.JSON;
import com.william.redis.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @Autowired
    private RedisTemplate redisTemplate;

    public void addBook(String key, Book book){
        redisTemplate.opsForValue().set(key,JSON.toJSONString(book));
    }

    public void updateBook(String tagKey,String tagName){

    }

    public void delteBook(String tagKey,String tagName){

    }

    public void Book(String tagKey,String tagName){

    }

}
