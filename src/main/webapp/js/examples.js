
var ex01 = {
	template:'div.who',
	data:{ who:'Hello Wrrrld' }
};

var ex02 = {
	template:'div.hello',
	data:{ who:'Hello Wrrrld' },
	directive:{ 'span':'who' }
};

var ex03 = {
	template:'div.friends',
	directive:{
		'.who':'who2.name',
		'.who@title':'See the tweets of #{who2.twitter}',
		'.who@href+':'who2.twitter'
	},
	data:{
		friend:[
			{
				name:'Hughes', 
				twitter:'hugheswaroquier'
			},{
				name:'Yves', 
				twitter:'yveshiernaux'
			}
		], 
		who:'dono',
		who2:{
			name:'Mic', 
			twitter:'tchvil'
		}
	}
};

var ex04 = {
	template:'table.playerList',
	directive:{
		'tbody tr':{
			'player<-players':{
				'@class+':function(arg){
					
					var oddEven = (arg.pos % 2 == 0) ? 'even' : 'odd';
					var firstLast = (arg.pos == 0) ? 'first' : (arg.pos == arg.player.items.length - 1) ? 'last' : '';
					return ' ' + oddEven + ' ' + firstLast;
				},
				'td':'player',
				'td@style': 'cursor:pointer',
				'td@onclick':'clickLine(this);'
			},
			sort:function(a, b){
				return a > b ? 1 : -1; 
			}
		}
	},
	data:{
		players:[
			"Adrian Meador","Wendy Leatherbury","Michèle Noïjû","Chloé Ellemême","Bryan O'Connor","Walter Hagelstein",
			"La \"volante\"","Todd Swift","Valerie Paige"
		]
	}
};
function clickLine(elm){
	alert(elm.innerHTML);
}

var ex05 = {
	template:'table.partialTable',
	data:{
		cols:['name', 'food', 'legs'],
		animals:[
			{name:'bird', food:'seed', legs:2},
			{name:'cat', food:'mouse, bird', legs:4},
			{name:'dog', food:'bone', legs:4},
			{name:'mouse', food:'cheese', legs:4}
		]
	},
	directive1:{
		'th':{
			'col<-cols':{
				'.':'col'
			}
		},
		'td':{
			'col<-cols':{
				'@class':'col'
			}
		}
	},
	directive2:{
		'tbody tr':{
			'animal<-animals':{
				'td.name':'animal.name',
				'td.food':'animal.food',
				'td.legs':'animal.legs'
			}
		}
	}
};

var ex06 = {
	
	template:'div.scoreBoard',
	data:{
		teams: [{
			name: 'Cats',
			players: [	
				{first: 'Alicé', last: 'Kea\'sler', score: [16, 15, 99, 100]}, 
				{first: '', name: '', score: 0},
				{first: 'Vicky', last: 'Benoit', score: [3, 5]}, 
				{first: 'Wayne', last: 'Dartt', score: [9, 10]}
			]
		},{
			name: 'Dogs',
			players: [
				{first: 'Ray', last: 'Braun', score: 14}, 
				{first: 'Aaron', last: 'Ben', score: 24}, 
				{first: 'Steven', last: 'Smith', score: 1}, 
				{first: 'Kim', last: 'Caffey', score: 19}
			]
		},{
			name:'Birds',
			players:null
		},{
			name: 'Mice',
			players: [
				{first: 'Natalie', last: 'Kinney', score: 16}, 
				{first: 'Caren', last: 'Cohen', score: 3}
			]
		}]
	},
	directive:{
		'tr.scoreBoard': {
			'team <- teams': {
				'td.teamName' : 'team.name',
				'tr.teamList': {
					'player <- team.players': {
						'td.player': '#{player.first} (#{player.last})',
						'td.score': '#{player.score}',
						'td.position': 
							function(arg){
								return arg.pos + 1;
						},
						'@class+':
							function(arg){
								return (arg.player.pos % 2 == 1) ? ' odd' : ' even';
						}
					}
				}
			}
		}
	}
};

var ex07 = {
	template:'ul.treeItem',
	data:{
		children: [{
			name: 'Europe',
			children: [{
				name: 'Belgium',
				children: [{
					name: 'Brussels',
					children:null},{
					name: 'Namur'},{
					name: 'Antwerpen'}]},{
				name: 'Germany'},{
				name: 'UK'}]},{
			name: 'America',
			children: [{
				name: 'US',
				children: [{
					name: 'Alabama'},{
					name: 'Georgia'}]},{
				name: 'Canada'},{
				name: 'Argentina'}]},{
			name: 'Asia'},{
			name: 'Africa'},{
			name: 'Antarctica'}
		]
	},
	directive:{
		'li': {
			'child <- children': {
				'a': 'child.name',
				'a@onclick':'alert(\'#{child.name}\');',
				'div.children': function(ctxt){
					return ctxt.child.item.children ? ex07.rfn(ctxt.child.item):'';
				}
			}
		}
	}
};

var ex08 = function(){
	var	
		
		
		html = $p( '#clock' ),

		
		tz = 'Europe/Brussels',
		url = 'http:

		
		directive = {
			'span.hour': overlay('hour'),
			'span.minute': overlay('minute'),
			'span.second': overlay('second'),
			'span.tz': 'tz'
		},

		
		template = html.compile( directive );

	
	function overlay(what){
		return function(a){
			var val = a.context[what];
			return val === 0 ? '00' : val < 10 ? '0' + val : val;
		};
	}

	
	var noCache = 0;
	function loadTime(){
		var	old = document.getElementById('dataLoad08'),
			s = document.createElement("script");
		s.src = url + noCache++;
		!old ? document.body.appendChild(s) : document.body.replaceChild(s, old);
		s.id = 'dataLoad08';
	}

	
	window.showTime = function(data){
		
		html = html.render( data, template );
		
		window._to = setTimeout( loadTime, 1000 );
	};

	
	loadTime();
};

var ex09 = function() {
	
	var noCache = 0; 
	function doYQL(YQ,community,path) {
		var URI = "http:
		URI += ( community ) ? "&env=http:
		var YQU = URI + "&q=" + YQ;
		var	old = document.getElementById('dataLoad09'),
			s = document.createElement("script");
		s.src = encodeURI(YQU).replace(/_nocache/, noCache++);
		!old ? document.body.appendChild(s) : document.body.replaceChild(s, old);
		s.id = 'dataLoad09';
	}
	var symbols = ["^n225","^hsi","^gdaxi","^fchi","^ftse","^dji","^ixic"].sort().join('","');
	var YQL_QUERY = 'select symbol, Name, Change, LastTradePriceOnly from yahoo.finance.quotes where symbol in ("'+symbols+'")';
	var WI = {
		"template":"table.indices",
		"header": {
			"column":[ "symbol","name","change", "percent", "price" ]
		},
		"directivehead": {
			"th": {
				"c<-column": {
					".":"c"
				}
			},
			'td':{
				"c<-column":{
					"@class":"c"
				}
			}
		},
		"directivebody": {
			"tbody tr": {
				"q<-quote":{
					"td.symbol":"q.symbol",
					"td.name":"q.Name",
					"td.change":"q.Change",
					"td.percent":function(a){
						return Math.floor(parseFloat(a.item.Change) / parseFloat(a.item.LastTradePriceOnly) * 1000000) / 10000 + ' %';
					},
					"td.price":"q.LastTradePriceOnly",
					"td@class+":function(a){
						return (/^-/).test(a.item.Change) ? ' red' : ' green';
					}
				}
			}
		}
	};

	var TEMPLATE = $p( WI.template ).render( WI.header, WI.directivehead ).compile( WI.directivebody );

	doYQL(YQL_QUERY,true,null);

	window.showQuotes = function(data){
		if(typeof data.error !== 'undefined'){
			$p('table.indices')[0].innerHTML =  '<tr><td>Error in the service: ' + data.error.description + '</td></tr>';
		}else{
			var PAYLOAD = data.query.results;
			$p('table.indices').render( PAYLOAD, TEMPLATE );
			window._to10 = setTimeout(function(){
				doYQL(YQL_QUERY,true,null);
			}, 10000);
		}
	};
};

var ex10 = {
	template:'form.sizes',
	directive:{
        'option': {
            'size <- sizes': {
                '.':'#{size.val} - #{size.name}',
				'@value':'size.val',
                '@selected':'size.sel'
            }
        }
	},
	data:{
		sizes: [{ val:'S', name: 'small' }, { val:'M', name: 'medium', sel: true }, {val:'L', name:'large'}]
	}
};

var ex11 = function(){
	var data = {
		'makes':[
			{
				"name":"Ford",
				"models":[
					{"name":"Fusion"},
					{"name":"Mustang"}
				]
			},
			{
				"name":"Chevrolet",
				"models":[
					{"name":"Aveo"},
					{"name":"Camaro"}
				]
			}
		]
	};
	
	$p('select.make').render(data, {
		'@onchange':'"clickSelect(this);"',
		'option.values':{
			'make <- makes':{
				'.':'make.name'
			}
		}
	});
	
	var subSel = $p('select.model').compile({
		'@disabled':function(a){
			return a.context === false ? 'x':''; 
		},
		'option':{
			'model <-':{
				'.':'model.name'
			}
		}
	});
	
	window.clickSelect = function(sel){
		var idx = sel.selectedIndex-1,
			models;
		if(idx >= 0){ 
			models = data.makes[idx].models;
			$p('select.model').render(models, subSel);
		}else{
			$p('select.model').render(false, subSel);
		}
	};
};
