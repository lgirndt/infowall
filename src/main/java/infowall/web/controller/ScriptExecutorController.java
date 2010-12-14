package infowall.web.controller;

import infowall.domain.process.ScriptExecutorProcess;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 */
@Controller
public class ScriptExecutorController {

    private final ScriptExecutorProcess scriptExecutorProcess;

    @Autowired
    public ScriptExecutorController(ScriptExecutorProcess scriptExecutorProcess) {
        this.scriptExecutorProcess = scriptExecutorProcess;
    }

    @RequestMapping("/exec/{dashboardId}/{itemName}")
    @ResponseBody
    public ObjectNode execScriptAndPrint(
            @PathVariable String dashboardId,
            @PathVariable String itemName){

        return scriptExecutorProcess.printScriptOutput(dashboardId,itemName);
    }
}
