package com.sxw.encryption;


import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 对称加密算法：DES
 * DES加密效果没有AES对称加密安全
 */
@Slf4j
public class DESUtil {

    /**
     * 生成DES的密钥key
     */
    public static String getKeyDES(){
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("DES");
            keyGen.init(56);//设置DES密钥算法为56位
            SecretKey secretKey = keyGen.generateKey();//获取密钥key
            String base64String = Base64Util.byte2Base64(secretKey.getEncoded());
            log.info("生成DES的密钥key[{}]",base64String);
            return base64String;
        }catch (Exception e){
            log.error("获取DES的密钥key异常",e);
        }
        return "";
    }

    /**
     * 根据BASE64解码后的字符串）加载DES的密钥key
     * @param base64String
     * @return
     */
    public static SecretKey loadKeyDES(String base64String){
        try {
            byte[] bytes = Base64Util.base642byte(base64String);
            SecretKey key = new SecretKeySpec(bytes,"DES");
            return key;
        }catch (Exception e){
            log.error("（根据BASE64解码后的字符串）加载DES的密钥key异常",e);
        }
        return null;
    }


    /**
     * 加密
     * 当转换为字符串时乱码，由于内存数据流和字符串的编码规则不同。
     * 解决方法：
     * 把加密后的字节数组再通过base64加密一次，乱码问题解决，只需要des解密之前用base64解密就可以了。
     */
    public static String encryptDES(String content,String key){
        try {
            Cipher cipher = Cipher.getInstance("DES");
            SecretKey secretKey = loadKeyDES(key);
            cipher.init(Cipher.ENCRYPT_MODE,secretKey);
            byte[] bytes = cipher.doFinal(content.getBytes());
            return Base64Util.byte2Base64(bytes);
        }catch (Exception e){
            log.error("DES加密失败",e);
        }
        return "";
    }

    /**
     * 解密
     */
    public static String decryptDES(String content,String key){
        try {
            Cipher cipher = Cipher.getInstance("DES");
            SecretKey secretKey = loadKeyDES(key);
            cipher.init(Cipher.DECRYPT_MODE,secretKey);
            byte[] bytes = cipher.doFinal(Base64Util.base642byte(content));
            return new String(bytes);
        }catch (Exception e){
            log.error("DES解密失败",e);
        }
        return "";
    }

    public static void main(String[] args) {
     //   String en = DESUtil.getKeyDES(); //AnB2TAcN5fI=
      //  log.info(en);
        log.info("加密后数据【{}】",DESUtil.encryptDES("你好，kye!","AnB2TAcN5fI="));
        log.info("解密后数据【{}】",DESUtil.decryptDES("CpcoU/AGrLPxSS7B2vrKEg==","AnB2TAcN5fI="));
    }

}
