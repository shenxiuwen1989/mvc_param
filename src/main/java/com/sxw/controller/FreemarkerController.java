package com.sxw.controller; /*
 * @(#)FreemarkerController.java 1.0 2018/11/2
 * @Copyright:  Copyright © 2007-2018 ky-express.com.All Rights Reserved.
 * @Description: 
 * 
 * @Modification History:
 * @Date:        2018/11/2
 * @Author:      
 * @Version:     1.0.0.0
 * @Description: (Initialize)
 * @Reviewer:    
 * @Review Date: 
 */

import com.sxw.entry.FindWEBLogisticsYDCustomParam;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import freemarker.template.Configuration;

import java.io.StringWriter;

@RestController
@RequestMapping(value = "freemarker")
public class FreemarkerController {

    @Autowired
    private Configuration configuration;
    @RequestMapping(value = "/test" ,method = RequestMethod.POST)
    public String find_WEB_LogisticsYD_Custom(@RequestBody FindWEBLogisticsYDCustomParam param){

        try {
            Template template = configuration.getTemplate("Find_WEB_LogisticsYD_Custom.ftl");
            StringWriter sw = new StringWriter();
             template.process(param, sw);
            return sw.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return "ERROR";
    }
}
