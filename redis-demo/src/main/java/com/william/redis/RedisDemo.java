package com.william.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;

public class RedisDemo {

    //  redisTemplate.opsForValue();    //操作字符串
    //  redisTemplate.opsForHash();     //操作hash
    //  redisTemplate.opsForList();     //操作list
    //  redisTemplate.opsForSet();      //操作set
    //  redisTemplate.opsForZSet();     //操作有序set

    @Autowired
    private RedisTemplate redisTemplate;


    public static void main(String[] args) {
        String REDIS_HOST = "127.0.0.1";
        Jedis jedis = new Jedis(REDIS_HOST,6379);
        jedis.set("","");
    }

    public void setValue(String key,String value){
        redisTemplate.opsForValue().set(key,value);
    }

    public Object getValue(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public void setList(){
        redisTemplate.opsForList().leftPush();
    }

}
