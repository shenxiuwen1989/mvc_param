package com.sxw;

import com.alibaba.fastjson.JSONObject;
import com.sxw.entry.RequestStudent;
import com.sxw.entry.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试返回数据格式
 */

public class json {


    public static void main(String[] args) {
        RequestStudent requestStudent = new RequestStudent();
        requestStudent.setCode("0000");
        requestStudent.setMsg("查询成功");
        List<Student> data = new ArrayList<>();
        requestStudent.setData(data);
        String str = JSONObject.toJSONString(requestStudent);
        //打印的值为 {"code":"0000","data":[],"msg":"查询成功"}
        System.out.println(str);

        RequestStudent requestStudent1 = new RequestStudent();
        requestStudent1.setCode("0000");
        requestStudent1.setMsg("查询成功");
        List<Student> data1 = new ArrayList<>();
        Student student1 = new Student("sxs",16);
        data1.add(student1);
        requestStudent1.setData(data1);
        String str1 = JSONObject.toJSONString(requestStudent1);
        //打印的值为 {"code":"0000","data":[{"age":16,"name":"sxs"}],"msg":"查询成功"}
        System.out.println(str1);

        RequestStudent requestStudent2 = new RequestStudent();
        requestStudent2.setCode("0000");
        requestStudent2.setMsg("查询成功");
        List<Student> data2 = new ArrayList<>();
        Student student2 = new Student("tony",28);
        data2.add(student1);
        data2.add(student2);
        requestStudent2.setData(data2);
        String str2 = JSONObject.toJSONString(requestStudent2);
        //打印的值为 {"code":"0000","data":[{"age":16,"name":"sxs"},{"age":28,"name":"tony"}],"msg":"查询成功"}
        System.out.println(str2);
    }
}
