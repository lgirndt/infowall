package infowall.domain.process;

import infowall.domain.model.DashboardItemRef;
import infowall.domain.model.ItemValue;
import infowall.domain.model.ItemValuePair;
import infowall.domain.persistence.ItemValueRepository;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        itemValue.setCreation(new DateTime());
        itemValue.setLastUpdate(new DateTime());

        DashboardItemRef ref = new DashboardItemRef(dashboardId,itemName);
        itemValue.setItemRef(ref);

        itemValueRepository.put(itemValue);
    }

    public ItemValuePair showRecentValues(DashboardItemRef itemRef){

        List<ItemValue> itemValues = itemValueRepository.findMostRecentItemValues(itemRef,2);

        if(itemValues == null ||  itemValues.size() == 0){
            return null;
        }

        return toPair(itemValues);
    }

    private ItemValuePair toPair(List<ItemValue> itemValues) {

        final ItemValue curr = itemValues.get(0);
        final ItemValue prev;

        if(itemValues.size()>1){
            prev = itemValues.get(1);
        }else {
            prev = null;
        }

        return new ItemValuePair(curr,prev);
    }
}
