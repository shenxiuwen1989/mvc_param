package com.sxw.controller;

import com.sxw.entry.Student;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "list")
public class ListController {


    @RequestMapping(value = "post/printPostParam",method = RequestMethod.POST)
    public String postList2(@RequestBody List<Student> list){
        String collect = list.stream().map(e -> e.getName()).collect(Collectors.joining(","));
        return collect;
    }

    /**
     *  这种只适合post方法
     *
     *
     *  http://localhost:8080/mvc/list/post/printPostParam
     *  [{"name":"sxw1","age":"121"},{"name":"sxw2","age":"122"}]
     * 注意：[]表示对象
     *
     * 2.5.springmvc controller层接收List类型的参数
     参考： https://www.cnblogs.com/liusonglin/p/4895694.html
     https://blog.csdn.net/q649381130/article/details/77898683
     */
}
