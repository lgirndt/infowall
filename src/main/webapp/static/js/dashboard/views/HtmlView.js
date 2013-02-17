define(['./GenericView','text!./Html.html'],function(GenericView, template) {
    var HtmlView = function() {

    };

    HtmlView.prototype = new GenericView();
    HtmlView.prototype.template = template;
    return HtmlView;
});
