package com.example.myRoomInfo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

/**
 * Created by daitian on 2017/6/2.
 */
//@Component
public class CleanRedisCluster {

    @Autowired
    JedisCluster jedisCluster;

//    @Scheduled()
//    public void reportCurrentTimes() {
//        jedisCluster.set("id58","0");
//        jedisCluster.set("idf","0");
//        jedisCluster.set("idg","0");
//        System.out.println("操作成功!");
//    }

    @Scheduled(fixedRate = 1000)
    public void reportCurrentTimes() {
        jedisCluster.del("roominfo");
        jedisCluster.del("id58");
        jedisCluster.del("id58");
        System.out.println("操作成功!");
    }
}
