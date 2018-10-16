package com.sxw.encryption;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES算法操作与AES类似，不同的是AES支持128，192，256三种密钥长度，加密强度更高，但是由于美国出口限制需要另外下载无政策和司法文件否则程序运行时会报错。
 * AES加密时抛出java.security.InvalidKeyException: Illegal key size or default parameters时，是因为因为美国对软件出口的控制。解决办法：
 * https://blog.csdn.net/shangpusp/article/details/7416603
 *
 */
@Slf4j
public class AESUtil {


    /**
     * 生成AES的密钥key
     */
    public static String getKeyAES(){
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);//设置AES密钥算法为128位
            SecretKey secretKey = keyGen.generateKey();//获取密钥key
            String base64String = Base64Util.byte2Base64(secretKey.getEncoded());
            log.info("生成AES的密钥key[{}]",base64String);
            return base64String;
        }catch (Exception e){
            log.error("获取AES的密钥key异常",e);
        }
        return "";
    }

    /**
     * 根据BASE64解码后的字符串）加载AES的密钥key
     * @param base64String
     * @return
     */
    public static SecretKey loadKeyAES(String base64String){
        try {
            byte[] bytes = Base64Util.base642byte(base64String);
            SecretKey key = new SecretKeySpec(bytes,"AES");
            return key;
        }catch (Exception e){
            log.error("（根据BASE64解码后的字符串）加载AES的密钥key异常",e);
        }
        return null;
    }


    /**
     * 加密
     * 当转换为字符串时乱码，由于内存数据流和字符串的编码规则不同。
     * 解决方法：
     * 把加密后的字节数组再通过base64加密一次，乱码问题解决，只需要AES解密之前用base64解密就可以了。
     */
    public static String encryptAES(String content,String key){
        try {
            Cipher cipher = Cipher.getInstance("AES");
            SecretKey secretKey = loadKeyAES(key);
            cipher.init(Cipher.ENCRYPT_MODE,secretKey);
            byte[] bytes = cipher.doFinal(content.getBytes());
            return Base64Util.byte2Base64(bytes);
        }catch (Exception e){
            log.error("AES加密失败",e);
        }
        return "";
    }

    /**
     * 解密
     */
    public static String decryptAES(String content,String key){
        try {
            Cipher cipher = Cipher.getInstance("AES");
            SecretKey secretKey = loadKeyAES(key);
            cipher.init(Cipher.DECRYPT_MODE,secretKey);
            byte[] bytes = cipher.doFinal(Base64Util.base642byte(content));
            return new String(bytes);
        }catch (Exception e){
            log.error("AES解密失败",e);
        }
        return "";
    }

    public static void main(String[] args) {
           String en = AESUtil.getKeyAES(); //AnB2TAcN5fI=
        //  log.info(en);
        log.info("加密后数据【{}】",AESUtil.encryptAES("你好，kye!","4Z3xKvrMcWnbFg2iw8ozww=="));
        log.info("解密后数据【{}】",AESUtil.decryptAES("pi5o2tiAOQkMzSUH3jSDpQ==","4Z3xKvrMcWnbFg2iw8ozww=="));
    }

}
