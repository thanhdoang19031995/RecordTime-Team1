/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManageTag;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.Vector;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

/**
 *
 *
 */
public class BusManageTag {

    public String ListTag(String UserId) {
        String re = "";
        Random r = new Random();
        long l = r.nextLong();
        String url = "http://localhost:8080/K19T1_Team1/TagServlet?IdUser=" + UserId + "&rand=" + l;

        re = ConnectManageTag(url);
        return re;
    }

    public String AddTag(String UserId, String nameTag) {
        nameTag = encryption(nameTag);
        String re = "";
        String url = "http://localhost:8080/K19T1_Team1/AddTagSeverlet?IdUser=" + UserId + "&NameTag=" + nameTag;
        re = ConnectManageTag(url);
        return re;
    }

    public String Edittag(String UserId, String Idtags, String nameTag) {
        nameTag = encryption(nameTag);
        String re = "";
        String url = "http://localhost:8080/K19T1_Team1/EditTagSeverlet?IdUser=" + UserId + "&NameTag=" + nameTag + "&IdTag=" + Idtags;
        re = ConnectManageTag(url);
        return re;
    }

    public String DeleteTag(String Idtags) {
        String re = "";
        String url = "http://localhost:8080/K19T1_Team1/DeleteTagSeverlet?IdTag=" + Idtags;

        re = ConnectManageTag(url);
        return re;
    }

    public String ConnectManageTag(String URL) {

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
    public static String keyEncryption = "%20";

    public static String encryption(String sValue) {
        String sEncryption = "";
        sValue = sValue.trim();
        for (int i = 0; i < sValue.length(); i++) {
            if (sValue.substring(i, i + 1).equals(" ")) {
                sEncryption += keyEncryption;
            } else {
                sEncryption += sValue.substring(i, i + 1);
            }
        }
        return sEncryption;
    }

    public static String decryption(String sDecryp) {
        String[] sValue = Split(sDecryp, keyEncryption);
        String sResult = "";
        for (int i = 0; i < sValue.length; i++) {
            sResult += sValue[i] + " ";
        }
        return sResult.trim();
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

//    public boolean isSpecialCharacter(String sValue){
//         return true;  
//    }
}
