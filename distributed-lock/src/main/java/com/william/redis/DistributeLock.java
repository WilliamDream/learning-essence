package com.william.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.UUID;

/**
 * @Author: WilliamDream
 * @Description: 基于redis实现分布式锁
 * @Date: 2019/5/20 21:11
 */
public class DistributeLock {

    /**
       * @Description
       * @param lockName
     * @param acquireTimeOut
     * @param lockTimeOut  锁超时时间，避免长时间持有锁造成死锁
       * @return java.lang.String
       * @Date 2019/5/20 21:15
       */
    public String acquireLock(String lockName,long acquireTimeOut,int lockTimeOut){
        //保证获取锁与释放锁是同一个
        String identifier = UUID.randomUUID().toString();
        String lockKey = "lock:"+lockName;
        //过期时间
        int lockExpire = lockTimeOut/1000;
        Jedis jedis = null;
        try {
            jedis = JedisConnectionUtil.getJedis();
            long end = System.currentTimeMillis() + acquireTimeOut;
            while (System.currentTimeMillis() < end){
                if(jedis.setnx(lockKey,identifier)==1){ //获得锁成功
                    jedis.expire(lockKey,lockExpire);
                    System.out.println(Thread.currentThread().getName()+"->成功获得锁。");
                    return identifier;
                }
                if(jedis.ttl(lockKey)==-1){     //判断是否设置过期时间
                    jedis.expire(lockKey,lockExpire);
                }
                try {
                    //没有获得锁，睡眠一会儿再尝试获得锁
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        } finally {
            jedis.close();
        }
        return null;

    }

    /**
       * @Description
       * @param lockName
     * @param identifier
       * @return boolean
       * @Date 2019/5/20 20:25
       */
    public boolean releaseLock(String lockName,String identifier){
        System.out.println(Thread.currentThread().getName()+"->开始释放锁。");
        String lockKey = "lock:"+lockName;
        Jedis jedis = null;
        boolean isRelease = false;
        try {
            jedis = JedisConnectionUtil.getJedis();
            while (true){
                jedis.watch(lockKey);               //使用redis事务,保证
                if (identifier.equals(jedis.get(lockKey))){     //判断释放的是否为同一把锁
                    Transaction transaction = jedis.multi();
                    transaction.del(lockKey);
                    if(transaction.exec().isEmpty()){
                        continue;
                    }
                    isRelease = true;
                }
                jedis.unwatch();
                break;
            }
        } finally {
            jedis.close();
        }
        return isRelease;
    }


}
