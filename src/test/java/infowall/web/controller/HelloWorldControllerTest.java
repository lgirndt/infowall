package infowall.web.controller;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 *
 */
public class HelloWorldControllerTest {

    @Test
    public void testHelloWorld() throws Exception {

        HelloWorldController controller = new HelloWorldController();
        ModelAndView mav = controller.helloWorld(123l);
        assertThat(mav.getViewName(), is("hello"));
    }
}
