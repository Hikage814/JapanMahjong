package com.hikage.japanmahjong;

import android.text.format.Time;

public class GameLog {

    private static String[] name = new String[4];
    private static String[] score = new String[4];
    private static String datetime;
    private static String info;

    public GameLog(String[] name, String[] score) {
        for (int i = 0; i < 4; i++) {
            this.name[i] = name[i];
            this.score[i] = score[i];
        }
        setInfo();
        setDatetime();
    }

    public static void setInfo() {
        info = "";
        for (int i = 0; i < 4; i++) {
            info += "No." + (i + 1) + ":" + name[i] + "=" + score[i] + "  ";
            if (i == 1)
                info += "\n";
        }
    }

    public static void setDatetime() {
        Time t = new Time(Time.getCurrentTimezone());
        t.setToNow();
        String s = t.year + "-" + t.month + "-" + t.monthDay + "-" + t.hour + ":" + t.minute + ":" + t.second;
        datetime = s;
    }

    public static String getInfo() {
        return info;
    }

    public static String getDatetime() {
        return datetime;
    }

}
