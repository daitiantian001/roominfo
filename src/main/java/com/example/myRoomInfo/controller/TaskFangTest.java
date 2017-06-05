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

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by daitian on 2017/6/1.
 */
@Component
public class TaskFangTest {

    @Autowired
    JedisCluster jedisCluster;

    String fang = "http://esf.taiyuan.fang.com/house/a211-h316/";
    String fangs = "http://esf.taiyuan.fang.com/";

    @Scheduled(fixedRate = 10000)
    public void fang() {

        try {
            //获取最新消息
            Document document = Jsoup.connect(fang).get();
            Elements element = document.getElementsByClass("houseList").select("dl");
            for (Iterator<Element> iterator = element.iterator(); iterator.hasNext(); ) {
                Element next = (Element) iterator.next();

                String id = next.select("dt[class=img rel floatl]").select("a").attr("href");

                RoomInfo58 roomInfo58 = new RoomInfo58();

                Long number = jedisCluster.sadd("ids", id);
                if (number == 0) {
                    continue;
                }

                //如果id存在 continue
                roomInfo58.setId(id);
                roomInfo58.setComefrom("房天下");
                roomInfo58.setCreatetime(DateKit.getDateTime());
                roomInfo58.setTitle("" + next.select("p[class=title]").text());
                roomInfo58.setStyle("" + next.select("p[class=mt12]").text() + next.select("div[class=area alignR]").select("p").first().text().replaceAll("�O", "m2"));
                roomInfo58.setPosition("" + next.select("p[class=mt10]").text());
                roomInfo58.setPrice(next.select("p[class=mt5 alignR]").text());
                roomInfo58.setArea(next.select("p[class=danjia alignR mt5]").text().replaceAll("�O", "m2"));
                String url = fangs + id;
                roomInfo58.setUrl(url);

                Document doc = Jsoup.connect(url).get();
                Elements nexts = doc.getElementsByClass("bookTel");
                roomInfo58.setPhone(nexts.select("strong").text());
                roomInfo58.setName("" + nexts.select("a").text().replaceAll("业主", ""));
                jedisCluster.lpush("roominfo", roomInfo58.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
