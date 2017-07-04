package com.example.myRoomInfo.controller;

import com.example.myRoomInfo.common.DateKit;
import com.example.myRoomInfo.entity.RoomInfo58;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.util.Iterator;

/**
 * Created by daitian on 2017/5/31.
 */
@Component
public class Task58Test {

    Logger log = Logger.getLogger(Task58Test.class);

    @Autowired
    JedisCluster jedisCluster;

    @Autowired
    SimpMessagingTemplate template;

    String one = "http://ty.58.com/ershoufang/0/";
    String ones = "http://ty.58.com/ershoufang/11111x.shtml";

    @Scheduled(fixedRate = 15000)
    public void tongcheng() {

        try {
            //获取最新消息
            Document document = Jsoup.connect(one).get();
            if(!document.hasText()){
                log.info("58数据为空");
            }
            Elements element = document.getElementsByClass("house-list-wrap");

            String phone, newId, s, oldId, sendtime;

            Elements li = element.select("li");
            for (Iterator<Element> iterator = li.iterator(); iterator.hasNext(); ) {
                Element next = (Element) iterator.next();
                RoomInfo58 roomInfo58 = new RoomInfo58();


                newId = next.attr("logr").replaceAll("[\\w]+[\\d]+_([\\d]+)_[\\d]{0,2}_[\\d]{0,2}_.+", "$1");//.substring(19, 33);
                sendtime = next.select("div[class=time]").text();

                //获取最小值,如果newId大于 最小值 加入集合, 控制集合大小 就可以做到无重复.无丢失.

                //TODO 根据Id获取最新数据 id58
                oldId = jedisCluster.get("id58");
                if (newId.compareTo(oldId) > 0) {
                    jedisCluster.set("id58", newId);
                } else {
                    continue;
                }
                //如果id存在 continue
                roomInfo58.setId(newId);
                roomInfo58.setComefrom("58同城");
                roomInfo58.setCreatetime(DateKit.getDateTime());
                roomInfo58.setTitle("" + next.select("h2").text());
                roomInfo58.setStyle("" + next.select("p[class=baseinfo]").get(0).text());
                roomInfo58.setPosition("" + next.select("p[class=baseinfo]").get(1).text());
                roomInfo58.setName("" + next.select("span[class=jjrname-outer]").text());
                roomInfo58.setPrice(next.select("p[class=sum]").text());
                roomInfo58.setArea(next.select("p[class=unit]").text());
                roomInfo58.setSendtime(sendtime);
                String url = ones.replace("11111", newId);
                roomInfo58.setUrl(url);

                //获取手机号
                Document doc = Jsoup.connect(url).get();
                phone = doc.select("p[class=phone-num]").text();
                roomInfo58.setPhone(phone);
                //TODO 如果手机号是null 放地址
                //pnumber=jedisCluster.sadd("phones", phone); 屏蔽某人发的信息
                s = roomInfo58.toString();
                jedisCluster.lpush("roominfo", s);
                template.convertAndSend("/topic/rooms", s);
            }
            jedisCluster.ltrim("roominfo", 0, 10000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
