package com.sxw.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.Map.Entry;



public class HttpClientUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    /**
     * 连接超时，单位：毫秒
     */
    private static final int CONNECT_TIME_OUT = 6000;

    /**
     * 读超时，单位：毫秒
     */
    private static final int SOCKET_TIME_OUT = 10000;

    /**
     * 重试次数
     */
    private static final int RETRY_TIMES = 3;

    /**
     * 连接池大小
     */
    private static final int MAX_TOTAL = 200;

    /**
     * 每个路由最大连接数
     */
    private static final int MAX_PER_ROUTE = 100;

    /**
     * 可用空闲连接过期时间，重用空闲连接时会先检查是否空闲时间超过这个时间，如果超过，释放socket重新建立
     */
    private static final int VALIDATE_AFTER_INACTIVITY = 1000;

    private static final String EMPTY_STRING = "";
    private static final String SYMBOL_MARK = "?";
    private static final String SYMBOL_CONNECTOR = "&";
    private static final String SYMBOL_EQUAL = "=";

    private static Executor executor;

    private static RequestConfig requestConfig;

    private static PoolingHttpClientConnectionManager cm;
    private static SSLConnectionSocketFactory socketFactory;
    private static CloseableHttpClient httpClient;
    private static Map<String, String> defaultHeaders;

    private static final String OUT_SIDE_OPEN_CODE = "out.side.open.token";
    private static final Timer cmt = new Timer("FeignApacheHttpClientConfiguration.connectionManagerTimer", true);

    /**
     * 初始化HttpClient和connectionManager配置
     */
    static {
        requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIME_OUT)
                .setConnectTimeout(CONNECT_TIME_OUT)
                .setConnectionRequestTimeout(CONNECT_TIME_OUT)
                .setCookieSpec(CookieSpecs.STANDARD_STRICT)
                .setRedirectsEnabled(false).setExpectContinueEnabled(true)
                .setTargetPreferredAuthSchemes(
                        Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                .setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
                .build();
        if (cm == null) {
            ignoreVerifySSL();
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
                    .<ConnectionSocketFactory> create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", socketFactory).build();
            cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            cm.setDefaultMaxPerRoute(MAX_PER_ROUTE);
            cm.setMaxTotal(MAX_TOTAL);
            cm.setValidateAfterInactivity(VALIDATE_AFTER_INACTIVITY);
        }
        reSetHeader();
        httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig)
                .setConnectionManager(cm)
                .setRetryHandler(new DefaultHttpRequestRetryHandler(RETRY_TIMES, true))
                .build();
        executor = Executor.newInstance(httpClient);
        
        //定时清理过期的连接
        cmt.schedule(new TimerTask() {
            @Override
            public void run() {
                cm.closeExpiredConnections();
            }
        }, 30000, 3000);
    }

    public static void reSetHeader() {
        defaultHeaders = new HashMap<>();
        defaultHeaders.put("Accept",
                "text/html,application/xhtml+xml,application/xml;application/json;q=0.9,image/webp,*/*;q=0.8");
        defaultHeaders.put("Accept-Encoding", "gzip, deflate, sdch, br");
        defaultHeaders.put("Accept-Language", "zh-CN,zh;q=0.8");
        defaultHeaders.put("Upgrade-Insecure-Requests", "1");
        defaultHeaders.put("User-Agent",
                "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
    }

    /**
     * 忽略证书
     */
    private static void ignoreVerifySSL() {
        try {
            TrustManager manager = new X509TrustManager() {
                @Override
                public void checkClientTrusted(
                        X509Certificate[] x509Certificates, String s)
                        throws CertificateException {

                }

                @Override
                public void checkServerTrusted(
                        X509Certificate[] x509Certificates, String s)
                        throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            SSLContext context = SSLContext.getInstance("TLS");// SSLv3
            context.init(null, new TrustManager[] { manager }, null);
            socketFactory = new SSLConnectionSocketFactory(context,
                    NoopHostnameVerifier.INSTANCE);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    public static String outSideOpenPost(String url, MultiValueMap<String, String> headers, String requestBody){

        //1.解析url获取api_code
        String[] arr = url.split("/");
        String apiCode = arr[arr.length-1];
        headers.add("method","open.api.legacy."+apiCode);
        headers.add("appkey",ApplicationContextUtil.context.getEnvironment().getProperty("outside.open.appkey"));

        String remoteUrl = ApplicationContextUtil.context.getEnvironment().getProperty("outside.open.url")+"/router/rest";
        //2.校验是表单还是json请求
        String contentType = headers.toSingleValueMap().get("Content-Type");
        if("application/json".equals(contentType)){
           return postJson(remoteUrl, headers.toSingleValueMap(), requestBody);
        }else{//表单提交
            return post( remoteUrl, headers.toSingleValueMap(),  requestBody);
        }
    }

    /**
     * post请求
     * from表单提交
     * @return
     */
    public static String post(String url, Map<String, String> header, String requestBody) {
        List<Header> headers = new ArrayList<>();
        if (Objects.nonNull(header) && header.size() > 0) {
            for(Entry<String, String> entry : header.entrySet()) {
                headers.add(new BasicHeader(entry.getKey(), entry.getValue()));
            }
        }
        ContentType contentType = ContentType.create("application/x-www-form-urlencoded", Consts.UTF_8);
        return execute(Request.Post(url).bodyString(requestBody, contentType).setHeaders(headers.toArray(new Header[] {})));
    }

    /**
     * post请求
     * json提交
     */
    public static String postJson(String url, Map<String, String> headers, String json) {
        StringEntity se = new StringEntity(json, Consts.UTF_8);
        se.setContentEncoding("UTF-8");  
        se.setContentType("application/json");
        Request post = Request.Post(url);
        post.addHeader(HTTP.CONTENT_TYPE,"application/json");

        if(headers != null) {
            headers.forEach((k,v)->{
                post.addHeader(k,v);
            });
        }
        return execute(post.body(se));
    }

    /**
     * 执行request
     *
     * @param request
     *            Request
     * @return String
     */
    public static String execute(Request request) {
        try {
            return executor.execute(request).returnContent().asString(Consts.UTF_8);
        } catch(ConnectTimeoutException e){
            logger.error("Execute request error", e);
            throw new RuntimeException("连接远程服务器超时");
        }catch (Exception e) {
          logger.error("Execute request error", e);
            throw new RuntimeException("连接远程服务器异常");
        }
    }


    /**
     * 申请token
     * @return
     */
    public static synchronized void applyAccessToken(){
        String token = "";
        try {
            String appkey = ApplicationContextUtil.context.getEnvironment().getProperty("outside.open.appkey");
            String appsecret = ApplicationContextUtil.context.getEnvironment().getProperty("outside.open.appsecret");
            String url = ApplicationContextUtil.context.getEnvironment().getProperty("outside.open.url");
            String resStr = "{\"appkey\":\""+ appkey+"\",\"appsecret\":\""+ appsecret+"\"}";
            String routResp = applyAccessToken(url+"/security/token", resStr, "application/json");
            logger.info("++++++++++++++++++"+new Date()+"++++++++++申请开放token:"+routResp);
            if(StringUtils.isNotBlank(routResp) ){
                JSONObject object = (JSONObject) JSONObject.parseObject(routResp).get("data");
                if(object !=null){
                    String refreshToken = (String) object.get("refresh_token");
                    if(StringUtils.isNotBlank(refreshToken)){
                        Map<String,String> result = refreshAccessToken(refreshToken);
                        token = result.get("token");
                    }else {
                        token = (String) object.get("token");
                    }
                }
            }
            if(StringUtils.isNotBlank(token)){
                 //  Redis.set(OUT_SIDE_OPEN_CODE,token);
            }else{
                logger.error(">>>>>>>>>>>>>初始化对外开放平台token为空，请检查原因");
            }

        } catch (Exception e) {
            logger.error("初始化对外开放平台token异常",e);
        }
    }

    /**
     * 调用南山内部开放平台
     * @param url
     * @param reqStr
     * @param appliType
     * @return
     * @throws Exception
     */
    private static String applyAccessToken(String url, String reqStr, String appliType) throws Exception{
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
    private static Map<String,String> refreshAccessToken(String refreshToken){
        Map<String,String> map = new HashMap<>();
        try {
            String url = ApplicationContextUtil.context.getEnvironment().getProperty("outside.open.url");
            String resStr = "{\"refresh_token\":\""+refreshToken+"\"}";
            String routResp = applyAccessToken(url +"/security/token/refresh", resStr, "application/json");
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

    public static void main(String[] args) {

    }
}
