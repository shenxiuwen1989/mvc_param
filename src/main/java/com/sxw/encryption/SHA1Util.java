package com.sxw.encryption;


import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;

/**
 * 签名算法二：SHA1
 * SHA1比MD5安全一点，因为SHA1摘要的长度比MD5长
 */
@Slf4j
public class SHA1Util {

    /**
     * MD5摘要
     * @param content ：待加密的内容
     * @param encoding ：加密的编码集，如 “UTF-8”,"GBK"
     * @return
     */
    public static String sha1Signature(String content,String encoding){
        String signature = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(content.getBytes(encoding));
            byte b[] = md.digest();

            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if(i<0) i+= 256;
                if(i<16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();
        }catch (Exception e){
            log.error("SHA1签名异常：【{}】",e);
        }
        return "";
    }

    public static void main(String[] args) {
        String  str =  SHA1Util.sha1Signature("SXW","UTF-8");
        System.out.println(str);
    }
}
