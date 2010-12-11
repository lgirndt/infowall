package infowall.domain.process;

import infowall.domain.model.DashboardItemRef;
import infowall.domain.model.ItemValue;
import infowall.domain.persistence.ItemValueRepository;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 *
 */
@Service
public class ItemValueProcess {

    private final ItemValueRepository itemValueRepository;

    @Autowired
    public ItemValueProcess(ItemValueRepository itemValueRepository) {
        this.itemValueRepository = itemValueRepository;
    }

    public void storeSimpleValue(String dashboardId,String itemName, String value){

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode data = mapper.createObjectNode();
        data.put("value",value);

        ItemValue itemValue = new ItemValue();
        itemValue.setData(data);
        itemValue.setCreation(new Date());
        itemValue.setLastUpdate(new Date());

        DashboardItemRef ref = new DashboardItemRef(dashboardId,itemName);
        itemValue.setItemRef(ref);

        itemValueRepository.put(itemValue);
    }

    public ObjectNode getValue(String dashboardId,String itemName){

        ItemValue itemValue = itemValueRepository.findMostRecentItemValue(dashboardId,itemName);

        if(itemValue == null){
            return null;
        }

        return itemValue.getData();
    }
}
