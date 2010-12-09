package infowall.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldController {

    private final Logger logger = LoggerFactory.getLogger(HelloWorldController.class);

    @RequestMapping(value="/hello/{helloId}",method = RequestMethod.GET)
    public ModelAndView helloWorld(@PathVariable long helloId) {

        logger.info("some log messsage.");

        String message = "Hello World, me " + helloId;
        return new ModelAndView("hello", "message", message);
    }
}