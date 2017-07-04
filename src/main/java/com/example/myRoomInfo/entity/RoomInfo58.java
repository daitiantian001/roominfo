package com.example.myRoomInfo.entity;

import java.util.Date;

/**
 * Created by daitian on 2017/6/1.
 */
public class RoomInfo58 {
    private String id;
    private String title;
    private String style;
    private String position;
    private String name;
    private String price;
    private String area;
    private String phone;
    private String comefrom;
    private String createtime;
    private String url;
    private String sendtime;

    public String getSendtime() {
        return sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getComefrom() {
        return comefrom;
    }

    public void setComefrom(String comefrom) {
        this.comefrom = comefrom;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + "\"" + id + "\"" +
                ", \"title\":" + "\"" + title + "\"" +
                ", \"style\":" + "\"" +  style + "\"" +
                ", \"position\":" + "\"" + position + "\"" +
                ", \"name\":" + "\"" + name + "\"" +
                ", \"price\":" + "\"" + price + "\"" +
                ", \"area\":" + "\"" + area + "\"" +
                ",\"phone\":" + "\"" + phone + "\"" +
                ",\"comefrom\":" + "\"" + comefrom + "\"" +
                ",\"createtime\":" + "\"" + createtime + "\"" +
                ",\"url\":" + "\"" + url + "\"" +
                ",\"sendtime\":" + "\"" + sendtime + "\"" +
                "}";
    }
}
