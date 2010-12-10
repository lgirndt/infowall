design.view = {
    map : function(doc) {

        if (doc.type == 'itemValue') {
            emit([doc.itemRef.dashboardId,doc.itemRef.itemName], doc);
        }
    }
}