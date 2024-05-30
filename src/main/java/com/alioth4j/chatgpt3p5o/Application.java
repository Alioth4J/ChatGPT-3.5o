package com.alioth4j.chatgpt3p5o;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.alioth4j.chatgpt3p5o.mapper")
public class Application {

    public static void main(String[] args) {
        // TODO
        System.setProperty("http.proxyHost", "127.0.0.1");
        System.setProperty("http.proxyPort", "10809");
        System.setProperty("https.proxyHost", "127.0.0.1");
        System.setProperty("https.proxyPort", "10809");
        SpringApplication.run(Application.class, args);
    }

}
