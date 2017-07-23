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

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by daitian on 2017/6/1.
 */
@Component
public class TaskFangTest {

    Logger log = Logger.getLogger(TaskFangTest.class);

    @Autowired
    JedisCluster jedisCluster;

    @Autowired
    SimpMessagingTemplate template;

    String fang = "http://esf.taiyuan.fang.com/house/a211-h316/";
    String fangs = "http://esf.taiyuan.fang.com/";

    @Scheduled(fixedRate = 15000)
    public void fang() {

        try {
            //获取最新消息
            Document document = Jsoup.connect(fang).get();
            if(!document.hasText()){
                log.info("fangdata数据为空");
            }
            Elements element = document.getElementsByClass("houseList").select("dl");

            String newId, s, oldId;

            for (Iterator<Element> iterator = element.iterator(); iterator.hasNext(); ) {
                Element next = (Element) iterator.next();

                newId = next.select("dt[class=img rel floatl]").select("a").attr("href").replaceAll("/chushou/([\\w]+)\\.htm", "$1");

                oldId = jedisCluster.get("idf");
                if (newId.compareTo(oldId) > 0) {
                    jedisCluster.set("idf", newId);
                } else {
                    continue;
                }

                RoomInfo58 roomInfo58 = new RoomInfo58();
                //如果id存在 continue
                roomInfo58.setId(newId);
                roomInfo58.setComefrom("房天下");
                roomInfo58.setCreatetime(DateKit.getDateTime());
                roomInfo58.setTitle("" + next.select("p[class=title]").text());
                roomInfo58.setStyle("" + next.select("p[class=mt12]").text() + next.select("div[class=area alignR]").select("p").first().text().replaceAll("�O", "m2"));
                roomInfo58.setPosition("" + next.select("p[class=mt10]").text());
                roomInfo58.setPrice(next.select("p[class=mt5 alignR]").text());
                roomInfo58.setArea(next.select("p[class=danjia alignR mt5]").text().replaceAll("�O", "m2"));
                roomInfo58.setSendtime("新上");
                String url = fangs + "chushou/" + newId + ".htm";
                roomInfo58.setUrl(url);

                Document doc = Jsoup.connect(url).get();
                Elements nexts = doc.getElementsByClass("trlcont");
                roomInfo58.setPhone(nexts.select("span[class=pnum]").get(0).text());
                roomInfo58.setName(nexts.select("div[class=btn1_mod yyKanfBtn downBtn]").text());
                s = roomInfo58.toString();
                jedisCluster.lpush("roominfo", s);
                template.convertAndSend("/topic/rooms", s);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
