/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagerTime;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author TrungKien
 */
public class BusManageTime {

    public static String getDateString(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return year+"-"+month+"-"+day;
    }
    
    public static String getTimeString(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return hour+":"+minute+":"+second;
    }

    public String AddTime(String IdTag, String IdUser, String DateEnter, String BeginTime, String EndTime, String Content) {
        String re = "";
        String url = "http://localhost:8085/K19T1_Team1/AddTimeRecordSeverlet?IdTag=" + IdTag + "&IdUser=" + IdUser + "&DateEnter=" + DateEnter + "&BeginTime=" + BeginTime + "&EndTime=" + EndTime + "&Content=" + Content;
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

}
