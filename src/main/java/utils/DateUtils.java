package utils;

import java.util.Date;
public class DateUtils{
    public static long get_time(){
        long date = new Date().getTime();
        return date;
    }
}