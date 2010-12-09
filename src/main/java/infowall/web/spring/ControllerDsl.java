package infowall.web.spring;

import org.springframework.web.servlet.ModelAndView;

/**
 *
 */
public class ControllerDsl {

    public static ModelAndView render(String viewName,String modelName,Object modelObject){
        return new ModelAndView(viewName,modelName,modelObject);
    }

    public static ModelAndView redirect(String requestMapping){
        return new ModelAndView("redirect:/app" + requestMapping);
    }
}
