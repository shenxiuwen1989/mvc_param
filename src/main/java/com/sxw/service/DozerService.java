/*
 * @(#)EJBGenerator.java 1.0 2018年7月20日
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
package com.sxw.service;

import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class DozerService {

    @Autowired
    protected Mapper dozerMapper;

    public <T, S> T convert(S s, Class<T> clz) {
        if (s == null || clz == null) {
            return null;
        }
        
        try {
            T t = clz.newInstance();
            return convert(s, t);
        } catch (Exception e) {
            log.error("dozer反射实例化对象失败",e);
            return null;
        }
        
    }
    
    public <T, S> T convert(S s, T t) {
        if (s != null && t != null) {
            this.dozerMapper.map(s, t);
            return t;
        }
        return null;
    }

    public <T, S> List<T> convert(List<S> s, Class<T> clz) {
        if (s == null) {
            return null;
        }
        List<T> list = new ArrayList<T>();
        for (S vs : s) {
            list.add(this.dozerMapper.map(vs, clz));
        }
        return list;
    }

    public <T, S> Set<T> convert(Set<S> s, Class<T> clz) {
        if (s == null) {
            return null;
        }
        Set<T> set = new HashSet<T>();
        for (S vs : s) {
            set.add(this.dozerMapper.map(vs, clz));
        }
        return set;
    }

    public <T, S> T[] convert(S[] s, Class<T> clz) {
        if (s == null) {
            return null;
        }
        @SuppressWarnings("unchecked")
        T[] arr = (T[]) Array.newInstance(clz, s.length);
        for (int i = 0; i < s.length; i++) {
            arr[i] = this.dozerMapper.map(s[i], clz);
        }
        return arr;
    }
}
