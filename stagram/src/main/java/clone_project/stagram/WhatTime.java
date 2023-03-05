package clone_project.stagram;

import org.springframework.context.annotation.Configuration;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class WhatTime {

    /** 현재 시간 구하기 **/
    public static String whatTimeIsItNow() {
        Date timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now_dt = sdf.format(timestamp);

        System.out.println(now_dt);

        return now_dt;
    }
}
