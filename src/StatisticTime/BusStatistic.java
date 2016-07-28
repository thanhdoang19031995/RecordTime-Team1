/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StatisticTime;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

/**
 *
 * @author ThanhDoang
 */
public class BusStatistic {
    public static String getDateString(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        //calendar.setTimeZone(value);
        return year + "-" + month + "-" + day;
    }
    
    public static String getTimeString(long time){
        time=time/1000;
        //int dayTime=24*60*60*1000;
        String sResult="";
        sResult+=confixTime(time/3600)+":";
        time=time%3600;
        sResult+=confixTime(time/60)+":";
        time=time%60;
        sResult+=confixTime(time);
        return sResult;
    }
    
    public static String confixTime(long time){
        return time>9?""+time:"0"+time;
    }
    public static String getTimeString(String time1, String time2){
        return getTimeString(getTime(time1, time2));
    }
    public static long getTime(String time1, String time2){
        return getTime(time2)-getTime(time1);
    }
    
    public static long getTime(String time1){
        //int dayTime=24*60*60*1000;
        String[] partTime = Split(time1, ":");

        long hour = Long.parseLong(partTime[0]);
        System.out.println("Step1: "+"year:"+hour);
        long minute = Long.parseLong(partTime[1]);
        System.out.println("Step2: "+"month:"+minute);
        long second = Long.parseLong(partTime[2].trim());
        System.out.println("Step3: "+"month:"+second);
        return (hour*60*60+minute*60+second)*1000;
    }
    
    public String ListTime(String IdUser, String BeginDate, String EndDate) {
        String re = "";
        String url = "http://localhost:8085/K19T1_Team1/StatisticsTimeRecordServlet?IdUser=" + IdUser+ "&BeginDate=" +BeginDate+ "&EndDate=" +EndDate;
        re = ConnectManageTime(url);
        return re;
    }
    
     public String ConnectManageTime(String URL) {
        String re = "";
        long len = 0;
        int ch = 0;
        String s = "";
        try {
            // open connection
            HttpConnection conn = (HttpConnection) Connector.open(URL);
            StringBuffer sb = new StringBuffer();
            // get source
            InputStream in = conn.openInputStream();
            len = conn.getLength();
            if (len != -1) {
                for (int i = 0; i < len; i++) {
                    if ((ch = in.read()) != -1) {
                        sb.append((char) ch);
                    }
                }
            } else {
                while ((ch = in.read()) != -1) {
                    len = in.available();
                    sb.append((char) ch);
                }
            }
            s = sb.toString();
            re = s;
        } catch (IOException ex) {
            ex.getMessage();
        }
        return re;
    }

    public static String[] Split(String splitStr, String delimiter) {
        StringBuffer token = new StringBuffer();
        Vector tokens = new Vector();
        // split
        char[] chars = splitStr.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (delimiter.indexOf(chars[i]) != -1) {
                // we bumbed into a delimiter
                if (token.length() > 0) {
                    tokens.addElement(token.toString());
                    token.setLength(0);
                }
            } else {
                token.append(chars[i]);
            }
        }
        // don't forget the "tail"...
        if (token.length() > 0) {
            tokens.addElement(token.toString());
        }
        // convert the vector into an array
        String[] splitArray = new String[tokens.size()];
        for (int i = 0; i < splitArray.length; i++) {
            splitArray[i] = (String) tokens.elementAt(i);
        }
        return splitArray;
    }
}
