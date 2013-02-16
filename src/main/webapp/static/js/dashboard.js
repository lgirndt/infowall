/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

/*global window,jQuery*/
(function (window, $, undefined) {

    var TemplateManager,RenderEngine,SingleValueView,TableValueView,HtmlView,ChartView,SlideShow;

    function error(msg){
        if(window.console){
            console.error(msg);
        }
    }

    TableValueView = function(){

    };

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

    SingleValueView = function(views){
        this.views = views;
        this.defaultStatusThreshold = 1;
        this.relations = {
            gt : function(a,b){return a > b},
            lt : function(a,b){return a < b}
        };
        this.defaultRelation = 'lt';
    };

    SingleValueView.prototype.transformModel = function(model,item){

        // fallback
        if (!model || !model.current || !model.current.data || model.current.data.value === null) {
            return {
                current : '?',
                previous: '?',
                status :  'ok',
                diff : '?',
                diffStatus : 'ok',
                since : '?',
                unit : '?'
            }
        }

        var current  = model.current.data.value;
        var previous = null;
        var diff = null;

        if(model.previous){
            previous = model.previous.data.value;
            diff = this.calcDiff(current,previous);
        }

        return {
            current : current,
            previous: previous,
            status :  this.calcStatus(current,item),
            diff : diff,
            diffStatus : this.calcDiffStatus(diff,item),
            since : this.calcSince(model),
            unit : this.calcUnit(item)
        }
    };

    SingleValueView.prototype.calcDiff = function(current,previous){
        var diff = (current - previous);//
        if(diff - Math.floor(diff) > 0){
            diff = diff.toFixed(1);
        }
        if(diff > 0){
            return '+' + diff;
        }

        return diff.toString();
    };

    SingleValueView.prototype.calcUnit = function(item){
        if(item.conf){
            if(item.conf.unit){
                return item.conf.unit
            }
        }
        return null;
    };

    SingleValueView.prototype.calcSince = function(model){
        var creation = model.current.creation;
        var now = new Date().getTime();
        var diff = now - creation;
        var secs = this.intDiv(diff,1000);
        if(secs < 60){
            return "a few seconds ago.";
        }
        var minutes = this.intDiv(secs,60);
        if(minutes < 5){
            return "a few minutes ago.";
        }
        if(minutes < 120) {
            return "about " + minutes + " minutes ago.";
        }
        var hours = this.intDiv(minutes,60);
        if(hours < 48) {
            return "about " + hours + " hours ago.";
        }
        var days = this.intDiv(hours,24);
        return "about " + days + " days ago.";
    };

    SingleValueView.prototype.intDiv = function(a,b){
        return Math.floor( a / b );
    };

    SingleValueView.prototype.calcStatus = function(val,item){
        var statusThreshold = item.statusThreshold || this.defaultStatusThreshold;
        var statusRelation  = item.statusRelation;
        if(this.relation(statusRelation,val,statusThreshold)){
            return 'ok';
        }
        else {
            return 'fail';
        }
    };

    SingleValueView.prototype.calcDiffStatus = function(val,item){
        var diffStatusRelation = item.statusRelation;
        if(this.relation(diffStatusRelation,val,0)){
            return 'ok';
        }
        else {
            return 'fail';
        }
    };

    SingleValueView.prototype.relation = function(name,a,b){
        var rel;
        if(name){
            rel = this.relations[name];
        }
        if(!rel){
            if(name){
                error("relation does not exist: " + name);
            }
            rel = this.relations[this.defaultRelation];
        }
        return rel(a,b);
    };

    HtmlView = function() {
    };

    HtmlView.prototype.transformModel = function(model,item){
        if(!model || !model.current || !model.current.data){
            return {table:[]};
        }
        return model.current.data;
    };

    ChartView = function() {
    };

    ChartView.prototype.transformModel = function(model,item) {
        return model.current;
    };

    ChartView.prototype.onRender = function() {
        console.log("Hello");
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
            var itemConf = item.conf || {};
            
            var viewName = itemConf.view || this.defaultViewName;
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

    RenderEngine.prototype.renderItem = function(name,dest,callback){
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
                var model = view.transformModel(data,item);
                var html = $.mustache(template,model);
                $(dest).html(html);
                console.log("hasonRender?");
                if('onRender' in view) {
                    view.onRender();
                }
                if(callback){
                    callback();
                }
            }
        });


    };

    var slideShowAnimation = {
        slide : {
            initNewSlide : function(elem) {
                var e = $(elem);
                var height = e.height();
                e.offset({left:0,top:height});
            },
            newSlideAnimation : function(elem) {
                return {top:0};
            },
            currentSlideAnimation : function(elem) {
                var width = $(elem).width();
                return {top:-width};
            },
            serializeAnimation : false
        },
        fade : {
            initNewSlide : function(elem) {
                $(elem).css({opacity:0});
            },
            newSlideAnimation : function(elem) {
                return {opacity:1};
            },
            currentSlideAnimation : function(elem) {
                return {opacity:0};
            },
            serializeAnimation:true
        }
    };

    SlideShow = function(opts){
        this.renderEngine = opts.renderEngine;
        this.items = opts.items;
        this.container = $(opts.container);
        this.delay = (opts.delay || 5) * 1000;
        this.fxDuration = opts.fxDuration || 600;
        this.currItemId = 0;
        this.slideShowAnimation = slideShowAnimation[ opts.animation || 'slide' ];
    };

    SlideShow.prototype.start = function(){
        this.replace();
        this.animateNext();
    };

    SlideShow.prototype.animateNext = function(){
        var self = this;
        this.timer = setTimeout(function(){
            self.start();
        },this.delay);
    };

    SlideShow.prototype.replace = function(){
        var item = this.nextItem();
        this.replaceTitle(item);
        this.replaceView(item);
    };

    SlideShow.prototype.replaceTitle = function(item){
        var duration = this.fxDuration/2;
        $('#title')
                .fadeOut(duration)
                .queue(function(next){
                    $(this).html(item.title)
                    next();
                })
                .fadeIn(duration);
    };

    SlideShow.prototype.replaceView = function(item){
        var self = this;
        var elem = $('<div class="view"></div>').appendTo(this.container);
        var width = $(this.container).width();
        var height = $(this.container).height();
        var fxDuration = this.fxDuration;
        $(elem)
            .width(width)
            .height(height);
        this.initNewSlide(elem);
        this.renderEngine.renderItem(item.name,elem,function(){
            var views = $('.view',this.container);
            var currentAnimation = self.currentSlideAnimation(elem);
            var newAnimation = self.newSlideAnimation(elem);
            var serializeAnimation = self.slideShowAnimation.serializeAnimation;

            var delay = serializeAnimation ? fxDuration  : 0;
            // animate current
            if(views.length > 1){
                $(views[0]).animate( currentAnimation,fxDuration,function(){$(this).remove()});
            }
            // animate new
            $(elem).delay(delay).animate( newAnimation ,{duration: fxDuration} );
        });
    };

    SlideShow.prototype.initNewSlide = function(elem){
        return this.slideShowAnimation.initNewSlide(elem);
    };

    SlideShow.prototype.newSlideAnimation = function(elem){
        return this.slideShowAnimation.newSlideAnimation(elem);
    };

    SlideShow.prototype.currentSlideAnimation = function(elem){
        return this.slideShowAnimation.currentSlideAnimation(elem);
    };

    SlideShow.prototype.nextItem = function(){
        return this.items[this.nextItemId()];
    };

    SlideShow.prototype.nextItemId = function(){
        var curr = this.currItemId;
        this.currItemId = (curr + 1) % this.items.length;
        return curr;
    };

	window.infowall = {};
	window.infowall.TemplateManager = TemplateManager;
    window.infowall.RenderEngine = RenderEngine;
    window.infowall.SingleValueView = SingleValueView;
    window.infowall.TableValueView = TableValueView;
    window.infowall.HtmlView = HtmlView;
    window.infowall.ChartView = ChartView;
    window.infowall.SlideShow = SlideShow;

}(window, jQuery));
