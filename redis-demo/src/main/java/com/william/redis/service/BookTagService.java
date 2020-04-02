package com.william.redis.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookTagService {

    @Autowired
    private RedisTemplate redisTemplate;


    public void addBookTag(String tagKey,String tagName){
        redisTemplate.opsForValue().set(tagKey,tagName);
    }


    public String getBookTagByKey(String tagKey){
        return (String)redisTemplate.opsForValue().get(tagKey);
    }


    public String getAndSetBooktag(String tagKey,String tagName){
        return (String)redisTemplate.opsForValue().getAndSet(tagKey,tagName);
    }

    public String fingByKeyLike(String tagKey){
        return (String) redisTemplate.opsForValue().get(tagKey+"*");
    }

    public List<String> fingByKeys(List<String> keys){
        return redisTemplate.opsForValue().multiGet(keys);
    }



}
