package com.sxw.java8;

import java.util.ArrayList;
import java.util.List;

/**
 * map方法进行数据计算/转换
 */
public class mapLanbda {

    public static void main(String[] args) {
        List<Integer> integerList = new ArrayList<>();
        integerList.add(50);
        integerList.add(666);
        integerList.add(777);
        integerList.add(333);
        integerList.add(0);
        integerList.add(555);
        integerList.add(20);
        integerList.add(-1);
        //计算
        integerList.stream().map(m->m+0.2*m).forEach(System.out::println);
    }
}
