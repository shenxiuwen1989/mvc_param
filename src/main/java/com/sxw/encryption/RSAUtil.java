package com.sxw.encryption;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 *  RSA非对称加密
 *  总的来说就是公私钥，一个做加密，那么另外一个就做解密；一个做解密，那么另外一个就做加密
 */
@Slf4j
public class RSAUtil {



    public static KeyPair getKeyPair(){
        try{
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(512);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            return keyPair;
        }catch (Exception e){
            log.error("获取KeyPair异常");
        }
        return null;
    }

    /**
     * 获取公钥
     * @param keyPair
     * @return
     */
    public static String getPublicKey(KeyPair keyPair){
        try {
            PublicKey publicKey = keyPair.getPublic();
            byte[]bytes = publicKey.getEncoded();
            return Base64Util.byte2Base64(bytes);
        }catch (Exception e){
            log.error("获取公钥异常");
        }
        return "";
    }

    /**
     * 获取私钥
     */
    public static String getPrivate(KeyPair keyPair){
        PrivateKey privateKey = keyPair.getPrivate();
        byte[]bytes = privateKey.getEncoded();
        return Base64Util.byte2Base64(bytes);
    }

    public static PublicKey string2PublicKey(String pubKey){
        try {
            byte[] keyBytes = Base64Util.base642byte(pubKey);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        }catch (Exception e){
            log.error("字符串类型的秘钥转换为PublicKey异常",e);
        }
        return null;
    }

    public static PrivateKey string2PrivateKey(String priKey){
        try {
            byte[] keyBytes = Base64Util.base642byte(priKey);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            return privateKey;
        }catch (Exception e){
            log.error("字符串类型的秘钥转换为PrivateKey异常",e);
        }
        return null;
    }

    /**
     * 公钥加密
     */
    public static String publicEncrypt(String content,PublicKey publicKey){
        try {
        //    byte[] bytes = Base64Util.base642byte(content);

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE,publicKey);
            byte[] encryptByte =  cipher.doFinal(content.getBytes());
            return Base64Util.byte2Base64(encryptByte);
        }catch (Exception e){
            log.error("公钥加密失败",e);
        }
        return "";
    }


    /**
     * 私钥解密
     */
    public static String privateDecrypt(String content,PrivateKey privateKey){
        try {
            byte[] bytes = Base64Util.base642byte(content);

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE,privateKey);
            byte[] encryptByte =  cipher.doFinal(bytes);
            return new String(encryptByte);
        }catch (Exception e){
            log.error("公钥解密失败",e);
        }
        return "";
    }


    public static void main(String[] args) {
//        KeyPair keyPair = RSAUtil.getKeyPair();
//        log.info("私钥：【{}】",RSAUtil.getPrivate(keyPair));
//        log.info("公钥：【{}】",RSAUtil.getPublicKey(keyPair));

        //私钥
        String privateStr = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAobpUQmncRIIB5HQwBGGexuvwmj3jyWTGb8kOaxNd8YBIIkEyJTFE+mAKOrEYaLJnX9d485NgnAEgBFpsRNYx1QIDAQABAkAoN3aGgV6V5qJj1gSuwjvCiZ9uK5++UAIMgDnGpK1CxVbFnyJ3TpSNVTLj6deTExOiWrPsAsdCLGVRt7affwlNAiEA5M4mXFJU/xG9QZ+Ws2KkgRZmC3SSawQi+6nLj8hcq68CIQC08zeAcQcYr4IfUIYVdjvJ1QnXlFsvKlKctbalNH0HuwIgXWpBRS4kH6OZdmJ2v7SFX2LKtOCaKvEca01Om9x84WcCIG4dnGbzYYgqNhtsW0xwOQ4oMOcaByt6q+9lvZJnkLbFAiEAw1TjYjfOG1784YYNJLvy4+sH+PQedqgbEL1hQ+fRf2Y=";
        //公钥
        //String publicStr = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAKG6VEJp3ESCAeR0MARhnsbr8Jo948lkxm/JDmsTXfGASCJBMiUxRPpgCjqxGGiyZ1/XePOTYJwBIARabETWMdUCAwEAAQ==";
        //明文
        //String str = "你好，测试加密！@#";
        //log.info("加密后的数据：【{}】",RSAUtil.publicEncrypt(str,RSAUtil.string2PublicKey(publicStr)));
        log.info("解密后的数据：【{}】",RSAUtil.privateDecrypt("D/5r5lDvDRqLfoDa3hoTmfj4gYAWPSkSm7F9oQPAJWZ8nstJEh25VjW0vGKCFbHwG0NwLhNdtDaw3DsXmzEcpw==",RSAUtil.string2PrivateKey(privateStr)));



    }
}
