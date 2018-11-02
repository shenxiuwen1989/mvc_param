package com.sxw.apache; /*
 * @(#)StringTest.java 1.0 2018/10/31
 * @Copyright:  Copyright © 2007-2018 ky-express.com.All Rights Reserved.
 * @Description: 
 * 
 * @Modification History:
 * @Date:        2018/10/31
 * @Author:      
 * @Version:     1.0.0.0
 * @Description: (Initialize)
 * @Reviewer:    
 * @Review Date: 
 */

import org.apache.commons.lang3.StringUtils;

public class StringTest {
    public static void main(String[] args) {
        //找到2个字符串第一个出现不同的位置（1开始）
       String difference = StringUtils.difference("s123", "s13");
        System.out.println(difference);//3

        //判断2个字符串是否相等
        boolean equals = StringUtils.equals("s1", "s1");
        System.out.println(equals);//true

        //判断字符串里面是否含有特定字符串
        boolean b2 = StringUtils.contains("asd", "as");
        System.out.println(b2);//true

        //把数组的元素用:进行拼接
        String concatStr = StringUtils.join(new String[]{"dog", "cat", "monkey"},":");
        System.out.println(concatStr);//dog:cat:monkey

        //根据特定分隔符对字符串进行拆分
        String[] split = StringUtils.split("apple|xiaomi|dell|lenovo", "|");
        for (String s1 : split) {
            System.out.print(s1 + "、");//apple、xiaomi、dell、lenovo、
        }
        System.out.println();

        //统计某个字符串在字符串出现的次数
        int matchCount = StringUtils.countMatches("Happy Birthday to you", "o");
        System.out.println(matchCount);//2

        //必须要8位，不够的就拿0去字符串左边补
        String leftPad = StringUtils.leftPad("54", 8, "0");
        System.out.println(leftPad);//00000054

        //必须要8位，不够的就拿0去字符串右边补
        String rightPad = StringUtils.rightPad("54", 8, "0");
        System.out.println(rightPad);//54000000

        //判断字符串是否以特定字符串开头，区分大小写
        boolean startsWith = StringUtils.startsWith("GoodMorning", "go");
        System.out.println(startsWith);//false

        //判断字符串是否以特定字符串开头，区分大小写
        boolean endsWith = StringUtils.endsWith("GoodMorning", "ing");
        System.out.println(endsWith);//true
    }
}
