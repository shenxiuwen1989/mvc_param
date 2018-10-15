/*
 * @(#)DozerMapperConfig.java 1.0 2018年7月20日
 * @Copyright:  Copyright © 2007-2018 ky-express.com.All Rights Reserved.
 * @Description: 
 * 
 * @Modification History:
 * @Date:        2018年7月20日
 * @Author:      lucius.lv
 * @Version:     1.0.0.0
 * @Description: (Initialize)
 * @Reviewer:    
 * @Review Date: 
 */
package com.sxw.config;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class DozerMapperConfiguration {

    @Bean
    public DozerBeanMapper dozerBeanMapper(@Value("classpath*:dozer/*.xml") Resource[] resources) throws Exception {
        DozerBeanMapper mapper = new DozerBeanMapper();
        if(ArrayUtils.isNotEmpty(resources)) {
            List<String> collect = Arrays.asList(resources).stream().map(e -> {
                try {
                    return e.getURI().toString();
                } catch (IOException e1) {
                    return null;
                }
            }).filter(StringUtils::isNotBlank).collect(Collectors.toList());
            mapper.setMappingFiles(collect);
        }
        mapper.getMappingMetadata();//加载配置文件，避免第一次调用dozer的时候特别慢
        return mapper;
    }
}

