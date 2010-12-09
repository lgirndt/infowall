package infowall.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 *
 */
@Service
class ConfigRootImpl implements InitializingBean, ConfigRoot {

    private final Logger logger = LoggerFactory.getLogger(ConfigRootImpl.class);

    private File directory;

    @Override
    public void afterPropertiesSet() throws Exception {
        String property = System.getProperty("infowall.config-root");
        if(property == null){
            throw new IllegalArgumentException("The System Property -Dinfowall.config-root is not set.");
        }
        directory = new File(property);

        if(!directory.exists()){
            throw new IllegalArgumentException(
                    "The Config Root '" + property + "' from the " +
                    "System Property -Dinfowall.config-root does not exist");
        }

        logger.info("Config Root Directory successfully set to '{}'.",directory.getAbsolutePath());
    }

    @Override
    public File getDirectory() {
        return directory;
    }
}
