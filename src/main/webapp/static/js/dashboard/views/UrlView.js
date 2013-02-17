define(['./GenericView','text!./Url.html'],function(GenericView, template) {
    var UrlView = function() {

    };

    UrlView.prototype = new GenericView();
    UrlView.prototype.template = template;
    UrlView.prototype.name     = 'url';

    return UrlView;
});
