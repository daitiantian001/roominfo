package com.example.myRoomInfo.controller;

import org.springframework.beans.factory.NamedBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;

/**
 * Created by daitian on 2017/6/2.
 */
//@Component
public class CleanRedisCluster {

    @Autowired
    JedisCluster jedisCluster;

    @Resource(name = "redisTemplate")
    ValueOperations ops;


//    @Scheduled()
//    public void reportCurrentTimes() {
//        jedisCluster.set("id58","0");
//        jedisCluster.set("idf","0");
//        jedisCluster.set("idg","0");
//        System.out.println("操作成功!");
//    }

    @Scheduled(fixedRate = 1000)
    public void reportCurrentTimes() {
        ops.set("11","22");
        jedisCluster.del("roominfo");
        jedisCluster.del("id58");
        jedisCluster.del("id58");
        System.out.println("操作成功!");
    }
}
