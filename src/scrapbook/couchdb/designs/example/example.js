design.view = {
    map : function(doc) {

        if (doc.type == 'itemValue') {
            emit([doc.itemRef.dashboardId,doc.itemRef.itemName], doc);
        }
    },
    reduce : function(keys, values, rereduce) {
        if (rereduce) {
            return values;
        }
        else {
            var v;
            
            var max = values[0];
            for (v in values) {
                if (v.lastUpdate > max.lastUpdate) {
                    max = v;
                }
            }
            return max.data;
        }
    }
};