define(function() {
    return {
        error : function(msg){
            if(window.console){
                console.error(msg);
            }
        }
    };
});
