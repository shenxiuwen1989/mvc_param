package com.sxw.java8; /*
 * @(#)Sort.java 1.0 2018/10/24
 * @Copyright:  Copyright © 2007-2018 ky-express.com.All Rights Reserved.
 * @Description: 
 * 
 * @Modification History:
 * @Date:        2018/10/24
 * @Author:      
 * @Version:     1.0.0.0
 * @Description: (Initialize)
 * @Reviewer:    
 * @Review Date: 
 */

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class Sort {
    /**
     * stream().sorted()/Comparator.naturalOrder()/Comparator.reverseOrder()，要求元素必须实现Comparable接口。
     * @param args
     */
    public static void main(String[] args) {

        listSort();

        arraySort();

        getSignStr();
    }

    private static void arraySort() {
        String[]sportsArr={"xiaofen","yuechu","yixiaochuan"};
        //未使用lambda表达式
        Arrays.sort(sportsArr, new Comparator<String>() {
            @Override
            public int compare(String arg0, String arg1) {
                return arg0.compareTo(arg1);
            }
        });
        System.out.println(sportsArr);
        //使用表达式
        Arrays.sort(sportsArr,(s1,s2)->s1.compareTo(s2));
        System.out.println(sportsArr);
    }

    private static void listSort() {
        List<String> strings = new ArrayList<>();
        strings.add("111");
        strings.add("666");
        strings.add("777");
        strings.add("333");
        strings.add("");
        strings.add("555");
        strings.add("333");
        strings.add("");
        System.out.println("初始值"+strings);
        //1.选出不为空的元素，然后倒序，然后转换为list
        List<String> list = strings.stream().filter(StringUtils::isNotBlank).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        System.out.println("降序"+list);

        //2.升序
        List<String> list2 = strings.stream().filter(StringUtils::isNotBlank).sorted((x,y)->{
            return x.compareTo(y);
        }).collect(Collectors.toList());
        System.out.println("升序"+list2);
        //3.自然升序
        List<String> list3 = strings.stream().filter(StringUtils::isNotBlank).sorted().collect(Collectors.toList());
        System.out.println("自然排序"+list3);
    }

    /**
     * 比较重要
     *ASCII排序
     */
    public static String getSignStr() {
        Map<String, Object> map = new HashMap<>();
        map.put("sn","7A9F265AB830485AA387343E0635C752");
        map.put("phone","13242957750");
        map.put("isOK","true");

        ArrayList<String> keys = new ArrayList<>();
        Iterator mapItor = map.entrySet().iterator();
        while (mapItor.hasNext()) {
            Map.Entry<String, Object> entry = (Map.Entry) mapItor.next();
            if (StringUtils.isNotBlank(entry.getValue().toString())) {
                keys.add((String) entry.getKey() + "" + entry.getValue());
            }
        }

        Collections.sort(keys);
        StringBuilder requestStr = new StringBuilder();
        Iterator keyItor = keys.iterator();

        while (keyItor.hasNext()) {
            String key = (String) keyItor.next();
            requestStr.append(key);
        }

        return requestStr.toString();
    }
}
