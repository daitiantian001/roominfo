package com.example.myRoomInfo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @GetMapping("init/{t}")
    public String taskTest2(@PathVariable(value = "t") String t) {
        switch (t) {
            case "58":
                jedisCluster.set("id58", "0");
                break;
            case "fang":
                jedisCluster.set("idf", "0");
                break;
            case "ganji":
                jedisCluster.set("idg", "0");
                break;
            case "all":
                jedisCluster.set("id58", "0");
                jedisCluster.set("idf", "0");
                jedisCluster.set("idg", "0");
                break;
            default:
                return "false";
        }
        return "success";
    }

}
