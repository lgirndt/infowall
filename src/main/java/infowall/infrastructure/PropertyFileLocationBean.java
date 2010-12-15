package infowall.infrastructure;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.File;

/**
 *
 */
public class PropertyFileLocationBean implements FactoryBean<Resource>{

    private final Resource location;

    public PropertyFileLocationBean(ConfigRoot configRoot) {
        File baseDir = configRoot.getDirectory();
        File file = new File(baseDir,"app.properties");
        if(!file.exists()){
            throw new IllegalArgumentException("Properties file '" + file.getAbsolutePath() + "' does not exist.");
        }
        this.location = new FileSystemResource(file);
    }

    @Override
    public Resource getObject() throws Exception {
        return location;
    }

    @Override
    public Class<?> getObjectType() {
        return Resource.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
