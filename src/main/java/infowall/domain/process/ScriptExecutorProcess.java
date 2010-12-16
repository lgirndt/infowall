package infowall.domain.process;

import infowall.domain.model.DashboardItemRef;
import infowall.infrastructure.service.GroovyExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 *
 */
@Service
public class ScriptExecutorProcess {

    private final Logger logger = LoggerFactory.getLogger(ScriptExecutorProcess.class);

    private final GroovyExecutor groovyExecutor;
    private final ItemValueProcess itemValueProcess;
    private final ScriptFileProvider scriptFileProvider;

    @Autowired
    public ScriptExecutorProcess(
            GroovyExecutor groovyExecutor,
            ItemValueProcess itemValueProcess,
            ScriptFileProvider scriptFileProvider) {
        this.groovyExecutor = groovyExecutor;
        this.itemValueProcess = itemValueProcess;
        this.scriptFileProvider = scriptFileProvider;
    }

    public String printScriptOutput(DashboardItemRef itemRef){
        String content = execScript(itemRef);
        return content;
        /*
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(content,ObjectNode.class);
        } catch (IOException e) {
            logger.error("cannot exec script",e);
            return null;
        }
        */
    }

    private String execScript(DashboardItemRef itemRef) {
        File itemFile = scriptFileProvider.toScriptFile(itemRef);
        return groovyExecutor.exec(itemFile);
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
