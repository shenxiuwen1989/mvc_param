package com.sxw.encryption;


import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * 数字签名算法 MD5withRSA
 *  首先用MD5对正文摘要，然后用RSA对正文加解密
 */
@Slf4j
public class MD5withRSA {

    /**
     * 私钥生成签名
     * @param content
     * @param privateKey
     * @return
     */
    public static String sign(String content, PrivateKey privateKey){

        try {

            //1.对正文MD5摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest();

            //2.对摘要RSA私钥加密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE,privateKey);
            byte[] encryptByte =  cipher.doFinal(bytes);
            return Base64Util.byte2Base64(encryptByte);
        }catch (Exception e){
            log.info("私钥生成签名异常",e);
        }

        return "";
    }

    /**
     * 验证签名
     */
    public static boolean verify(String content, String sign, PublicKey publicKey){
        try {
            //1.对正文MD5摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest();

            //2.对签名公钥解密
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE,publicKey);
            byte[] encryptByte =  cipher.doFinal(bytes);

            if(Base64Util.byte2Base64(bytes).equals(Base64Util.byte2Base64(encryptByte))){
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            log.info("验证签名异常",e);
        }
        return false;
    }
}
