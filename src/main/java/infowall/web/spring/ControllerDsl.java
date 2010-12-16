package infowall.web.spring;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import infowall.web.services.errorhandling.Errors;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import static com.google.common.collect.ImmutableMap.of;

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
                of(
                        modelName0,modelObject0,
                        modelName1,modelObject1)
        );
    }

    public static ModelAndView render(
            String viewName,
            Errors errors,
            Map<String,Object> modelMap){
        
        Map<String,Object> actualModel;
        if(errors.haveOccurred()){
            actualModel = new ImmutableMap.Builder<String,Object>()
                    .putAll(modelMap)
                    .put("errors",errors.getMessages()).build();
        } else {
            actualModel = modelMap;
        }
        return new ModelAndView(viewName,actualModel);
    }

    public static ModelAndView render(
            String viewName,
            FlashMessage flash,
            Map<String,?> modelMap){

        Map<String,Object> actualModel = Maps.newHashMap( modelMap );
        String info = flash.consumeInfo();
        if(info != null){
            actualModel.put("info",info);
        }
        Errors errors = flash.consumeErrors();
        if(errors != null && errors.haveOccurred()){
            actualModel.put("errors",errors.getMessages());
        }
        return new ModelAndView(viewName,actualModel);
    }

    public static ModelAndView render(
            String viewName,
            FlashMessage flash,
            String modelName,Object modelObject){

        return render(viewName,flash, of(modelName,modelObject));
    }

        public static ModelAndView render(
            String viewName,
            FlashMessage flash,
            String modelName1,Object modelObject1,
            String modelName2,Object modelObject2){

        return render(viewName,flash, of(modelName1,modelObject1,modelName2,modelObject2));
    }

    public static ModelAndView redirect(String requestMapping){
        return new ModelAndView("redirect:/app" + requestMapping);
    }

    public static ModelAndView to404(){
        return new ModelAndView("/status404");
    }
}
