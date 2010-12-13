package infowall.infrastructure.service;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collections;

/**
 *
 */
public class GroovyExecutor {

    private final Logger logger = LoggerFactory.getLogger(GroovyExecutor.class);

    public String exec(File file){
        try {

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PrintStream wrappedOut = new PrintStream(out);

            Binding binding = new Binding();
            binding.setProperty("out",wrappedOut);

            GroovyShell shell = new GroovyShell(binding);
            shell.run(file, Collections.emptyList());

            return out.toString("UTF-8");

        } catch (IOException e) {
            logger.error("Cannot exec groovy script " + file.getAbsolutePath(),e);
        }
        return null;
    }
}
