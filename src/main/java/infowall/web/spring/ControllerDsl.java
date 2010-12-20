/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

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
