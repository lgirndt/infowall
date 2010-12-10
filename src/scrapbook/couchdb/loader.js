var fs      = require('fs');
var http    = require('http');
var path    = require('path');
var console = require('sys');
var url     = require('url');

var Script   = process.binding('evals').Script;
var couchURL = url.parse(process.argv[2]);
var couch    = http.createClient(couchURL.port, couchURL.hostname);

function loadScript(designPath, basename) {
    var fullPath = path.join(designPath, basename);
    var design = {};
    new Script(fs.readFileSync(fullPath), fullPath).runInNewContext({design: design});
    return design;
}

var pendingCount = 0;
function updateDesignDoc(name, data) {
    var designId = '_design/' + name;
    var get = couch.request('GET', couchURL.pathname + '/' + designId);
    get.end();
    get.on('response', function (getResponse){
        if (getResponse.statusCode == 404) {
            data._id = designId;
            updateCouch('PUT', data);

        } else {
            var buffer = '';
            getResponse.on('data', function(chunk){ buffer += chunk; });
            getResponse.on('end', function(){
                var doc = JSON.parse(buffer);
                data._id  = doc._id;
                data._rev = doc._rev;
                updateCouch('PUT', data);
            });
        }
    });
    pendingCount++;
}
function updateCouch(method, doc) {
    var json = JSON.stringify(doc);
    var req = couch.request(method, couchURL.pathname + '/' + doc._id, {
            'Content-Type': 'application/json',
            'Content-Length': json.length });
    req.write(json);
    req.end();
    req.on('response', function(resp){
        var data = '';
        resp.on('data', function(chunk){ data += chunk; });
        resp.on('end', function(){
            console.log(doc._id + ' [' + method + ']: ' + data);
            pendingCount--;
        });
    });
}

function designsFor(name, callback) {
    var baseDir = path.join(__dirname, name);
    fs.readdirSync(baseDir).forEach(function(designName) {
        var designPath = path.join(baseDir, designName);
        var stats      = fs.statSync(designPath);
         if (stats.isDirectory()) {
             var files = fs.readdirSync(designPath).filter(function(fn){
                 return path.extname(fn) == '.js';
             });
             callback(designName, designPath, files);
         }
    });
}


designsFor('designs', function(designName, designPath, scripts){
    var designDoc = {};
    scripts.forEach(function(fn){
        var name = fn.substring(0, fn.length - 3);
        try {
            console.log('[' + designName + '/' + fn + '] loading...');
            var design = loadScript(designPath, fn);
            if (design.view) {
                console.log('  found view: ' + name);
                if (!designDoc.views) designDoc.views = {};
                if (design.view.map) {
                    console.log('  - map function found');
                    designDoc.views[name] = { map: design.view.map.toString() };
                    if (design.view.reduce) {
                        console.log('  - reduce function found');
                        designDoc.views[name].reduce = design.view.reduce.toString();
                    }
                }
            }
            if (design.update) {
                console.log('  found update: ' + name);
                if (!designDoc.updates) designDoc.updates = {};
                designDoc.updates[name] = design.update.toString();
            }
        } catch(e) {
            console.log("unable to load design script '" + designName + '/' + fn + "': " + e);
        }
    });

    updateDesignDoc(designName, designDoc);
});

var timeout;
timeout = setInterval(function(){
    if (pendingCount == 0) {
        console.log('Done.');
        clearTimeout(timeout);
    }
}, 100);
