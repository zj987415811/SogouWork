package com;
import java.io.*;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * @author 马弦
 * @date 2017年10月23日 下午2:49
 * HttpClient工具类
 */
public class Main {

    /**
     * post请求（用于请求json格式的参数）
     *
     * @param url
     * @param params
     * @return
     */
    public static String doPost(String url, String params) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);// 创建httpPost
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Accept-Encoding", "gzip, deflate");
        httpPost.setHeader("Accept-Language", "h-CN,zh;q=0.8");
        httpPost.setHeader("Connection", "keep-alive");
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        httpPost.setHeader("Cookie", "uin_cookie=987415811; euin_cookie=89EDE9981B5C355DA2346836B0C703D4E71F34861F49CF62; pgv_pvi=4232369152; RK=kD7I8b90Vi; ptui_loginuin=987415811; pt2gguin=o0987415811; ptcz=e7057144892216c31aaa8609fa758ead8ec61545d846b3c9615eacc2a2aea7ed; pgv_pvid=426747033; o_cookie=987415811; pgv_si=s3506486272; pgv_info=ssid=s2770174388");
        httpPost.setHeader("Host", "nlp.qq.com");
        httpPost.setHeader("Origin", "http://nlp.qq.com");
        httpPost.setHeader("Referer", "http://nlp.qq.com/semantic.cgi");
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0");
        httpPost.setHeader("X-Requested-With", "XMLHttpRequest");
        String charSet = "UTF-8";
        StringEntity entity = new StringEntity(params, charSet);
        httpPost.setEntity(entity);
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpPost);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK) {
                HttpEntity responseEntity = response.getEntity();
                String jsonString = EntityUtils.toString(responseEntity);
                return jsonString ;
            } else {
            }
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String outText(String realInput) throws UnsupportedEncodingException {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuffer input = new StringBuffer();
        stringBuffer.append("{\"code\":2097152,\"text\":\"");
        stringBuffer.append(realInput).append("\n\",\"type\":1}");
        String text = "{\"code\":2097152,\"text\":\"" + realInput + "\n\",\"type\":1}";
        input.append("api=2&body_data=").append(URLEncoder.encode(stringBuffer.toString(), "UTF-8"));
        System.out.println(input.toString());
        String jsonOut = null;
        try {
            System.out.println(doPost("http://nlp.qq.com/public/wenzhi/api/common_api1469449716.php", input.toString()));
            jsonOut = doPost("http://nlp.qq.com/public/wenzhi/api/common_api1469449716.php", input.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonOut;
    }
    public String readWord(File file) throws IOException {

        BufferedReader reader = null;
        StringBuilder stringBuilder = null;

        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束

            while ((tempString = reader.readLine()) != null) {
                // 显示行号
               line++;
                if (line >= 1000 ) {
                    System.out.println("line " + line + ": " + tempString);
                    stringBuilder.append(tempString);
                }
                if(line>250000)
                    break;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }

        }
         return  stringBuilder.toString();
    }
    public static void main(String[] args) throws UnsupportedEncodingException, FileNotFoundException {
        //String string1="%0A%2C%22";
        //System.out.println( URLDecoder.decode(string1,"UTF-8"));
        /**
         * 1.读取text词组；
         * 2.处理后返回JSon格式并且写入txt；
         * 3.解析JSon
         */
        File file=new File("D:\\MyWork\\mytest.txt");

        Main main=new Main();
        try {
            String realInput=main.readWord(file)+"\\n";
            System.out.println(realInput);
            Main.outText(realInput);
            hanlderJson();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void hanlderJson() {

    }

}

