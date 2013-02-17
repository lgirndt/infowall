define(function() {
    var GenericView = function() {
    };

    GenericView.prototype.transformModel = function(model,item){
        if(!model || !model.current || !model.current.data){
            return {table:[]};
        }
        return model.current.data;
    };

    return GenericView;
});
