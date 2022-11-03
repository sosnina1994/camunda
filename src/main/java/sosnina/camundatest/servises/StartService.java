package sosnina.camundatest.servises;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StartService {

    private final RuntimeService runtimeService;

    public void startProcess() {
        runtimeService.startProcessInstanceByKey("Process_1yhdj7o");
    }
}

