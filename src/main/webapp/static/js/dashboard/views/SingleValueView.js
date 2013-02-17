define(function() {
    function error(msg){
        if(window.console){
            console.error(msg);
        }
    }

    var SingleValueView = function(views){
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

    return SingleValueView;
});
