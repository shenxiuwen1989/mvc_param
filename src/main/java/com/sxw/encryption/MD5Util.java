package com.sxw.encryption;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;

/**
 * 签名算法一：MD5
 */
@Slf4j
public class MD5Util {

    /**
     * MD5摘要：接收任意大小的数据，输出固定长度的哈希值
     * @param content ：待加密的内容
     * @param encoding ：加密的编码集，如 “UTF-8”,"GBK"
     * @return
     */
    public static String md5Signature(String content,String encoding){
        String signature = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(content.getBytes(encoding));
            //调用 digest 方法完成哈希计算
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
            log.error("MD5签名异常：【{}】",e);
        }
        return "";
    }


    public static void main(String[] args) {
       String  str =  MD5Util.md5Signature("SXW","UTF-8");
        System.out.println(str);
    }
}
