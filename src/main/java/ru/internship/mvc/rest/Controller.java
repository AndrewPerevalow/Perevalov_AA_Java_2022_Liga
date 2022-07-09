package ru.internship.mvc.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.internship.mvc.service.command.CommandService;

@RestController
public class Controller {

    private final CommandService commandService;

    @Autowired
    Controller(CommandService commandService) {
        this.commandService = commandService;
    }

    @GetMapping("/cli")
    public String getCommand(@RequestParam String command) {
        return commandService.executeCommand(command);
    }
}
