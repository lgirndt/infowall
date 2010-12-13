package infowall.infrastructure.service;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 *
 */
public class GroovyExecutorTest {

    @Test
    public void testExec() throws Exception {

        Resource resource  = new ClassPathResource("/groovy/simple_groovy.groovy");

        GroovyExecutor executor = new GroovyExecutor();
        assertThat(executor.exec(resource.getFile()), is("hello world\n") );
    }
}
