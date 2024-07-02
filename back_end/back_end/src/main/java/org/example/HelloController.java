package org.example;

import classe.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/hello")
    public String sayHello() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        logger.info("hello");
        String response = "hello world!";

        stopWatch.stop();
        logger.info("Execution time of sayHello: {} ms", stopWatch.getTotalTimeMillis());

        return response;
    }

    @GetMapping("/init/{num}")
    public String callGareDatabase(@PathVariable int num) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        if (num==1){
            num=Fonction.deja_init();
        }else{
            Fonction.clear_all();
        }
        if(num!=0){
            return "Already initialized";
        }
        logger.info("init");
        Create_class.create_all_class();
        String response = "create_all_class() initialized";

        stopWatch.stop();
        logger.info("Execution time of init all gare: {} ms", stopWatch.getTotalTimeMillis());

        return response;
    }

    @GetMapping("/find_gare")
    public String find_chemin(@RequestParam String start, @RequestParam String end) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        if (Fonction.deja_init()==0){
            Create_class.create_all_class();
        }

        logger.info("start: " + start + " end: " + end);
        String response = Fonction.find_chemin_start_end(start, end);

        stopWatch.stop();
        logger.info("Execution time of find_chemin: {} ms", stopWatch.getTotalTimeMillis());

        return response;
    }

    @GetMapping("/gares")
    public String all_gares() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        logger.info("toute les gares");
        String response = ReturnGareNoTraited.CallFunctions();

        stopWatch.stop();
        logger.info("Execution time of all_gares: {} ms", stopWatch.getTotalTimeMillis());

        return response;
    }

    @GetMapping("/connexite")
    public boolean connexite() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        logger.info("connexité");
        boolean response = Fonction.main_connexite();

        stopWatch.stop();
        logger.info("Execution time of connexite: {} ms", stopWatch.getTotalTimeMillis());

        return response;
    }

    @GetMapping("/garesMap")
    public String all_garesMap() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        logger.info("toute les gares");
        String response = JsonConverter.convertObjectToJson(ReturnGareWithOrder.ReturnVue_lieu_with_line_and_order_());

        stopWatch.stop();
        logger.info("Execution time of all_garesMap: {} ms", stopWatch.getTotalTimeMillis());

        return response;
    }
}
