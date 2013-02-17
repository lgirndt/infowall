define(['text!./TableValue.html'],function(template) {
    var TableValueView = function(){

    };

    TableValueView.prototype.template = template;

    TableValueView.prototype.transformModel = function(model,item){
        var idx,entry;

        if(!model || !model.current || !model.current.data){
            return {table:[]};
        }

        for(idx in model.current.data.table){
            entry = model.current.data.table[idx];
            entry.status = entry.value == 0 ? 'ok' : 'fail';
        }

        return model.current.data;
    };

    return TableValueView;
});
