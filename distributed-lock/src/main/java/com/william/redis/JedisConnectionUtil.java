package com.william.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author: WilliamDream
 * @Description:
 * @Date: 2019/5/20 21:27
 */
public class JedisConnectionUtil {

    private static JedisPool pool = null;

    static {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(100);   //最大连接数
        pool = new JedisPool(jedisPoolConfig,"192.168.11.153",6379);

    }

    public static Jedis getJedis(){
        return new Jedis();
    }

}
