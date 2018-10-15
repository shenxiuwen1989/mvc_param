package com.sxw.controller;

import com.sxw.entry.Student;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "array")
public class ArrayController {

    @RequestMapping(value = "post/printPostParam",method = RequestMethod.POST)
    public String printAarry(@RequestBody Student[] arr){
        String str = "";
       for(int i=0,length=arr.length;i<length;i++){
           str += arr[i].getName()+"&";
       }
        return str;
    }

    /**
     *
     *   *  这种只适合post方法
     *
     *
     *  http://localhost:8080/mvc/array/post/printPostParam
     *  [{"name":"sxw1","age":"121"},{"name":"sxw2","age":"122"}]
     * 注意：[]表示对象
     */
}
