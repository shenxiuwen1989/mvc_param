package com.sxw.lambda;

import com.sxw.entry.Student;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    }

    public static void main(String[] args) {
        Collection collection = new Collection();

        List<Student> list = new ArrayList<>();
        list.add(new Student("sxw",18));
        list.add(new Student("王华",20));
        list.add(new Student("李四",31));
        collection.testList(list);
    }
}
