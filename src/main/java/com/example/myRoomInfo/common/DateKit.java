package com.example.myRoomInfo.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by daitian on 2017/5/13.
 */
public class DateKit {

    public static String getDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
}
