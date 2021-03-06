// modules =================================================
var express = require('express');
var app = express();
var bodyParser = require('body-parser');
var methodOverride = require('method-override');
var path = require('path');
//require('dns').lookup(require('os').hostname(), function (err, add, fam) {
//  console.log('addr: '+add);
//    host = add;
//});

// configuration ===========================================

var port = process.env.PORT || 8080; // set our port
var host = process.env.HOST || 'localhost';
// get all data/stuff of the body (POST) parameters
//app.use(bodyParser.json()); // parse application/json
app.use(bodyParser.json({type: 'application/json'})); // parse application/vnd.api+json as json
app.use(bodyParser.urlencoded({extended: true})); // parse application/x-www-form-urlencoded

app.use('/public/js', express.static(__dirname + '/public/js'));
app.use('/public/bower_components', express.static(__dirname + '/../bower_components'));
app.use('/public/css', express.static(__dirname + '/public/css'));

app.use(methodOverride('X-HTTP-Method-Override')); // override with the X-HTTP-Method-Override header in the request. simulate DELETE/PUT
app.use(express.static(__dirname + '/public')); // set the static files location /public/img will be /img for users
app.use(express.static(path.join(__dirname, 'public')));

app.get('/hostname', function(req, res) {
   return res.json({host: host})
});

// routes ==================================================
require('./app/routes')(app); // pass our application into our routes

// start app ===============================================
app.listen(port);
console.log('Magic happens on port ' + port); 			// shoutout to the user
exports = module.exports = app; 						// expose app