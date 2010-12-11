//package infowall.domain.persistence.couchdb;
//
//import infowall.domain.model.ItemValue;
//import infowall.domain.persistence.ItemValueRepository;
//import org.ektorp.ComplexKey;
//import org.ektorp.CouchDbConnector;
//import org.ektorp.support.CouchDbRepositorySupport;
//import org.ektorp.support.View;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//
///**
// *
// */
//public class CouchDbItemValueRepository
//        extends CouchDbRepositorySupport<ItemValue>
//        implements ItemValueRepository, InitializingBean {
//
//    private final Class<ItemValue> type;
//
//    @Autowired
//    protected CouchDbItemValueRepository(
//            CouchDbConnector db) {
//
//        super(ItemValue.class, db);
//        type = ItemValue.class;
//    }
//
//    @Override
//    /*
//    @View(
//            name="latest_itemvalue",
//            map="function(doc){if(doc.type =='itemValue'){ emit(doc.itemRef,doc)}}",
//            reduce = "function(key,values,rereduce){ return values[0] }"
//    )
//    */
//    public ItemValue findLatesItemValue(String dashboardId, String itemName) {
//        this.queryView("latest_itemvalue");
//        return null;
//    }
//
//    @Override
//    @View(
//            name="most_recent_itemvalue_id",
//            map=    "function(doc){" +
//                    "   if(doc.type == 'itemValue'){" +
//                    "       emit([doc.itemRef.dashboardId,doc.itemRef.itemName],doc)" +
//                    "   }" +
//                    "}",
//            reduce= "function(keys,values,rereduce){" +
//                    "   if(rereduce){" +
//                    "       return values;" +
//                    "   }" +
//                    "   var max = values[0];"+
//                    "   for(v in values){" +
//                    "       if(max.lastUpdate < v.lastUpdate) max = v;" +
//                    "   }" +
//                    "   return max._id;" +
//                    "}"
//            )
//    public String findMostRecentItemValueId(String dashboardId, String itemName) {
//
//        ComplexKey key = ComplexKey.of(dashboardId,itemName);
//        List<String> result =  db.queryView(
//                createQuery("most_recent_itemvalue_id")
//                    .group(true)
//                    .key(key),
//                String.class);
//
//        if(result.size() == 0){
//            return null;
//        }
//
//        if(result.size() > 1){
//            throw new IllegalStateException("Expected exactly 1 result, but got:" + result);
//        }
//        return result.get(1);
//    }
//
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        initStandardDesignDocument();
//    }
//}
