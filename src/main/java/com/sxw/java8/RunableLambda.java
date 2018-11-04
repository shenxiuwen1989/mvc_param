package com.sxw.java8; /*
 * @(#)RunableLambda.java 1.0 2018/11/2
 * @Copyright:  Copyright © 2007-2018 ky-express.com.All Rights Reserved.
 * @Description: 
 *  匿名内部类
 */

public class RunableLambda {
    public static void main(String[] args) {
        StringBuffer buffer = new StringBuffer();
        Runnable runnable = ()-> {
            buffer.append("通过lambda表达式开启一个子线程\n");
            System.out.println(buffer);
        };
        runnable.run();
    }
}
