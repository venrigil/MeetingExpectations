//test from https:

var doc           = require('jsdom').jsdom(),
    window        = doc.createWindow(),
    jQueryElement = doc.createElement('script'),
    pureElement   = doc.createElement('script'),
    body          = doc.getElementsByTagName('body').item(0),
    directive     = { 'span.who': 'who' },
    data          = { 'who': 'Hello Wrrrld' };

body.innerHTML = '<div id="template"><div class="hello">\
<span class="who"></span>\
</div></div>';


jQueryElement.src = 'http:
doc.head.appendChild(jQueryElement);


pureElement.src = 'http:
doc.head.appendChild(pureElement);


http.createServer(function (req, res) {

	window.jQuery('div#template').render(data, directive);

}).listen(1234, '127.0.0.1');



