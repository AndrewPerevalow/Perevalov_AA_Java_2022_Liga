package ru.internship.mvc.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.internship.mvc.service.strategy.StrategyService;

@RestController
public class Controller {

    private final StrategyService strategyService;

    @Autowired
    Controller(StrategyService strategyService) {
        this.strategyService = strategyService;
    }

    @GetMapping("/cli")
    public String getCommand(@RequestParam String command) {
        return strategyService.executeCommand(command);
    }
}
