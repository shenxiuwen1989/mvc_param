package com.sxw.controller;

import com.sxw.entry.Student;
import org.springframework.web.bind.annotation.*;

/**
 *
 */
@RestController
@RequestMapping(value = "/rest")
public class BaseController {

    /**
     * http://127.0.0.1:8080/mvc/rest/pathVar/str
     * @param str
     */
    @RequestMapping(value = "/pathVar/{str}",method = RequestMethod.GET)
    public String demoPathVar(@PathVariable String str){
        return str;
    }

    /**
     *
     * 示例： http://127.0.0.1:8080/mvc/rest/requestParam?str="sxw"
     *  如果用map接收的化就必须加上@RequestParam
     */
    @RequestMapping(value = "/requestParam",method = RequestMethod.GET)
    public String passRequestParam(String str){
        return str;
    }

    /**
     *
     * 示例：
     *      url   http://127.0.0.1:8080/mvc/rest/requestParam1
     *      请求参数：
     *          1.1 方式 可以为from-data 或 x-www-from-urlencoded
     *
     */
    @RequestMapping(value = "/requestParam1",method = RequestMethod.POST)
    public String postRequestParam(String str){
        return str;
    }

    /**
     * url上的参数自动映射
     * @param student
     *  示例 ：
     *      http://localhost:8080/mvc/rest/requestParam2?name=sxw&age14
     */
    @RequestMapping(value = "/requestParam2",method = RequestMethod.GET)
    public String getRequestParam(Student student){
        return student.getName();
    }

    /**
     * url上的参数自动映射
     * @param student
     *  示例 ：
     *      http://localhost:8080/mvc/rest/requestParam2
     *      方式:from-data 或 x-www-from-urlencoded
     */
    @RequestMapping(value = "/requestParam3",method = RequestMethod.POST)
    public String requestParam3(Student student){
        return student.getName();
    }

    /**
     * url上的参数自动映射
     * @param student
     *  示例 ：
     *      http://localhost:8080/mvc/rest/requestParam2
     *      方式：raw（json格式）
     */
    @RequestMapping(value = "/requestParam4",method = RequestMethod.POST)
    public String requestParam4(@RequestBody Student student){
        return student.getName();
    }
}
