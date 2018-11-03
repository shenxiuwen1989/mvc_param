package com.sxw.java8;

import com.sxw.entry.Student;
import org.apache.commons.lang3.StringUtils;


import java.util.*;
import java.util.stream.Collectors;

public class Collection {


    public void testList(List<Student> list){

        //1.拼接
        String str1 = list.stream().map(m->m.getName()).collect(Collectors.joining("-"));
        System.out.println(str1);

        StringBuffer str2 = new StringBuffer();
        list.forEach(m->{
            str2.append(m.getName()+"-");
        });
        System.out.println(str2.toString().substring(0,str2.length()-1));

        //2.获取单个值
        Optional<Student> swx = list.stream().filter(m -> m.getName().equals("swx")).findFirst();//获取名字为"sxw"的一条数据
        System.out.println(swx.isPresent());

        //排序（升序）
        Collections.sort(list, (s1, s2) -> s1.getAge()>s2.getAge()?1:s1.getAge()==s2.getAge()?0:-1);
        System.out.println(list);
        ///排序（倒序）
        Collections.sort(list, (s1, s2) -> s2.getAge()>s1.getAge()?1:s2.getAge()==s1.getAge()?0:-1);
        System.out.println(list);

        List<String> strings = new ArrayList<>();
        strings.add("111");
        strings.add("666");
        strings.add("777");
        strings.add("222");
        strings.add("");
        strings.add("555");
        strings.add("333");
        List<String> list2 = strings.stream().filter(StringUtils::isNotBlank).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        System.out.println(list2);

        List <Map<String,String>> collect =list.parallelStream().map(e -> {
            Map<String,String> map = new HashMap<>();
            map.put("name",e.getName());
            map.put("age",String.valueOf(e.getAge()));
            return map;
        }).collect(Collectors.toList());
        System.out.println(collect);
    }

    public static void main(String[] args) {
        Collection collection = new Collection();

        List<Student> list = new ArrayList<>();
        list.add(new Student("sxw",18));
        list.add(new Student("王华",100));
        list.add(new Student("李四",31));
        list.add(new Student("张三",18));
        collection.testList(list);
    }
}
