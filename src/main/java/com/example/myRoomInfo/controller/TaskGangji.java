package com.example.myRoomInfo.controller;

import com.example.myRoomInfo.common.DateKit;
import com.example.myRoomInfo.entity.RoomInfo58;
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
 * Created by daitian on 2017/6/28.
 */
@Component
public class TaskGangji {

    @Autowired
    JedisCluster jedisCluster;

    @Autowired
    SimpMessagingTemplate template;

    String gang = "http://ty.ganji.com/fang5/";
    String gangs = "http://ty.ganji.com/fang5/11111.htm";

    @Scheduled(fixedRate = 435000)
    public void fang() {
        try {
            //获取最新消息
            Document document = Jsoup.connect(gang).get();
            Elements element = document.getElementsByClass("f-main-list");

            String phone, s, newId, oldId, sendtime;

            Elements li = element.select("div[class=f-list-item ershoufang-list]");//
            for (Iterator<Element> iterator = li.iterator(); iterator.hasNext(); ) {
                Element next = (Element) iterator.next();
                RoomInfo58 roomInfo58 = new RoomInfo58();

                newId = next.attr("href").replaceAll("/fang5/([\\w]+).htm.+", "$1");//.substring(19, 33);
                sendtime = next.select("div[class=time]").text();

                //根据Id获取最新数据
                oldId = jedisCluster.get("idg");
                if (newId.compareTo(oldId) > 0) {
                    jedisCluster.set("id5g", newId);
                } else {
                    continue;
                }
                //如果id存在 continue
                roomInfo58.setId(newId);
                roomInfo58.setComefrom("赶集网");
                String url = gangs.replace("11111", newId);
                roomInfo58.setUrl(url);

                //获取手机号
                Document doc = Jsoup.connect(url).get();
                Elements e = doc.getElementsByClass("card-info f-fr");
                System.out.println(e.select("span[class=company]").text());

                roomInfo58.setCreatetime(DateKit.getDateTime());
                roomInfo58.setTitle("" + e.select("p[class=card-title]").text());
                roomInfo58.setStyle("" + e.select("ul[class=er-list f-clear]").select("li").get(0).select("span[class=content]").text() + e.select("ul[class=er-list f-clear]").select("li").get(1).select("span[class=content]").text() + e.select("ul[class=er-list f-clear]").select("li").get(2).select("span[class=content]").text() + e.select("ul[class=er-list f-clear]").select("li").get(3).select("span[class=content]").text());
                roomInfo58.setPosition("" + e.select("li[class=er-item f-fl]").get(0).select("a[class=xiaoqu card-blue]").text());
                roomInfo58.setName("" + e.select("a[class=name]").text());
                roomInfo58.setPrice(e.select("span[class=price]").text() + "万元");
                roomInfo58.setArea(e.select("span[class=unit]").text().replace("|", "").trim());
                roomInfo58.setSendtime(sendtime);
                roomInfo58.setPhone(e.select("div[class=c_phone f-clear]").attr("data-phone"));

//                System.out.println(roomInfo58);
                //TODO 如果手机号是null 放地址
                //pnumber=jedisCluster.sadd("phones", phone); 屏蔽某人发的信息
                s = roomInfo58.toString();
//            jedisCluster.lpush("roominfo", s);
//            template.convertAndSend("/topic/rooms",s);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
