define(['jquery','underscore','../Log','jquery.mustache'], function($,_,log) {
    var RenderEngine = function(opts){
        opts = opts || {};

        this.defaultViewName = opts.defaultView || 'single-value';
        this.entries = {};

        var items = opts.items || [];
        _.each(items,function(item) {
            var itemConf = item.conf || {};

            var viewName = itemConf.view || this.defaultViewName;
            var view = _.find(opts.views, function(v) { return v.name === viewName });
            this.entries[item.name] = {
                item : item,
                view : view,
                viewName : viewName
            }
        },this);

        this.baseUrl = opts.baseUrl;
    };

    RenderEngine.prototype.renderItem = function(name,dest,callback){
        var entry = this.entries[name];
        if(!entry){
            log.error('Cannot render item "' + name + '", because it does not consist.');
            return;
        }

        var view = entry.view;
        var item = entry.item;

        $.ajax({
            url : this.baseUrl + "/" + item.name,
            success:function(data){
                var model = view.transformModel(data,item);
                var html = $.mustache(view.template,model);
                $(dest).html(html);
                console.log("hasonRender?");
                if('onRender' in view) {
                    view.onRender(dest[0], model);
                }
                if(callback){
                    callback();
                }
            }
        });
    };

    return RenderEngine;
});
