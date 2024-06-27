package org.example;

import classe.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

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
        Create_class.create_all_class();
        return "create_all_class() initialized";
    }

    @GetMapping("/find_gare")
    public String find_chemin(@RequestParam String start, @RequestParam String end) {
        System.out.println("start: " + start + " end: " + end);
        Create_class.create_all_class();
        logger.info("find gare");
        return Fonction.find_chemin_start_end(start, end);
    }

    @GetMapping("/gares")
    public String all_gares() {
        logger.info("toute les gares");
        return ReturnGareNoTraited.CallFunctions();
    }

    @GetMapping("/garesMap")
    public String all_garesMap() {
        Create_class.create_all_class();
        logger.info("toute les gares");
        String ObjToJson = JsonConverter.convertObjectToJson(ReturnGareWithOrder.ReturnVue_lieu_with_line_and_order_());
        return ObjToJson;
    }

    @GetMapping("/multiply/{num1}/{num2}")
    public double multiplyNumbers(@PathVariable double num1, @PathVariable double num2) {
        logger.info("Multiplying numbers: {} and {}", num1, num2);
        return num1 * num2;
    }

}
