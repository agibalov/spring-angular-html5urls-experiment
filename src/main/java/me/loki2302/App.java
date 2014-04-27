package me.loki2302;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

public class App {
    public static void main(String[] args) {
        SpringApplication.run(Configuration.class, args);
    }

    @EnableAutoConfiguration
    @ComponentScan
    public static class Configuration {

    }

    @Controller
    public static class HomeController {
        @RequestMapping("/test")
        @ResponseBody
        public String hello() {
            return "hello there!";
        }

        @RequestMapping("/page1")
        public String page1() {
            return "forward:/index.html";
        }

        @RequestMapping("/page2")
        public String page2() {
            return "forward:/index.html";
        }

        @RequestMapping("/pages/page3")
        public String page3() {
            return "forward:/index.html";
        }
    }
}
