package infowall.domain.process;

import infowall.infrastructure.ConfigRoot;
import infowall.infrastructure.service.GroovyExecutor;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 *
 */
@Service
public class ScriptExecutorProcess {

    private final Logger logger = LoggerFactory.getLogger(ScriptExecutorProcess.class);

    private final ConfigRoot configRoot;
    private final GroovyExecutor groovyExecutor;

    @Autowired
    public ScriptExecutorProcess(ConfigRoot configRoot, GroovyExecutor groovyExecutor) {
        this.configRoot = configRoot;
        this.groovyExecutor = groovyExecutor;
    }

    public ObjectNode printScriptOutput(String dashboardId,String itemName){
        File root = configRoot.getDirectory();
        File scriptDir = new File(root,"scripts");
        File dashboardDir = new File(scriptDir,dashboardId);
        File itemDir = new File(dashboardDir,itemName + ".groovy");

        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(groovyExecutor.exec(itemDir),ObjectNode.class);
        } catch (IOException e) {
            logger.error("cannot exec script",e);
            return null;
        }
    }
}
