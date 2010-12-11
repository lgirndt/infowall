/*global window,jQuery*/
(function (window, $, undefined) {

    var TemplateManager,RenderEngine,SingleValueView;

    function error(msg){
        if(window.console){
            console.error(msg);
        }
    }

    SingleValueView = function(views){
        this.views = views;
    };

    SingleValueView.prototype.prepareModel = function(model){
        return model.current;
    };

    /**
     * The TemplateManager is responsible to extract the Mustache template code from the hosting DOM tree.
     *
     * The template has to be embedded in a script tag and there in HTML comments:
     *
     * <script type="text/mustache">
     * <!--
     * YOUR MUSTACHE CODE HERE
     * -->
     * </script>
     *
     *
     *
     */
	TemplateManager = function () {};

    /**
     * Extracts the template from the DOM Tree.
     *
     * @param container the id of the DOM script Element, which contains the tempalte code.
     */
	TemplateManager.prototype.getTemplate = function (container) {
	    var text = $('#template-' + container).html();
	    if (text === undefined || text === null) {
	        return null;
	    }

	    text = this.trimLines(text);
	    text = this.removeComments(text);

	    return text;
	};

    TemplateManager.prototype.getTemplates = function(containers) {
        var result = {}, container, template,idx;
        for(idx in containers){
            container = containers[idx];
            template = this.getTemplate(container);
            if(template != null){
                result[container] = template;
            }
        }
        return result;
    };

    /**
     * The Template Code is embedded in HTML Comments. Hence we have to remove them.
     *
     * @param str
     */
	TemplateManager.prototype.removeComments = function (str) {
	    return str.replace('<!--', '').replace('-->', '');
	};

    /**
     * Since we create a lot of HTML, it is a good idea to avoid any non required data. Whitespace
     * as such could be removed without harm, hence we do that for the sake of performance.
     *
     * @param str
     */
	TemplateManager.prototype.trimLines = function (str) {
		var lines, i;

	    lines = str.split('\n');

	    for (i = 0; i < lines.length; i = i +  1) {
	        lines[i] = $.trim(lines[i]);
	    }

	    return lines.join('\n');
	};

    RenderEngine = function(opts){
        opts = opts || {};

        this.defaultViewName = opts.defaultView || 'single-value';
        this.entries = {};

        var items = opts.items || [];
        for(var idx in items){
            var item = items[idx];
            var viewName = item.view || this.defaultViewName;
            var view = opts.views[viewName];
            this.entries[item.name] = {
                item : item,
                view : view,
                viewName : viewName
            }
        }

        this.baseUrl = opts.baseUrl;

        this.templates = opts.templates || {};
    };

    RenderEngine.prototype.renderItem = function(name,dest){
        var entry = this.entries[name];
        if(!entry){
            error('Cannot render item "' + name + '", because it does not consist.');
            return;
        }

        var view = entry.view;
        var item = entry.item;
        
        var template = this.templates[entry.viewName];
        if(!template){
            error('no template found for "' + view+"'.");
            return;
        }

        $.ajax({
            url : this.baseUrl + "/" + item.name,
            success:function(data){
                var model = view.prepareModel(data);
                var html = $.mustache(template,model);
                $(dest).append(html);
            }
        });


    };

	window.infowall = {};
	window.infowall.TemplateManager = TemplateManager;
    window.infowall.RenderEngine = RenderEngine;
    window.infowall.SingleValueView = SingleValueView;

}(window, jQuery));