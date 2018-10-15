package com.sxw.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value="map")
public class MapController {

    /**
     *
     */
    @RequestMapping(value = "get/printGetParam",method = RequestMethod.GET)
    public String printParam(@RequestParam  Map<String,Object> map){
        String buffer = "";
        for (Map.Entry<String,Object> entry : map.entrySet()){
            buffer += entry.getKey()+"="+entry.getValue();
        }

        return buffer;
    }

    @RequestMapping(value = "post/printPostParam1",method = RequestMethod.POST)
    public String printPostParam(@RequestParam  Map<String,Object> map){
        String buffer = "";
        for (Map.Entry<String,Object> entry : map.entrySet()){
            buffer += entry.getKey()+"="+entry.getValue();
        }

        return buffer;
    }

    @RequestMapping(value = "post/printPostParam2",method = RequestMethod.POST)
    public String printPostParam2(@RequestBody Map<String,Object> map){
        String buffer = "";
        for (Map.Entry<String,Object> entry : map.entrySet()){
            buffer += entry.getKey()+"="+entry.getValue();
        }

        return buffer;
    }
    /**
     * 备注：
     *  1.用map接受参数时，
     *  `   1.1请求方法为get:
     *          请求详情：http://localhost:8080/mvc/map/get/printGetParam?isStart=1&userType=1&startNum=0&pageSize=5
     *          参数必须追加到url后面/必须加上@RequestParam注解
     *      1.2请求方法为POST:
     *          1.2.1 如果不加@RequestParam 注解 map接受不到参数
     *          1.2.2 加上@RequestParam 注解，
     *          请求参数可以用from-data或者x-www-from-urlencoded方式，但格式必须为：isStart=1&userType=1&startNum=0&pageSize=5
     *          1.2.3 将注解换成@RequestBody注解,必须用raw方式参数必须是json格式：{"name":"sxw","age":"12"}
     *  2.用map接受参数的优缺点：
     *      优点灵活；
     *      缺点就是可读性性差，不知道用户的key值，当key值比较多时建议用实体
     */
}
