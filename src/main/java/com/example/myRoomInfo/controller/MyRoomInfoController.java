package com.example.myRoomInfo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

import java.util.List;

/**
 * Created by daitian on 2017/6/2.
 */
@RestController
public class MyRoomInfoController {

    @Autowired
    JedisCluster jedisCluster;

    @GetMapping("all")
    public String taskTest() {
        List<String> roominfo = jedisCluster.lrange("roominfo", 0, 30);
        return roominfo.toString();
    }

    //初始化redis
    @GetMapping("init")
    public String taskTest2() {
        jedisCluster.set("id58","0");
        jedisCluster.set("idf","0");
        jedisCluster.set("idg","0");
        return "success";
    }

}
