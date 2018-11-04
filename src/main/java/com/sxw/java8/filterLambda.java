package com.sxw.java8;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * 过滤列表（集合中）符合条件的数据
 */
public class filterLambda {

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
        //设置条件
        Predicate<Integer> sta1 = (num)-> num>100;
        Predicate<Integer> sta2 = (num)-> num<100;

        //filter过滤
        integerList.stream().filter(sta1).forEach(lam-> System.out.println(lam));
        System.out.println("-----------------------------------------------------------------");
        integerList.stream().filter(sta2).forEach(lam-> System.out.println(lam));

    }
}
