package com.example.websocket.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeFormatter {

    public static String formatTime() {
        StringBuilder sb =  new StringBuilder();
        sb.append("HH:mm:ss");
        SimpleDateFormat sdf = new SimpleDateFormat(sb.toString());
        String dateString = sdf.format(new Date());

        return dateString;
    }
}
