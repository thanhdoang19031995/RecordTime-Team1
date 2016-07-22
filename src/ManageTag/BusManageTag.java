/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManageTag;
import java.io.IOException;
import java.io.InputStream;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
/**
 *
 * @author TrungKien
 */
public class BusManageTag {
    public String ListTag( String UserId){
        String re = "";
        String url = "http://localhost:8080/K19T1_Team1/TagServlet?IdUser="+ UserId;
        re = ConnectManageTag(url);
        return re;
    }
    
     public String ConnectManageTag(String URL){
         
        String re ="";
            long len = 0;
            int ch = 0;
            String s = "";
            try {
                // open connection
                HttpConnection conn=(HttpConnection)Connector.open(URL);       
                StringBuffer sb = new StringBuffer();
                // get source
                InputStream in=conn.openInputStream();
                len = conn.getLength();
                    if (len != -1) {
                      for (int i = 0; i < len; i++)
                        if ((ch = in.read()) != -1)
                          sb.append((char) ch);
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
