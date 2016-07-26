/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManageTag;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
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
        String url = "http://localhost:8085/K19T1_Team1/TagServlet?IdUser=" + UserId + "&rand=" + l;

        re = ConnectManageTag(url);
        return re;
    }

    public String AddTag(String UserId, String nameTag) {
        String re = "";
        String url = "http://localhost:8085/K19T1_Team1/AddTagSeverlet?IdUser=" + UserId + "&name=" + nameTag;
        re = ConnectManageTag(url);
        return re;
    }

    public String Edittag(String UserId, String Idtags, String nameTag) {
        String re = "";
        String url = "http://localhost:8085/K19T1_Team1/EditTagSeverlet?IdUser=" + UserId + "&NameTag=" + nameTag + "&IdTag=" + Idtags;
        re = ConnectManageTag(url);
        return re;
    }

    public String DeleteTag(String Idtags) {
        String re = "";
        String url = "http://localhost:8085/K19T1_Team1/DeleteTagSeverlet?IdTag=" + Idtags;

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

}
