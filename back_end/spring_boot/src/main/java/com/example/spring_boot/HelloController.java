package com.example.spring_boot;

import model.GareDatabase;
import model.YenKSP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/hello")
    public String sayHello() {
        logger.info("hello");
        return "hello world!";
    }

    @GetMapping("/init")
    public String callGareDatabase() {
        logger.info("init");
        GareDatabase.create_all_class();
        return "create_all_class() initialized";
    }

    @GetMapping("/find_gare")
    public YenKSP.Result find_chemin() {
        GareDatabase.create_all_class();
        logger.info("find gare");
        return GareDatabase.find_chemin_start_end("Gare Montparnasse", "Chatelet");
    }

    @GetMapping("/multiply/{num1}/{num2}")
    public double multiplyNumbers(@PathVariable double num1, @PathVariable double num2) {
        logger.info("Multiplying numbers: {} and {}", num1, num2);
        return num1 * num2;
    }
}
