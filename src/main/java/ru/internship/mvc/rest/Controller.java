package ru.internship.mvc.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.internship.mvc.service.strategy.StrategyService;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final StrategyService strategyService;

    @GetMapping("/cli")
    public String getCommand(@RequestParam String command) {
        return strategyService.executeCommand(command);
    }

    @GetMapping("/search-user-max-tasks")
    public String getUserWithMaxTasks(@RequestParam("status") String status,
                                      @RequestParam("date_interval") String dateInterval) {
        return strategyService.executeFind(status,dateInterval);
    }
}
