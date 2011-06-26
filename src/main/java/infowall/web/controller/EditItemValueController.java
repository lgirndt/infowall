package infowall.web.controller;

import static infowall.web.spring.ControllerDsl.redirect;
import static infowall.web.spring.ControllerDsl.render;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import infowall.domain.model.EditItemValue;
import infowall.domain.model.ItemRef;
import infowall.domain.service.EditItemValueService;
import infowall.web.services.errorhandling.Errors;
import infowall.web.spring.FlashMessage;

@Controller
public class EditItemValueController {

    private final EditItemValueService editItemValueService;
    private final FlashMessage flash;

    @Autowired
    public EditItemValueController(
            final EditItemValueService editItemValueService,
            final FlashMessage flash) {

        this.editItemValueService = editItemValueService;
        this.flash = flash;
    }

    @RequestMapping("/configure/edit/dashboard/{dashboardId}/{itemName}")
    ModelAndView show(@PathVariable String dashboardId,@PathVariable String itemName){

        final EditItemValue model = editItemValueService.find(new ItemRef(dashboardId, itemName));

        return render("dashboard/edit",flash,"model",model);
    }

    @RequestMapping(value = "/configure/save/dashboard", method = RequestMethod.POST)
    ModelAndView saveChanges(
            @RequestParam final String dashboardId,
            @RequestParam final String itemName,
            @RequestParam final String data){

        Errors errors = new Errors();
        editItemValueService.save(new ItemRef(dashboardId,itemName),data,errors);

        if(errors.haveOccurred()){
            flash.putErrors(errors);
            return redirect("/configure/edit/dashboard/"+dashboardId+"/"+itemName);
        }else {
            flash.putInfo("The Changes have been saved.");
            return redirect("/configure/dashboard/" + dashboardId);
        }
    }
}
