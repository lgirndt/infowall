package infowall.domain.process;

import infowall.domain.model.DashboardItemRef;
import infowall.infrastructure.ConfigRoot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 *
 */
@Service
public class ScriptFileProvider {

    private final ConfigRoot configRoot;

    @Autowired
    public ScriptFileProvider(ConfigRoot configRoot) {
        this.configRoot = configRoot;
    }

    public File toScriptFile(DashboardItemRef itemRef) {
        File root = configRoot.getDirectory();
        File scriptDir = new File(root,"scripts");
        File dashboardDir = new File(scriptDir,itemRef.getDashboardId());
        return new File(dashboardDir,itemRef.getItemName() + ".groovy");
    }

    public boolean existsScriptFile(DashboardItemRef itemRef){
        File file = toScriptFile(itemRef);
        return file.exists();
    }
}
