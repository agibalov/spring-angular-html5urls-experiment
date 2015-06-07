package me.loki2302;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Controller
    public static class HomeController {
        @RequestMapping("/test")
        @ResponseBody
        public String hello() {
            return "hello there!";
        }

        @RequestMapping({
                "/page1",
                "/page2",
                "/pages/page3"
        })
        public String indexHtml() {
            return "forward:/index.html";
        }
    }
}
