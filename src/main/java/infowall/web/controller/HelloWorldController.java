package infowall.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWorldController {

    @RequestMapping(value="/hello/{helloId}",method = RequestMethod.GET)
    public ModelAndView helloWorld(@PathVariable long helloId) {

        String message = "Hello World, me " + helloId;
        return new ModelAndView("hello", "message", message);
    }
}