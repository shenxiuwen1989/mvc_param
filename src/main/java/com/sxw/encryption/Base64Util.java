package com.sxw.encryption;

import lombok.extern.slf4j.Slf4j;

/**
 * 编码/解码 ：Base64
 * Java 8之后的作法，与sun.mis c套件和Apache Commons Codec所提供的Base64编解码器来比较的话，Java 8提供的Base64拥有更好的效能。
 * 实际测试编码与解码速度的话，Java 8提供的Base64，要比sun.mis c套件提供的还要快至少11倍，比Apache Commons Codec提供的还要快至少3倍。因此在Java上若要使用Base64，这个Java 8底下的java .util套件所提供的Base64类别绝对是首选！
 */
@Slf4j
public class Base64Util {

    private static final java.util.Base64.Decoder decoder = java.util.Base64.getDecoder();
    private static final java.util.Base64.Encoder encoder = java.util.Base64.getEncoder();

    /**
     * 编码
     * @param content
     * @return
     */
    public static String encoderToString(String content){
        try{
            byte[] contentByte = content.getBytes();
            String encodedText = byte2Base64(contentByte);
            return encodedText;
        }catch (Exception e){
            log.error("Base64编码异常【{}】",e);
        }
        return "";
    }

    public static String byte2Base64(byte[] contentByte){
        return encoder.encodeToString(contentByte);
    }
    /**
     * 解码
     * @param content
     * @return
     */
    public static String decoderToString(String content){
        try{
            byte[]  decoderText = base642byte(content);
            return new String(decoderText);
        }catch (Exception e){
            log.error("Base64解密异常【{}】",e);
        }
        return "";
    }

    public static byte[] base642byte(String base64){
        return decoder.decode(base64);
    }

    public static void main(String[] args) {
        String en = Base64Util.encoderToString("sxs");
        log.info("编码【{}】",en);
        String de = Base64Util.decoderToString(en);
        log.info("解码【{}】",de);
    }
}
