define(['jquery'],function($) {
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

    var SlideShow = function(opts){
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

    return SlideShow;
});
