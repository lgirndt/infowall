define(['./GenericView','text!dashboard/templates/Url.html'],function(GenericView, template) {
    var UrlView = function() {

    };

    UrlView.prototype = new GenericView();
    UrlView.prototype.template = template;
    return UrlView;
});
