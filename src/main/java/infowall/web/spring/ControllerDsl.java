package infowall.web.spring;

import com.google.common.collect.ImmutableMap;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 */
public class ControllerDsl {

    public static ModelAndView render(String viewName,String modelName,Object modelObject){
        return new ModelAndView(viewName,modelName,modelObject);
    }

    public static ModelAndView render(
            String viewName,
            String modelName0,Object modelObject0,
            String modelName1,Object modelObject1){
        return new ModelAndView(viewName,
                ImmutableMap.of(
                        modelName0,modelObject0,
                        modelName1,modelObject1)
        );
    }

    public static ModelAndView redirect(String requestMapping){
        return new ModelAndView("redirect:/app" + requestMapping);
    }
}
