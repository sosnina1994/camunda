package sosnina.camundatest.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import sosnina.camundatest.servises.StartService;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final StartService startService;

    @PostMapping("/start")
    public void createProcess() {
        startService.startProcess();
    }

}
