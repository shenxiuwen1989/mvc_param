package com.sxw.lambda; /*
 * @(#)ThreadLambda.java 1.0 2018/10/12
 * @Copyright:  Copyright © 2007-2018 ky-express.com.All Rights Reserved.
 * @Description: 
 * 
 * @Modification History:
 * @Date:        2018/10/12
 * @Author:      
 * @Version:     1.0.0.0
 * @Description: (Initialize)
 * @Reviewer:    
 * @Review Date: 
 */

public class ThreadLambda {

    public static void main(String[] args) {
        StringBuffer buffer = new StringBuffer();
        new Thread(() -> {
            buffer.append("通过lambda表达式开启一个子线程\n");
            System.out.println(buffer);
        }).start();

    }
}

