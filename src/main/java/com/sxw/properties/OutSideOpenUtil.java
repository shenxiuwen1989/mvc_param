package com.sxw.properties;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 仅供调用开放平台使用
 */
@Configuration
public class OutSideOpenUtil {
    private static final Logger logger = LoggerFactory.getLogger(OutSideOpenUtil.class);

    @Value("${outside.open.appkey}")
    private  String appkey;
    @Value("${outside.open.url}")
    private  String url;
    @Value("${outside.open.appsecret}")
    private  String appsecret;

    @PostConstruct
    public void init(){
        System.out.println(appsecret);
        Map<String,String> map = applyAccessToken();
      //  String token = map.get("token");
        System.out.println(appsecret);
    }

    /**
     * 申请token
     * @return
     */
    public  Map<String,String> applyAccessToken(){
        Map<String,String> map = new HashMap<>();
        try {
            String resStr = "{\"appkey\":\""+ appkey+"\",\"appsecret\":\""+ appsecret+"\"}";
            String routResp = applyAccessToken(url+"/security/token", resStr, "application/json");
            logger.info("++++++++++++++++++"+new Date()+"++++++++++申请开放token:"+routResp);
            if(StringUtils.isNotBlank(routResp) ){
                JSONObject object = (JSONObject) JSONObject.parseObject(routResp).get("data");
                if(object !=null){
                    map.put("token",(String) object.get("token"));
                    map.put("refresh_token",(String) object.get("refresh_token"));
                }
            }
            System.out.println(routResp);
        } catch (Exception e) {
            logger.error("初始化开放平台token异常",e);
        }
        return map;
    }

    /**
     * 调用南山内部开放平台
     * @param url
     * @param reqStr
     * @param appliType
     * @return
     * @throws Exception
     */
    public String applyAccessToken(String url, String reqStr, String appliType) throws Exception{
        OutputStreamWriter out = null;
        BufferedReader in = null;
        String resp = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("Content-Type", appliType);
            conn.setRequestProperty("X-from", "openapi_app");

            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
            // 发送请求参数
            out.write(reqStr);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName("UTF-8")));
            String line;
            while ((line = in.readLine()) != null) {
                resp += line;
            }

        } finally {
            try {
                if (out !=null)
                    out.close();
                if (in !=null)
                    in.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return resp;
    }

    /**
     * 刷新token
     * @return
     */
    public  Map<String,String> refreshAccessToken(String token){
        Map<String,String> map = new HashMap<>();
        try {
            String resStr = "{\"refresh_token\":\""+token+"\"}";
            String routResp = applyAccessToken(url +"", resStr, "application/json");
            logger.info("++++++++++++++++++"+new Date()+"++++++++++刷新开放token:"+routResp);
            if(StringUtils.isNotBlank(routResp) ){
                JSONObject object = (JSONObject) JSONObject.parseObject(routResp).get("data");
                if(object !=null){
                    if(object !=null){
                        map.put("token",(String) object.get("token"));
                        map.put("refresh_token",(String) object.get("refresh_token"));
                    }
                }
            }
            System.out.println(routResp);
        } catch (Exception e) {
            logger.error("初始化开放平台token异常",e);
        }
        return map;
    }
}
