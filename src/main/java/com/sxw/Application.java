package com.sxw;

import com.sxw.util.ApplicationContextUtil;
import com.sxw.util.Local;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ApplicationContextUtil.context = SpringApplication.run(Application.class);

        //
        Local.init();
    }
}
