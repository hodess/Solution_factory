package com.example.spring_boot;

import model.GareDatabase;
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
        return "hello world!";
    }

    @GetMapping("/gare")
    public String callGareDatabase() {
        GareDatabase.main_gare_databse();  // Appel de la méthode principale
        return "Gare database processed!";
    }

    @GetMapping("/multiply/{num1}/{num2}")
    public double multiplyNumbers(@PathVariable double num1, @PathVariable double num2) {
        logger.info("Multiplying numbers: {} and {}", num1, num2);
        return num1 * num2;
    }
}
