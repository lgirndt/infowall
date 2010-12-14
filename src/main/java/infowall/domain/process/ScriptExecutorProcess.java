package infowall.domain.process;

import infowall.domain.model.DashboardItemRef;
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
    private final ItemValueProcess itemValueProcess;

    @Autowired
    public ScriptExecutorProcess(
            ConfigRoot configRoot,
            GroovyExecutor groovyExecutor,
            ItemValueProcess itemValueProcess) {
        this.configRoot = configRoot;
        this.groovyExecutor = groovyExecutor;
        this.itemValueProcess = itemValueProcess;
    }

    public ObjectNode printScriptOutput(DashboardItemRef itemRef){
        String content = execScript(itemRef);

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(content,ObjectNode.class);
        } catch (IOException e) {
            logger.error("cannot exec script",e);
            return null;
        }
    }

    private String execScript(DashboardItemRef itemRef) {
        File root = configRoot.getDirectory();
        File scriptDir = new File(root,"scripts");
        File dashboardDir = new File(scriptDir,itemRef.getDashboardId());
        File itemDir = new File(dashboardDir,itemRef.getItemName() + ".groovy");
        String content = groovyExecutor.exec(itemDir);
        return content;
    }

    public void execScriptAndStoreOutput(DashboardItemRef itemRef){
        String content = execScript(itemRef);
        if(content != null){
            itemValueProcess.storeItemValue(itemRef,content);
        }else {
            logger.warn("Could not store script result of " + itemRef + ", content was null.");
        }
    }
}
