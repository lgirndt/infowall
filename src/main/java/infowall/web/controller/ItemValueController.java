package infowall.web.controller;

import infowall.domain.model.DashboardItemRef;
import infowall.domain.model.ItemValuePair;
import infowall.domain.process.ItemValueProcess;
import infowall.web.model.ReturnStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static infowall.web.model.ReturnStatus.failed;
import static infowall.web.model.ReturnStatus.success;

/**
 *
 */
@Controller
public class ItemValueController {

    private final ItemValueProcess itemValueProcess;

    @Autowired
    public ItemValueController(ItemValueProcess itemValueProcess) {
        this.itemValueProcess = itemValueProcess;
    }

    @RequestMapping(value="/single/item/{dashboardId}/{itemName}/{value}",method = RequestMethod.PUT)
    @ResponseBody
    public ReturnStatus storeSimpleValue(
            @PathVariable String dashboardId,
            @PathVariable String itemName,
            @PathVariable String value){

        itemValueProcess.storeSimpleValue(dashboardId,itemName, value);
        return success();
    }

    @RequestMapping(value="/item/{dashboardId}/{itemName}",method = RequestMethod.PUT)
    @ResponseBody
    public ReturnStatus storeValueWithBody(
            @PathVariable String dashboardId,
            @PathVariable String itemName,
            @RequestBody String value){

        return status(itemValueProcess.storeItemValue(dashboardId, itemName, value));
    }

    @RequestMapping(value="/item/{dashboardId}/{itemName}", method = RequestMethod.GET)
    @ResponseBody
    public ItemValuePair getValueDate(@PathVariable String dashboardId,@PathVariable String itemName){
        return itemValueProcess.showRecentValues(new DashboardItemRef(dashboardId,itemName));
    }

    private ReturnStatus status(boolean status) {
        if(status){
            return success();
        }
        else {
            return failed();
        }
    }
}
