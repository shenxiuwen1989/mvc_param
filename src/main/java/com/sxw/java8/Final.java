package com.sxw.java8; /*
 * @(#)Final.java 1.0 2018/11/9
 * @Copyright:  Copyright © 2007-2018 ky-express.com.All Rights Reserved.
 * @Description: 
 * 
 * @Modification History:
 * @Date:        2018/11/9
 * @Author:      
 * @Version:     1.0.0.0
 * @Description: (Initialize)
 * @Reviewer:    
 * @Review Date: 
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 修饰数据（局部变量或者成员变量），该变量只能被赋值一次且它的值无法被改变。对于成员变量来讲，我们必须在声明时或者构造方法中对它赋值；
 * 修饰方法的参数，表示在变量的生命周期内不能被修改；
 * 修饰方法，被 final 修饰的方法不能被重写（注：类的private方法会隐式地被指定为final方法）；
 * 修饰类，被 final 修饰的类不能被继承（一般不建议使用）；
 */
public class Final {

    public static void main(String[] args) {
        final String name = "sxw";
        // name = "张三";//报错，修饰数据（局部变量或者成员变量），该变量只能被赋值一次且它的值无法被改变。对于成员变量来讲，我们必须在声明时或者构造方法中对它赋值；
        System.out.println(name);
        final List<String> list = new ArrayList<>();
        list.add("sxw");
        list.add("张三");
        System.out.println(list);
        // list = new ArrayList<>();//报错
    }
}
