package com.example.myRoomInfo.controller;

import com.example.myRoomInfo.common.DateKit;
import com.example.myRoomInfo.entity.RoomInfo58;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.util.Date;
import java.util.Iterator;

/**
 * Created by daitian on 2017/5/31.
 */
@Component
public class Task58Test {

    @Autowired
    JedisCluster jedisCluster;

    String one = "http://ty.58.com/ershoufang/0/";
    String ones = "http://ty.58.com/ershoufang/11111x.shtml";

    @Scheduled(fixedRate = 10000)
    public void tongcheng() {

        try {
            //获取最新消息
            Document document = Jsoup.connect(one).get();
            Elements element = document.getElementsByClass("house-list-wrap");

            Elements li = element.select("li");//
            for (Iterator<Element> iterator = li.iterator(); iterator.hasNext(); ) {
                Element next = (Element) iterator.next();
                RoomInfo58 roomInfo58 = new RoomInfo58();


                String id = next.attr("logr").substring(19, 33);
                Long number = jedisCluster.sadd("ids", id);
                if (number == 0) {
                    continue;
                }
                //如果id存在 continue
                roomInfo58.setId(id);
                roomInfo58.setComefrom("58同城");
                roomInfo58.setCreatetime(DateKit.getDateTime());
                roomInfo58.setTitle("" + next.select("h2").text());
                roomInfo58.setStyle("" + next.select("p[class=baseinfo]").get(0).text());
                roomInfo58.setPosition("" + next.select("p[class=baseinfo]").get(1).text());
                roomInfo58.setName("" + next.select("span[class=jjrname-outer]").text());
                roomInfo58.setPrice(next.select("p[class=sum]").text());
                roomInfo58.setArea(next.select("p[class=unit]").text());
                String url = ones.replace("11111", id);
                roomInfo58.setUrl(url);

                //获取手机号
                Document doc = Jsoup.connect(url).get();
                roomInfo58.setPhone(doc.select("p[class=phone-num]").text());

                //TODO 如果手机号是null 放地址

                jedisCluster.lpush("roominfo", roomInfo58.toString());
            }
            jedisCluster.ltrim("roominfo", 0, 10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
