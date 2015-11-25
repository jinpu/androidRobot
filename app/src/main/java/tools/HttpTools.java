package tools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import bean.Result;
import bean.chatmessage;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * Created by jinpu on 10/22/15.
 */
public class HttpTools {
     private static final String API_URL= "http://www.tuling123.com/openapi/api";
     private static final String API_KEY = "35362daf0583d2cf3a025a9cf5c4eebb";

    public static  chatmessage sendMessage(String msg){
         chatmessage chatmsg = new chatmessage();
         String res = doGet(msg);
         Gson gson = new Gson();
         Result result;
         if(res != null) {
             try {
                 result = gson.fromJson(res, Result.class);
                 chatmsg.setMsg(result.getText());
             } catch (JsonSyntaxException e) {
                 e.printStackTrace();
             }
         }
         chatmsg.setDate(new Date());
         chatmsg.setType(chatmessage.Type.INCOMING);

         return chatmsg;
    }

     public static String doGet(String msg)
     {
         String result = "";
         String url = setParams(msg);
         InputStream is = null;
         ByteArrayOutputStream baos = null;
         try
         {
             URL url1 = new URL(url);
             HttpURLConnection conn =  (HttpURLConnection)url1.openConnection();
             conn.setReadTimeout(5 * 1000);
             conn.setConnectTimeout(5 * 1000);
             conn.setRequestMethod("GET");

             is = conn.getInputStream();
             int len = -1;
             byte[] buf = new byte[128];
             baos = new ByteArrayOutputStream();

             while((len = is.read(buf))!= -1)
             {
                 baos.write(buf,0,len);
             }
             baos.flush();
             result = new String(baos.toByteArray());
             return result;
         }
         catch (MalformedURLException e)
         {
             e.printStackTrace();
         }catch(IOException e)
         {
             e.printStackTrace();
         }finally {
             try {
                 if (baos != null) {
                     baos.close();
                 }
             }
             catch (IOException e)
             {
                 e.printStackTrace();
             }
             try{
                 if (is!=null)
                 {
                    is.close();
                 }
             }catch(IOException e)
             {
                 e.printStackTrace();
             }
         }

         return result;
     }

     private static String setParams(String msg)
     {
         String url = "";
         try{
             url = API_URL+"?key="+API_KEY+"&info="+ URLEncoder.encode(msg,"UTF-8");
         }catch(UnsupportedEncodingException e)
         {
             e.printStackTrace();
         }
         return url;
     }
}
