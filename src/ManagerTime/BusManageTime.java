/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagerTime;

import java.io.IOException;
import java.io.InputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import java.util.Vector;
import java.util.Calendar;
import java.util.Date;
import java.util.*;
/**
 *
 * @author TrungKien
 */
public class BusManageTime {
    public static int dayTime=86400000;
    public static String getDateString(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        //calendar.setTimeZone(value);
        return year + "-" + month + "-" + day;
    }

    public static String getTimeString(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return hour + ":" + minute + ":" + second;
    }

    public static long getTime(String time1){
        //int dayTime=24*60*60*1000;
        String[] partTime = Split(time1, ":");

        long hour = Long.parseLong(partTime[0]);
        //System.out.println("Step1: "+"year:"+hour);
        long minute = Long.parseLong(partTime[1]);
       // System.out.println("Step2: "+"month:"+minute);
        long second = Long.parseLong(partTime[2].trim());
        //System.out.println("Step3: "+"month:"+second);
        return (hour*60*60+minute*60+second)*1000;
    }
    
    
    public static long getTimeOfDay(String date1) {        
        Calendar calendar = Calendar.getInstance();
        Date date2=new Date();
        calendar.setTime(date2);
        int year2 = calendar.get(Calendar.YEAR);
        int month2 = calendar.get(Calendar.MONTH)+1;
        int day2 = calendar.get(Calendar.DAY_OF_MONTH);
       // System.out.println("Step1: "+"year:"+year2+" month: "+month2+" day: "+day2);
        
        String[] partdate = Split(date1, "-");

        int year1 = Integer.parseInt(partdate[0]);
        //System.out.println("Step2: "+"year:"+year1);
        int month1 = Integer.parseInt(partdate[1]);
        //System.out.println("Step3: "+"month:"+month1);
        int days1 = Integer.parseInt(partdate[2].trim());
        //System.out.println("Step4: "+"day:"+days1);
        //Date returnDate=new Date();//
        int betweenday=getDateBetweenDay(days1, month1, year1, day2, month2, year2);
        return (getDefaulDate().getTime()-betweenday*dayTime);     
    }

    public String AddTime(String IdTag, String IdUser, String DateEnter, String BeginTime, String EndTime, String Content) {
        Content=encryption(Content);
        String re = "";
        String url = "http://localhost:8085/K19T1_Team1/AddTimeRecordSeverlet?IdTag=" + IdTag + "&IdUser=" + IdUser + "&DateEnter=" + DateEnter + "&BeginTime=" + BeginTime + "&EndTime=" + EndTime + "&Content=" + Content;
        re = ConnectManageTime(url);
        return re;
    }

    public String ListTime(String IdUser) {
        String re = "";
        String url = "http://localhost:8085/K19T1_Team1/TimeRecordSeverlet?IdUser=" + IdUser;
        re = ConnectManageTime(url);
        return re;
    }

    public String ListTimeID(String IdTime) {
        String re = "";
        String url = "http://localhost:8085/K19T1_Team1/TimeRecordSelectSeverlet?IdTime=" + IdTime;
        re = ConnectManageTime(url);
        return re;
    }

    public String DeleteTime(String IdTime) {
        String re = "";
        String url = "http://localhost:8085/K19T1_Team1/DeleteTimeRecordSeverlet?IdTime=" + IdTime;
        re = ConnectManageTime(url);
        return re;
    }

    public String EditTime(String IdTime, String IdTag, String DateEnter, String BeginTime, String EndTime, String Content) {
        Content=encryption(Content);
        String re = "";
        String url = "http://localhost:8085/K19T1_Team1/EditTimeRecordSeverlet?IdTime=" + IdTime + "&IdTag=" + IdTag + "&DateEnter=" + DateEnter + "&BeginTime=" + BeginTime + "&EndTime=" + EndTime + "&Content=" + Content;
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
    
    private static int getDateBetweenDay(int day1, int month1,int year1, int day2, int month2, int year2) {
        return getDateOfMonth(month2, isLeadYear(year2))-getDateOfMonth(month1, isLeadYear(year1))+day2-day1;
    }

    private static boolean isLeadYear(int year) {
        return ((year % 4 == 0) && year % 100 != 0) || (year % 400 == 0);
    }

    private static int getDateOfMonth(int month, boolean leap) {
       int iDay=0;
       int dateOfMonth[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
       for(int i=1;i<month;i++){
           iDay+=dateOfMonth[i-1];
       }
       if(month>3&&leap)iDay++;
       return iDay;
    }//get date of month
    
    public static Date getDefaulDate(){
        Date d=new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        long timeH=(hour*60*60+minute*60+second)*1000;
        d.setTime(d.getTime()-timeH);
        return d;
    }
    
    public static String keyEncryption="%20";
    public static String encryption(String sValue){
        String sEncryption="";
        sValue=sValue.trim();
        for(int i=0;i<sValue.length();i++ ){
            if(sValue.substring(i, i+1).equals(" "))
                sEncryption+=keyEncryption;
            else 
                sEncryption+=sValue.substring(i, i+1);
        }
        return sEncryption;
    }
    public static String decryption(String sDecryp){
        String[] sValue=Split(sDecryp, keyEncryption);
        String sResult="";
        for(int i=0;i<sValue.length;i++){
            sResult+=sValue[i]+" ";
        }
        return sResult.trim();
    } 
    
    public static boolean isSpecalCharacter(String value){
        String special = "!@#$%^&*()_";
        for(int i=0;i<value.length();i++){
            for(int j=0;j<special.length();j++){
                if(special.substring(j, j+1).equals(value.substring(i, i+1)))
                    return true;
            }
        }
        return false;
    }
}
