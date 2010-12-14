package infowall.domain.process;

import infowall.domain.model.DashboardItemRef;
import infowall.domain.model.ItemValue;
import infowall.domain.model.ItemValuePair;
import infowall.domain.persistence.ItemValueRepository;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 *
 */
@Service
public class ItemValueProcess {

    private final Logger logger = LoggerFactory.getLogger(ItemValueProcess.class);

    private final ItemValueRepository itemValueRepository;
    private final ObjectMapper mapper;

    @Autowired
    public ItemValueProcess(ItemValueRepository itemValueRepository) {
        this.itemValueRepository = itemValueRepository;
        mapper = new ObjectMapper();
    }

    public void storeSimpleValue(String dashboardId,String itemName, String value){

        ObjectNode data = createSimpleValue(value);
        storeItem(dashboardId, itemName, data);
    }


    public boolean storeItemValue(DashboardItemRef itemRef, String value){
        return storeItemValue(itemRef.getDashboardId(), itemRef.getItemName(), value);
    }

    public boolean storeItemValue(String dashboardId,String itemName, String value){
        try {
            ObjectNode data = mapper.readValue(value,ObjectNode.class);
            storeItem(dashboardId,itemName,data);

            return true;
        } catch (IOException e) {
            logger.error("Cannot write ItemValue",e);
            return false;
        }
    }

    private ObjectNode createSimpleValue(String value) {
        ObjectNode data = mapper.createObjectNode();
        data.put("value",value);
        return data;
    }

    private void storeItem(String dashboardId, String itemName, ObjectNode data) {
        ItemValue itemValue = createItemValue(dashboardId, itemName, data);

        itemValueRepository.put(itemValue);
    }

    private ItemValue createItemValue(String dashboardId, String itemName, ObjectNode data) {
        ItemValue itemValue = new ItemValue();
        itemValue.setData(data);
        itemValue.setCreation(new DateTime());
        itemValue.setLastUpdate(new DateTime());

        DashboardItemRef ref = new DashboardItemRef(dashboardId,itemName);
        itemValue.setItemRef(ref);
        return itemValue;
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
